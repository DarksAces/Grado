@echo off
rem --------------------------------------------------
rem ver-archivos.bat - Muestra el contenido de .txt y .bat en el directorio
rem Uso: ejecutar en la carpeta deseada
rem --------------------------------------------------

title Ver archivos (.txt y .bat)

if not exist "*.txt" if not exist "*.bat" (
    echo No hay archivos .txt ni .bat en este directorio.
    pause
    exit /b 0
)

for %%f in (*.txt *.bat) do (
    echo ===========================
    echo Mostrando: "%%f"
    echo ---------------------------
    type "%%f"
    echo.
)

echo Fin.
pause
exit /b 0
