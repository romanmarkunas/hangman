package com.romanmarkunas.hangman.infrastructure.database_mysql;

import com.romanmarkunas.hangman.domain.Word;
import com.romanmarkunas.hangman.services.DAOException;
import com.romanmarkunas.hangman.services.WordDAO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLWordDAO implements WordDAO {

    private Connection connection;


    public MySQLWordDAO(Connection connection) {

        this.connection = connection;
    }


    @Override
    @SuppressFBWarnings
    public List<Word> getWords() throws DAOException {

        List<Word> words;

        try {
            ResultSet rs = connection.prepareStatement("SELECT * FROM words",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery();

            rs.last();
            words = new ArrayList<>(rs.getRow() + 1);
            rs.beforeFirst();

            while (rs.next()) {

                words.add(new Word(rs.getString(1)));
            }

            rs.close();
        }
        catch (SQLException exc) {

            throw new DAOException("Error while retrieving words from database: " + exc.getMessage());
        }

        return words;
    }

    @Override
    @SuppressFBWarnings
    public void addWord(Word word) throws DAOException {

        try {
            connection.prepareStatement("INSERT INTO words (word) values ('" + word.getString() + "')").executeUpdate();
        }
        catch (SQLException exc) {

            throw new DAOException("Error while adding word " + word.getString() + " to database: " + exc.getMessage());
        }
    }

    @Override
    @SuppressFBWarnings
    public void removeWord(Word word) throws DAOException {

        try {
            connection.prepareStatement("DELETE FROM words WHERE word = '" + word.getString() + "'").executeUpdate();
        }
        catch (SQLException exc) {

            throw new DAOException("Error while removing word " + word.getString() + " from database: " + exc.getMessage());
        }
    }
}
