@echo off
rem --------------------------------------------------
rem ordenpor.bat - Lista archivos ordenados por nombre/ext/tam
rem Uso: ejecutar y elegir "nom", "ext" o "tam"
rem --------------------------------------------------

title Ordenar por (nombre | ext | tam)
setlocal

echo Elige: nom, ext o tam
set /p "nombre=Opcion: "

if /i "%nombre%"=="nom" goto por_nom
if /i "%nombre%"=="ext" goto por_ext
if /i "%nombre%"=="tam" goto por_tam

echo Opcion no valida. Usa nom, ext o tam.
pause
exit /b 1

:por_nom
echo Mostrando archivos ordenados por NOMBRE...
dir /b /o:n
goto fin

:por_ext
echo Mostrando archivos ordenados por EXTENSION...
dir /b /o:e
goto fin

:por_tam
echo Mostrando archivos ordenados por TAMANO (menor->mayor)...
dir /b /o:s
goto fin

:fin
echo.
pause
endlocal
exit /b 0
