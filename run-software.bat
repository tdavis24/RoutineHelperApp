@echo off

REM Set source and output directories
set SRC_DIR=src
set OUT_DIR=out

REM Compile all Java files found in the src directory
for /r "%SRC_DIR%" %%G in (*.java) do (
    javac -d "%OUT_DIR%" "%%G"
    if errorlevel 1 (
        echo Compilation failed for %%G.
        exit /b %errorlevel%
    )
)

REM Check if any .class files were generated
if not exist "%OUT_DIR%\*.class" (
    echo Compilation failed.
    exit /b 1
)

REM Execute the driver class (replace with the correct package and class name)
java -cp "%OUT_DIR%" path.to.driver.DriverClassName

REM Delete .class files after execution
del /Q "%OUT_DIR%\*.class" 2>nul

REM Prompt the user to press a key
echo.
echo Press any key to continue...
pause >nul