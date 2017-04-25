package com.romanmarkunas.hangman.infrastructure.database_mysql;

import com.romanmarkunas.hangman.domain.Word;
import com.romanmarkunas.hangman.services.DAOException;
import com.romanmarkunas.hangman.services.WordDAO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class MySQLWordDAOTest {

    private WordDAO testInstance;

    @Mock
    private Connection connectionMock;
    @Mock
    private PreparedStatement statementMock;
    @Mock
    private Word wordMock;
    @Mock
    private ResultSet resultMock;


    @Before
    @SuppressFBWarnings
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
        when(connectionMock.prepareStatement(anyString(), anyInt(), anyInt())).thenReturn(statementMock);

        testInstance = new MySQLWordDAO(connectionMock);
    }

    @Test
    @SuppressFBWarnings
    public void getWords() throws Exception {

        String secretWord = "lightsaber";

        when(statementMock.executeQuery()).thenReturn(resultMock);
        when(resultMock.getRow()).thenReturn(1);
        when(resultMock.next()).thenReturn(true).thenReturn(false);
        when(resultMock.getString(1)).thenReturn(secretWord);

        List<Word> words = testInstance.getWords();
        assertEquals(1, words.size());
        assertEquals(secretWord, words.get(0).getString());
    }

    @Test(expected = DAOException.class)
    @SuppressFBWarnings
    public void getWordsFails() throws Exception {

        doThrow(SQLException.class).when(statementMock).executeQuery();

        testInstance.getWords();
    }

    @Test
    @SuppressFBWarnings
    public void addWord() throws Exception {

        testInstance.addWord(wordMock);

        verify(statementMock, times(1)).executeUpdate();
    }

    @Test(expected = DAOException.class)
    @SuppressFBWarnings
    public void addWordFails() throws Exception {

        doThrow(SQLException.class).when(statementMock).executeUpdate();

        testInstance.addWord(wordMock);
    }

    @Test
    @SuppressFBWarnings
    public void removeWord() throws Exception {

        testInstance.removeWord(wordMock);

        verify(statementMock, times(1)).executeUpdate();
    }

    @Test(expected = DAOException.class)
    @SuppressFBWarnings
    public void removeWordFails() throws Exception {

        doThrow(SQLException.class).when(statementMock).executeUpdate();

        testInstance.removeWord(wordMock);
    }
}