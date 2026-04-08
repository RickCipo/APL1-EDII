/**
 * Projeto 1 de Estrutura de Dados II.
 * Prof. Joaquim Pessoa Filho.
 *
 * Integrantes do grupo:
 * - Gabriel Ferreira - RA: 10442043.
 * - Enrique Cipolla Martins - RA: 10427834
 * - Leonardo Binder - RA: 10402289.
 */

package com.mackenzie.model;

import java.io.*;
import com.mackenzie.utils.*;

public class Huffman {

    private int[] tabelaFrequencia = new int[256];
    private String[] tabelaCodigos = new String[256];
    private No arvoreHuffman;
    
    // Variáveis adicionadas para suportar as saídas visuais exigidas
    private String minHeapInicialStr;
    private int bitsComprimidos;

    private void gerarCodigosRecursivo(No no, String codigo) {
        if (no == null) return;
        if (no.isFolha()) {
            tabelaCodigos[no.getCaractere()] = codigo;
            return;
        }
        gerarCodigosRecursivo(no.getEsquerda(), codigo + "0");
        gerarCodigosRecursivo(no.getDireita(), codigo + "1");
    }

    public int[] getTabelaFrequencia() {
        return tabelaFrequencia;
    }

    public String[] getTabelaCodigos() {
        return tabelaCodigos;
    }

    public No getArvoreHuffman() {
        return arvoreHuffman;
    }

    public String getMinHeapInicialStr() {
        return minHeapInicialStr;
    }

    public int getBitsComprimidos() {
        return bitsComprimidos;
    }

    public void analisarFrequencia(String caminhoArquivoEntrada) throws IOException {
        try (FileInputStream fis = new FileInputStream(caminhoArquivoEntrada)) {
            int byteLido;
            while ((byteLido = fis.read()) != -1) {
                tabelaFrequencia[byteLido]++;
            }
        }
    }

    public void construirArvore() {
        MinHeap minHeap = new MinHeap();
        for (int i = 0; i < tabelaFrequencia.length; i++) {
            if (tabelaFrequencia[i] > 0) {
                minHeap.inserir(new No((char) i, tabelaFrequencia[i]));
            }
        }

        // Captura o estado do Heap logo após inserir as folhas (Etapa 2 do PDF)
        this.minHeapInicialStr = minHeap.getHeap().toString();

        while (minHeap.size() > 1) {
            No esquerda = minHeap.removerMin();
            No direita = minHeap.removerMin();
            int somaFrequencia = esquerda.getFrequencia() + direita.getFrequencia();
            No pai = new No(somaFrequencia, esquerda, direita);
            minHeap.inserir(pai);
        }
        this.arvoreHuffman = minHeap.removerMin();
    }

    public void gerarTabelaDeCodigos() {
        gerarCodigosRecursivo(this.arvoreHuffman, "");
    }

    public void comprimirArquivo(String caminhoEntrada, String caminhoSaida) throws IOException {
        try (FileInputStream fis = new FileInputStream(caminhoEntrada);
             ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoSaida))) {

            oos.writeObject(tabelaFrequencia);

            StringBuilder bits = new StringBuilder();
            fis.getChannel().position(0);
            int byteLido;
            while ((byteLido = fis.read()) != -1) {
                bits.append(tabelaCodigos[byteLido]);
            }

            // Salva a quantidade de bits processados para exibir no resumo (Etapa 5)
            this.bitsComprimidos = bits.length();
            oos.writeInt(bits.length());

            try (BitOutputStream bos = new BitOutputStream(oos)) {
                for (int i = 0; i < bits.length(); i++) {
                    bos.writeBit(bits.charAt(i) - '0');
                }
            }
        }
    }

    public void descomprimirArquivo(String caminhoEntrada, String caminhoSaida) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoEntrada));
             FileOutputStream fos = new FileOutputStream(caminhoSaida)) {

            this.tabelaFrequencia = (int[]) ois.readObject();
            construirArvore();

            int totalBits = ois.readInt();
            int bitsLidos = 0;
            No noAtual = this.arvoreHuffman;

            if (noAtual == null && totalBits > 0) {
                throw new IOException("A árvore de Huffman não pôde ser reconstruída, mas há dados para decodificar.");
            } else if (noAtual == null) {
                return;
            }

            try (BitInputStream bis = new BitInputStream(ois)) {
                while (bitsLidos < totalBits) {
                    int bit = bis.readBit();
                    if (bit == -1) break;

                    bitsLidos++;
                    noAtual = (bit == 0) ? noAtual.getEsquerda() : noAtual.getDireita();

                    if (noAtual.isFolha()) {
                        fos.write(noAtual.getCaractere());
                        noAtual = this.arvoreHuffman;
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            throw new IOException("Arquivo de cabeçalho inválido.", e);
        }
    }
}