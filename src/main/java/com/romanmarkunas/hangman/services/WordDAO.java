package com.romanmarkunas.hangman.services;

import com.romanmarkunas.hangman.domain.Word;

import java.util.List;

public interface WordDAO {

    List<Word> getWords() throws DAOException;

    void addWord(Word word) throws DAOException;

    void removeWord(Word word) throws DAOException;
}
