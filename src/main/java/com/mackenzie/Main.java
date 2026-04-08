package com.mackenzie;

import com.mackenzie.controller.HuffmanController;

public class Main {
    public static void main(String[] args) {
        // O PDF exige 3 argumentos: modo (c/d), entrada e saída 
        if (args.length < 3) {
            System.out.println("Uso incorreto. Exemplos esperados:");
            System.out.println("Comprimir: java -jar huffman.jar c arquivo.txt arquivo.huff");
            System.out.println("Descomprimir: java -jar huffman.jar d arquivo.huff arquivo_res.txt");
            return;
        }

        String modo = args[0];
        String arquivoEntrada = args[1];
        String arquivoSaida = args[2];

        HuffmanController controller = new HuffmanController();
        controller.executar(modo, arquivoEntrada, arquivoSaida);
    }
}