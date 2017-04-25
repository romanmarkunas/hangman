package com.romanmarkunas.hangman.services;

import org.junit.Test;

public class DAOExceptionTest {

    @Test(expected=DAOException.class)
    public void forCoverage() throws Exception {

        throw new DAOException("Something terribly wrong!");
    }
}