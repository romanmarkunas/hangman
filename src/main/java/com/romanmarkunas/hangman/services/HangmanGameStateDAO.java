package com.romanmarkunas.hangman.services;

import com.romanmarkunas.hangman.domain.HangmanGameState;

import java.util.List;

public interface HangmanGameStateDAO {

    List<HangmanGameState> getGames() throws DAOException;

    HangmanGameState getGame(int id) throws DAOException;

    int createNewGame() throws DAOException;

    void updateGame(int id) throws DAOException;

    void removeGame(int id) throws DAOException;

    void removeAllOutdated() throws DAOException;
}
