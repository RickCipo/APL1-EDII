/**
 * Projeto 1 de Estrutura de Dados II.
 * Prof. Joaquim Pessoa Filho.
 *
 * Integrantes do grupo:
 * - Gabriel Ferreira - RA: 10442043.
 * - Enrique Cipolla Martins - RA: 10427834
 * - Leonardo Binder - RA: 10402289.
 */

package com.mackenzie.controller;

import com.mackenzie.model.Huffman;
import com.mackenzie.view.ConsoleView;
import java.io.File;
import java.io.IOException;

public class HuffmanController {

    private final Huffman model = new Huffman();
    private final ConsoleView view = new ConsoleView();

    private void processoDeCompressao(String arquivoEntrada, String arquivoSaida) throws IOException {
        File fEntrada = new File(arquivoEntrada);
        if (!fEntrada.exists()) throw new IOException("Arquivo de entrada não encontrado.");

        // Medição de tempo conforme requisito 7 [cite: 167, 169]
        long tempoInicio = System.nanoTime();

        // Execução do algoritmo passo a passo
        model.analisarFrequencia(arquivoEntrada); // Passo 1 [cite: 31]
        model.construirArvore();                  // Passo 2 e 3 [cite: 34, 60]
        model.gerarTabelaDeCodigos();             // Passo 4 [cite: 62]
        model.comprimirArquivo(arquivoEntrada, arquivoSaida); // Passo 5 [cite: 64]

        long tempoFim = System.nanoTime();

        // Exibição das etapas obrigatórias no console [cite: 124, 127, 130, 132, 139, 143]
        view.imprimirTabelaFrequencia(model.getTabelaFrequencia());
        view.imprimirMinHeapInicial(model.getMinHeapInicialStr()); 
        view.imprimirArvore(model.getArvoreHuffman());
        view.imprimirTabelaCodigos(model.getTabelaCodigos());

        File fSaida = new File(arquivoSaida);
        // O resumo deve incluir bits e bytes conforme o exemplo do PDF [cite: 145]
        view.imprimirResumoCompressao(fEntrada.length(), model.getBitsComprimidos(), fSaida.length());

        double duracaoMs = (tempoFim - tempoInicio) / 1_000_000.0;
        System.out.printf("Tempo total de compressão: %.2f ms\n", duracaoMs);
        System.out.println("\n*** SUCESSO: Arquivo comprimido em " + arquivoSaida + " ***");
    }

    private void processoDeDescompressao(String arquivoEntrada, String arquivoSaida) throws IOException {
        long tempoInicio = System.nanoTime();

        model.descomprimirArquivo(arquivoEntrada, arquivoSaida); // Parte 2 e 3 [cite: 67, 69]

        long tempoFim = System.nanoTime();
        double duracaoMs = (tempoFim - tempoInicio) / 1_000_000.0;

        System.out.printf("Tempo total de descompressão: %.2f ms\n", duracaoMs);
        System.out.println("\n*** SUCESSO: Arquivo restaurado em " + arquivoSaida + " ***");
    }

    public void executar(String modo, String arquivoEntrada, String arquivoSaida) {
        try {
            // Suporta 'c'/'d' conforme PDF ou '-c'/'-d' conforme seus scripts atuais
            if (modo.equalsIgnoreCase("c") || modo.equalsIgnoreCase("-c")) {
                processoDeCompressao(arquivoEntrada, arquivoSaida);
            } else if (modo.equalsIgnoreCase("d") || modo.equalsIgnoreCase("-d")) {
                processoDeDescompressao(arquivoEntrada, arquivoSaida);
            } else {
                System.out.println("Modo inválido. Use 'c' (comprimir) ou 'd' (descomprimir).");
            }
        } catch (Exception e) {
            System.err.println("Erro durante a execução: " + e.getMessage());
        }
    }
}