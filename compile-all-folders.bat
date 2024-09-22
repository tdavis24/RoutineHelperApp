@echo off

REM Set the source directory and output directory
set SRC_DIR=src
set OUT_DIR=out

REM Remove existing .class files in the output directory
del /Q "%OUT_DIR%\*.class"

REM Initialize a variable to track compilation errors
set "ERRORS="

REM Create a temporary file to track errors
set ERROR_FILE=%TEMP%\compile_errors.txt
if exist "%ERROR_FILE%" del "%ERROR_FILE%"

REM Compile Java files within src directory
for /r "%SRC_DIR%" %%G in (*.java) do (
    javac -d "%OUT_DIR%" "%%G"
    if errorlevel 1 (
        echo Compilation failed for %%G.
        echo %%G >> "%ERROR_FILE%"
    )
)

REM Check if there were any compilation errors
if not exist "%ERROR_FILE%" (
    echo Compilation successful.
) else (
    set /p ERRORS=<"%ERROR_FILE%"
    echo Compilation failed for the following files:%ERRORS%
    del "%ERROR_FILE%"
)

PAUSE