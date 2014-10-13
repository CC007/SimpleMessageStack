/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.simplemessagestack.exceptions;

/**
 *
 * @author Rik
 */
public class checksumErrorException extends RuntimeException {

    /**
     * Creates a new instance of <code>checksumErrorException</code> without
     * detail message.
     */
    public checksumErrorException() {
    }

    /**
     * Constructs an instance of <code>checksumErrorException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public checksumErrorException(String msg) {
        super(msg);
    }
}
