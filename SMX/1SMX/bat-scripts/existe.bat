@echo off
rem --------------------------------------------------
rem existe.bat - Comprueba si existe un fichero o carpeta
rem Uso: doble clic o ejecutar desde consola y escribir nombre (con o sin ruta)
rem --------------------------------------------------

title Comprobar existencia de archivo/carpeta
setlocal enabledelayedexpansion

:: Pedimos el nombre (acepta espacios)
set /p "archivo=Escribe el nombre (ruta o nombre con extensión): "

:: Si el usuario no escribe nada, avisamos y salimos
if "%archivo%"=="" (
    echo No se ha introducido ningun nombre. Saliendo...
    pause
    endlocal
    exit /b 1
