/**
 * Projeto 1 de Estrutura de Dados II.
 * Prof. Joaquim Pessoa Filho.
 *
 * Integrantes do grupo:
 * - Gabriel Ferreira - RA: 10442043.
 * - Enrique Cipolla Martins - RA: 10427834
 * - Leonardo Binder - RA: 10402289.
 */

package com.mackenzie.utils;

import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream implements AutoCloseable {
    private final OutputStream out;
    private int buffer;
    private int count;

    public BitOutputStream(OutputStream out) {
        this.out = out;
    }

    public void writeBit(int bit) throws IOException {
        buffer = (buffer << 1) | bit;
        count++;
        if (count == 8) {
            out.write(buffer);
            count = 0;
            buffer = 0;
        }
    }

    @Override
    public void close() throws IOException {
        if (count > 0) {

            out.write(buffer << (8 - count));
        }
        out.close();
    }
}