@echo off
setlocal
cls

:: --- Variáveis de Caminho ---
set SRC_DIR=src\main\java
set TARGET_DIR=target
set CLASSES_DIR=%TARGET_DIR%\classes
set JAR_FILE=huffman.jar 
set MAIN_CLASS=com.mackenzie.Main

echo --- LIMPANDO ARQUIVOS ANTIGOS ---
if exist %TARGET_DIR% ( rmdir /S /Q %TARGET_DIR% )
if exist %JAR_FILE% ( del %JAR_FILE% )
mkdir %CLASSES_DIR%

echo --- COMPILANDO ARQUIVOS JAVA ---
dir /s /B "%SRC_DIR%\*.java" > sources.txt
javac -d %CLASSES_DIR% @sources.txt
if errorlevel 1 (
    echo *** ERRO NA COMPILACAO! ***
    del sources.txt
    exit /b
)
del sources.txt

echo --- CRIANDO O ARQUIVO JAR ---
jar cfe %JAR_FILE% %MAIN_CLASS% -C %CLASSES_DIR% .
if errorlevel 1 (
    echo *** ERRO AO CRIAR O JAR! ***
    exit /b
)

echo --- EXECUTANDO PROGRAMA ---
echo --- Comprimindo arq_de_teste.txt...
:: <-- Removido o hífen do 'c'
java -jar %JAR_FILE% c arq_de_teste.txt teste.huff

echo --- Descomprimindo teste.huff...
:: <-- Removido o hífen do 'd'
java -jar %JAR_FILE% d teste.huff teste_restaurado.txt

echo --- VERIFICANDO RESULTADO ---
fc /B arq_de_teste.txt teste_restaurado.txt > nul
if errorlevel 1 (
    echo *** FALHA: O arquivo original e o restaurado sao DIFERENTES. ***
) else (
    echo *** SUCESSO: O arquivo original e o restaurado sao IDENTICOS. ***
)

del teste.huff
del teste_restaurado.txt
endlocal