@echo off
setlocal enabledelayedexpansion

set "REQUIRED_JAVA_VERSION=17"
set "DOWNLOAD_LINK=https://wingetgui.com/apps/BellSoft-LibericaJDK-17"

echo.
echo === STEP 1: Checking if Java is installed ===
where java >nul 2>nul
if errorlevel 1 (
    echo.
    echo [ERROR] Java was not found on this system.
    echo Java %REQUIRED_JAVA_VERSION% is required to run this application.
    echo Please download it from:
    echo %DOWNLOAD_LINK%
    echo.
    pause
    exit /b 1
) else (
     echo Java runtime environment detected on this system.
 )

echo.
echo === STEP 2: Validating Java version ===

:: Получаем первую строку вывода java -version
set "JAVA_VERSION_LINE="
for /f "usebackq tokens=*" %%a in (`java -version 2^>^&1`) do (
    if not defined JAVA_VERSION_LINE (
        set "JAVA_VERSION_LINE=%%a"
    )
)

:: Проверяем, что строка не пустая
if not defined JAVA_VERSION_LINE (
    echo.
    echo [ERROR] Unable to retrieve Java version information.
    echo Java %REQUIRED_JAVA_VERSION% is required to run this application.
    echo Please ensure Java is properly installed and in PATH.
    echo.
    pause
    exit /b 1
)

echo Java version info: %JAVA_VERSION_LINE%

:: Извлекаем версию из вывода
set "VERSION="
for /f "tokens=3" %%i in ('java -version 2^>^&1 ^| findstr "version"') do (
    set "VERSION=%%~i"
)

:: Удаляем кавычки
set "VERSION=%VERSION:"=%"

echo Detected Java version: %VERSION%

:: Extract major version number
for /f "tokens=1 delims=." %%j in ('echo %VERSION%') do (
    set "MAJOR=%%j"
)

echo Major version: !MAJOR!

if "!MAJOR!" NEQ "!REQUIRED_JAVA_VERSION!" (
    echo.
    echo [ERROR] Incompatible Java version detected: %VERSION%
    echo Java %REQUIRED_JAVA_VERSION% is required to run this application.
    echo Please install the correct version from:
    echo %DOWNLOAD_LINK%
    echo.
    pause
    exit /b 1
)else (
     echo.
     echo The required Java version %REQUIRED_VERSION% has been detected on this system.
     echo The installed version matches the specified requirement;
     echo therefore, the application is eligible for compilation.
 )

echo.
echo === STEP 3: Building and running the application with Gradle ===

call gradlew.bat build
if errorlevel 1 (
    echo.
    echo [ERROR] Build failed.
    pause
    exit /b %errorlevel%
)

call gradlew.bat bootRun

endlocal