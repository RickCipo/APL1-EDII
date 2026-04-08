#!/bin/bash
clear

# --- Variáveis de Caminho ---
SRC_DIR="src/main/java"
TARGET_DIR="target"
CLASSES_DIR="${TARGET_DIR}/classes"
JAR_FILE="huffman.jar" # <-- Alterado para a raiz
MAIN_CLASS="com.mackenzie.Main"

echo "--- LIMPANDO ARQUIVOS ANTIGOS ---"
rm -rf "$TARGET_DIR"
rm -f "$JAR_FILE"
mkdir -p "$CLASSES_DIR"
echo "Diretório limpo e recriado."

echo "--- COMPILANDO ARQUIVOS JAVA ---"
find "$SRC_DIR" -name "*.java" -print | xargs javac -d "$CLASSES_DIR"
if [ $? -ne 0 ]; then
    echo "*** ERRO NA COMPILACAO! ***"
    exit 1
fi

echo "--- CRIANDO O ARQUIVO JAR ---"
jar cfe "$JAR_FILE" "$MAIN_CLASS" -C "$CLASSES_DIR" .
if [ $? -ne 0 ]; then
    echo "*** ERRO AO CRIAR O JAR! ***"
    exit 1
fi

echo "--- EXECUTANDO PROGRAMA ---"
echo "--- Comprimindo arq_de_teste.txt..."
# <-- Removido o hífen do 'c'
java -jar "$JAR_FILE" c arq_de_teste.txt teste.huff 

echo "--- Descomprimindo teste.huff..."
# <-- Removido o hífen do 'd'
java -jar "$JAR_FILE" d teste.huff teste_restaurado.txt 

echo "--- VERIFICANDO RESULTADO ---"
cmp -s arq_de_teste.txt teste_restaurado.txt
if [ $? -ne 0 ]; then
    echo "*** FALHA: O arquivo original e o restaurado sao DIFERENTES. ***"
else
    echo "*** SUCESSO: O arquivo original e o restaurado sao IDENTICOS. ***"
fi

echo "Limpando arquivos temporarios..."
rm -f teste.huff
rm -f teste_restaurado.txt