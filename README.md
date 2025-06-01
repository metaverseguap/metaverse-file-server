# metaverse-file-server
## Запуск приложения
Для запуска приложения установите Java 17. Например, с сайта `https://wingetgui.com/apps/BellSoft-LibericaJDK-17`.

Затем запустите скрипт `StartServer.bat` в корневой папке проекта.

## Swagger документация
После запуска сервера, в логах появится порт, на котором был запущен сервер:
```text
o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8101 (http) with context path '/'
```
Документация swagger будет находиться по адресу:
```url
http://localhost:port/swagger-ui/index.html
```