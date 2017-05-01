package com.romanmarkunas.hangman.infrastructure.database_mysql;

import com.romanmarkunas.hangman.domain.Word;
import com.romanmarkunas.hangman.services.WordDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class MySQLWordDAOTest {

    private WordDAO testInstance;

    @Mock private DataSource sourceMock;
    @Mock private Connection connectionMock;
    @Mock private PreparedStatement statementMock;
    @Mock private Word wordMock;
    @Mock private ResultSet resultMock;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        when(sourceMock.getConnection()).thenReturn(connectionMock);
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);

        testInstance = new MySQLWordDAO(sourceMock);
    }

    @Test
    public void getWords() throws Exception {

        String secretWord = "lightsaber";

        when(statementMock.executeQuery()).thenReturn(resultMock);
        when(resultMock.getRow()).thenReturn(1);
        when(resultMock.next()).thenReturn(true).thenReturn(false);
        when(resultMock.getString("word")).thenReturn(secretWord);

        List<Word> words = testInstance.getWords();

        assertEquals(1, words.size());
        assertEquals(secretWord, words.get(0).getString());

    }

    @Test(expected = DataAccessException.class)
    public void getWordsFails() throws Exception {

        doThrow(SQLException.class).when(sourceMock).getConnection();

        testInstance.getWords();
    }

    @Test
    public void addWord() throws Exception {

        testInstance.addWord(wordMock);

        verify(statementMock, times(1)).executeUpdate();
    }

    @Test
    public void removeWord() throws Exception {

        testInstance.removeWord(wordMock);

        verify(statementMock, times(1)).executeUpdate();
    }
}