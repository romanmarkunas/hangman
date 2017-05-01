package com.romanmarkunas.hangman.infrastructure.database_mysql;

import com.romanmarkunas.hangman.domain.HangmanGameState;
import com.romanmarkunas.hangman.services.HangmanGameStateDAO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class MySQLHangmanGameStateDAOTest {

    private HangmanGameStateDAO testInstance;

    @Mock private DataSource sourceMock;
    @Mock private Connection connectionMock;
    @Mock private PreparedStatement statementMock;
    @Mock private ResultSet resultMock;
    @Mock private HangmanGameState gameStateMock;

    private int id = 5;
    private String secretWord = "wellness";
    private String revealedWord = "__ll__ss";
    private int triesLeft = 0;
    private String notUsedChars = "enw";
    private Timestamp date = new java.sql.Timestamp(0L);


    @Before
    @SuppressFBWarnings
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        when(sourceMock.getConnection()).thenReturn(connectionMock);
        when(connectionMock.prepareStatement(anyString())).thenReturn(statementMock);
        when(connectionMock.prepareStatement(anyString(), anyInt())).thenReturn(statementMock);

        testInstance = new MySQLHangmanGameStateDAO(sourceMock);

        gameStateMock = mock(HangmanGameState.class);
        when(gameStateMock.getId()).thenReturn(id);
        when(gameStateMock.getSecretWord()).thenReturn(secretWord);
        when(gameStateMock.getRevealedWord()).thenReturn(revealedWord);
        when(gameStateMock.getTriesLeft()).thenReturn(triesLeft);
        when(gameStateMock.getNotUsedChars()).thenReturn(notUsedChars);
        when(gameStateMock.getExpiryDate()).thenReturn(LocalDateTime.now());
    }

    @Test
    @SuppressFBWarnings
    public void getGames() throws Exception {

        when(statementMock.executeQuery()).thenReturn(resultMock);
        when(resultMock.next()).thenReturn(true).thenReturn(false);
        when(resultMock.getInt("id")).thenReturn(id);
        when(resultMock.getString("secretword")).thenReturn(secretWord);
        when(resultMock.getString("revealedword")).thenReturn(revealedWord);
        when(resultMock.getInt("triesleft")).thenReturn(triesLeft);
        when(resultMock.getString("notusedchars")).thenReturn(notUsedChars);
        when(resultMock.getTimestamp("expirydate")).thenReturn(date);

        List<HangmanGameState> games = testInstance.getGames();

        assertEquals(1, games.size());
        assertEquals(gameStateMock.getId(), games.get(0).getId());
        assertEquals(gameStateMock.getRevealedWord(), games.get(0).getRevealedWord());
        assertEquals(gameStateMock.getTriesLeft(), games.get(0).getTriesLeft());
    }

    @Test
    @SuppressFBWarnings
    public void getGame() throws Exception {

        when(statementMock.executeQuery()).thenReturn(resultMock);
        when(resultMock.next()).thenReturn(true).thenReturn(false);
        when(resultMock.getInt("id")).thenReturn(id);
        when(resultMock.getString("secretword")).thenReturn(secretWord);
        when(resultMock.getString("revealedword")).thenReturn(revealedWord);
        when(resultMock.getInt("triesleft")).thenReturn(triesLeft);
        when(resultMock.getString("notusedchars")).thenReturn(notUsedChars);
        when(resultMock.getTimestamp("expirydate")).thenReturn(date);

        HangmanGameState game = testInstance.getGame(id);

        assertEquals(gameStateMock.getId(), game.getId());
        assertEquals(gameStateMock.getRevealedWord(), game.getRevealedWord());
        assertEquals(gameStateMock.getTriesLeft(),game.getTriesLeft());
    }

    @Test
    @SuppressFBWarnings
    public void createNewGame() throws Exception {

        testInstance.createNewGame(gameStateMock);
    }

    @Test
    @SuppressFBWarnings
    public void updateGame() throws Exception {

        testInstance.updateGame(gameStateMock);
    }

    @Test(expected = DataAccessException.class)
    @SuppressFBWarnings
    public void updateGameFails() throws Exception {

        doThrow(SQLException.class).when(sourceMock).getConnection();

        testInstance.updateGame(gameStateMock);
    }

    @Test
    @SuppressFBWarnings
    public void removeGame() throws Exception {

        testInstance.removeGame(id);
    }

    @Test
    @SuppressFBWarnings
    public void removeAllOutdated() throws Exception {

        testInstance.removeAllOutdated();
    }
}