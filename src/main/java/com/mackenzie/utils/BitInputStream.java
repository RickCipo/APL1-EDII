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
import java.io.InputStream;

public class BitInputStream implements AutoCloseable {
    private final InputStream in;
    private int buffer;
    private int count;

    public BitInputStream(InputStream in) {
        this.in = in;
        this.count = 0;
    }

    public int readBit() throws IOException {
        if (count == 0) {
            buffer = in.read();
            if (buffer == -1) {
                return -1;
            }
            count = 8;
        }
        count--;

        return (buffer >> count) & 1;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}