package com.metaverse.files.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;

import com.metaverse.files.rest.AuthRest;
import com.metaverse.files.utils.TimeUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Класс конфигурации JSON Web Token.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@Configuration
public class JwtConfig {

    /**
     * Имя заголовка Refresh JSON Web Token-а в Cookie
     */
    public static final String REFRESH_TOKEN = "refreshToken";

    private static final String PERMISSIONS = "permissions";


    // Поля конфигурируются через свойства указанные в файле application.properties
    @Value("${app.jwt.secretKey}")
    private String secretKey;
    @Value("${app.jwt.token.access.expirationAfterHours}")
    private Integer accessTokenExpirationAfterHours;
    @Value("${app.jwt.token.refresh.expirationAfterDays}")
    private Integer refreshTokenExpirationAfterDays;
    @Value("${app.jwt.token.refresh.cookiesLifetimeDays}")
    private Integer cookiesLifetimeDays;

    // Обязательно должен быть конструктор по умолчанию
    public JwtConfig() {
    }

    /**
     * Генерирует Access JSON Web Token на основе данных безопасности о пользователе.
     *
     * Access JSON Web Token используется для проверки доступа пользователя к endpoint-ам
     * @param userDetails данные безопасности о пользователе
     * @return Access JSON Web Token
     */
    public String generateAccessToken(UserDetails userDetails) {

        Map<String, Object> claims = getClaimsFromUser(userDetails);

        Date creationDate = TimeUtils.dateNow();
        Date expiredDate = new Date(creationDate.getTime() + TimeUnit.HOURS.toMillis(accessTokenExpirationAfterHours));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(creationDate)
                .setExpiration(expiredDate)
                .signWith(getRealSecretKey())
                .compact();
    }

    /**
     * Генерирует Refresh JSON Web Token на основе данных безопасности о пользователе.
     *
     * Refresh JSON Web Token используется для обновления Access JSON Web Token,
     * после истечения его срока действия
     * @param userDetails данные безопасности о пользователе
     * @return Refresh JSON Web Token
     */
    public String generateRefreshToken(UserDetails userDetails) {

        Map<String, Object> claims = getClaimsFromUser(userDetails);

        Date creationDate = TimeUtils.dateNow();
        Date expiredDate = new Date(creationDate.getTime() + TimeUnit.DAYS.toMillis(refreshTokenExpirationAfterDays));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(creationDate)
                .setExpiration(expiredDate)
                .signWith(getRealSecretKey())
                .compact();
    }

    private Map<String, Object> getClaimsFromUser(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // Получаем список разрешений пользователя
        List<String> permissionsList =
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();

        claims.put(PERMISSIONS, permissionsList);

        return claims;
    }

    /**
     * Получить секретный ключ для генерации токена основываясь на secretKey.
     *
     * @return секретный ключ для генерации токена
     */
    public SecretKey getRealSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * Получить имя пользователя из токена.
     *
     * @param token JSON Web Token
     * @return имя пользователя
     */
    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    /**
     * Получает разрешения пользователя из токена в строковом представлении.
     *
     * @param token JSON Web Token
     * @return список разрешений пользователя в строковом представлении
     */
    public List<String> getUserPermissionsAsString(String token) {
        return getAllClaimsFromToken(token).get(PERMISSIONS, List.class);
    }

    /**
     * Получает разрешения пользователя из токена в виде {@link SimpleGrantedAuthority}.
     *
     * @param token JSON Web Token
     * @return список разрешений пользователя в виде {@link SimpleGrantedAuthority}
     */
    public List<SimpleGrantedAuthority> getUserPermissionsAsSGA(String token) {
        return getUserPermissionsAsString(token).stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getRealSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Добавить в cookie указанного ответа на запрос новый Refresh JSON Web Token.
     *
     * Refresh JSON Web Token используется для обновления Access JSON Web Token,
     * после истечения его срока действия
     *
     * @param userDetails данные безопасности о пользователе
     * @param response    HttpServletResponse
     */
    public void addRefreshTokenCookie(UserDetails userDetails, HttpServletResponse response) {
        String refreshToken = generateRefreshToken(userDetails);
        addRefreshTokenCookie(refreshToken, response);
    }

    /**
     * Добавить в cookie указанного ответа на запрос Refresh JSON Web Token.
     *
     * Refresh JSON Web Token используется для обновления Access JSON Web Token,
     * после истечения его срока действия
     *
     * @param refreshToken Refresh JSON Web Token
     * @param response     HttpServletResponse
     */
    public void addRefreshTokenCookie(String refreshToken, HttpServletResponse response) {
        Cookie cookie = new Cookie(REFRESH_TOKEN, refreshToken);
        cookie.setHttpOnly(true); // Не доступен из JavaScript
        cookie.setPath(AuthRest.PATH);
        cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(cookiesLifetimeDays));

        response.addCookie(cookie);
    }
}
