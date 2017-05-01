package com.romanmarkunas.hangman.services;

import com.romanmarkunas.hangman.domain.HangmanGameState;

import java.util.List;

public interface HangmanGameStateDAO {

    List<HangmanGameState> getGames() throws DAOException;

    HangmanGameState getGame(int id) throws DAOException;

    int createNewGame(HangmanGameState state) throws DAOException;

    void updateGame(HangmanGameState state) throws DAOException;

    void removeGame(int id) throws DAOException;

    void removeAllOutdated() throws DAOException;
}
