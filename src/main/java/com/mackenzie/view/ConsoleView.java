/**
 * Projeto 1 de Estrutura de Dados II.
 * Prof. Joaquim Pessoa Filho.
 *
 * Integrantes do grupo:
 * - Gabriel Ferreira - RA: 10442043.
 * - Enrique Cipolla Martins - RA: 10427834
 * - Leonardo Binder - RA: 10402289.
 */

package com.mackenzie.view;

import com.mackenzie.model.No;

public class ConsoleView {

    public void imprimirTabelaFrequencia(int[] tabela) {
        System.out.println("ETAPA 1: Tabela de Frequencia de Caracteres");
        for (int i = 0; i < tabela.length; i++) {
            if (tabela[i] > 0) {
                System.out.println("Caractere '" + (char)i + "' (ASCII: " + i + "): " + tabela[i]);
            }
        }
        System.out.println();
    }

    public void imprimirMinHeapInicial(String heapStr) {
        System.out.println("ETAPA 2: Min-Heap Inicial (Vetor)");
        System.out.println(heapStr);
        System.out.println();
    }

    public void imprimirArvore(No raiz) {
        System.out.println("ETAPA 3: Arvore de Huffman");
        imprimirArvoreRecursivo(raiz, "");
        System.out.println();
    }

    private void imprimirArvoreRecursivo(No no, String prefixo) {
        if (no == null) return;
        
        String infoNo;
        if (no.isFolha()) {
            infoNo = "'" + no.getCaractere() + "'";
        } else {
            // Se for a primeira chamada (prefixo vazio), é a RAIZ, senão é nó interno (N)
            infoNo = prefixo.isEmpty() ? "RAIZ" : "N"; 
        }
        
        System.out.println(prefixo + "(" + infoNo + ", " + no.getFrequencia() + ")");

        if (!no.isFolha()) {
            imprimirArvoreRecursivo(no.getEsquerda(), prefixo + "  ");
            imprimirArvoreRecursivo(no.getDireita(), prefixo + "  ");
        }
    }

    public void imprimirTabelaCodigos(String[] tabela) {
        System.out.println("ETAPA 4: Tabela de Codigos de Huffman");
        for (int i = 0; i < tabela.length; i++) {
            if (tabela[i] != null) {
                System.out.println("Caractere '" + (char)i + "': " + tabela[i]);
            }
        }
        System.out.println();
    }

    public void imprimirResumoCompressao(long bytesOriginais, long bitsComprimidos, long bytesComprimidos) {
        System.out.println("ETAPA 5: Resumo da Compressao");
        
        long bitsOriginais = bytesOriginais * 8;
        // Calculo da taxa conforme a fórmula do PDF (1 - (tamanhoComprimido / tamanhoOriginal)) * 100
        double taxa = (1.0 - ((double)bytesComprimidos / bytesOriginais)) * 100.0;
        
        System.out.printf("Tamanho original....: %d bits (%d bytes)\n", bitsOriginais, bytesOriginais);
        System.out.printf("Tamanho comprimido..: %d bits (%d bytes)\n", bitsComprimidos, bytesComprimidos);
        System.out.printf("Taxa de compressao..: %.2f%%\n", taxa);
        System.out.println();
    }
}