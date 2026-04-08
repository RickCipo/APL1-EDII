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

public class No implements Comparable<No>{
    private char caractere;
    private int frequencia;
    private No esquerda;
    private No direita;

    // nó folha
    public No(char caractere, int frequencia) {
        this.caractere = caractere;
        this.frequencia = frequencia;
    }

    //nó sem caractere (interno)
    public No(int frequencia, No esquerda, No direita) {
        this.frequencia = frequencia;
        this.esquerda = esquerda;
        this.direita = direita;
    }

    public char getCaractere() {
        return caractere;
    }

    public void setCaractere(char caractere) {
        this.caractere = caractere;
    }

    public int getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(int frequencia) {
        this.frequencia = frequencia;
    }

    public No getDireita() {
        return direita;
    }

    public void setDireita(No direita) {
        this.direita = direita;
    }

    public No getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(No esquerda) {
        this.esquerda = esquerda;
    }

    public boolean isFolha() {
        return this.esquerda == null && this.direita == null;
    }

    @Override
    public String toString(){
        // Formatação exata exigida pelo PDF para a Etapa 2
        if(isFolha())
            return "No('" + this.caractere + "', " + this.frequencia + ")";
        return "No(interno, " + this.frequencia + ")";
    }

    @Override
    public int compareTo(No outroNo) {
        return Integer.compare(this.frequencia, outroNo.frequencia);
    }
}