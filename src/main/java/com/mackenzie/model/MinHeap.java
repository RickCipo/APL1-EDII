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

import java.util.ArrayList;
import java.util.Collections;

public class MinHeap {
    private ArrayList<No> heap = new ArrayList<>();

    // Novo método para expor o vetor e permitir a impressão na Etapa 2
    public ArrayList<No> getHeap() {
        return heap;
    }

    private void heapifyUp(int index) {
        int pai = (index - 1) / 2;
        if (index > 0 && heap.get(index).compareTo(heap.get(pai)) < 0) {
            Collections.swap(heap, index, pai);
            heapifyUp(pai);
        }
    }

    private void heapifyDown(int index) {
        int esquerda = 2 * index + 1;
        int direita = 2 * index + 2;

        int menor = index;

        if (esquerda < heap.size() && heap.get(esquerda).compareTo(heap.get(menor)) < 0)
            menor = esquerda;

        if (direita < heap.size() && heap.get(direita).compareTo(heap.get(menor)) < 0)
            menor = direita;

        if (menor != index) {
            Collections.swap(heap, index, menor);
            heapifyDown(menor);
        }
    }

    public int size() {
        return heap.size();
    }

    public void inserir(No no) {
        heap.add(no);
        heapifyUp(heap.size() - 1);
    }

    public No removerMin() {
        if (heap.isEmpty()) return null;

        No min = heap.get(0);
        No ultimo = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, ultimo);
            heapifyDown(0);
        }

        return min;
    }
}