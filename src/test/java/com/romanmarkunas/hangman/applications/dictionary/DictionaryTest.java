package com.romanmarkunas.hangman.applications.dictionary;

import com.romanmarkunas.hangman.domain.Word;
import com.romanmarkunas.hangman.services.DAOException;
import com.romanmarkunas.hangman.services.WordDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DictionaryTest {

    private Dictionary testDictionary;

    @Mock
    private WordDAO daoMock;

    @Mock
    private List<Word> databaseContents;


    @Before
    public void setUp() throws Exception {

        // mocks setup
        MockitoAnnotations.initMocks(this);
        when(daoMock.getWords()).thenReturn(databaseContents);

        // test instance
        testDictionary = new Dictionary(daoMock);
    }


    @Test
    public void addWordNullOrInvalid() throws Exception {

        assertFalse(testDictionary.addWord(null));
        assertFalse(testDictionary.addWord("panda!"));
        assertFalse(testDictionary.addWord("this is sentence"));
        verifyNoMoreInteractions(daoMock);
    }

    @Test
    public void addWordExistingWord() throws Exception {

        when(databaseContents.contains(any(Word.class))).thenReturn(true);

        assertFalse(testDictionary.addWord("elephant"));

        verify(daoMock, times(1)).getWords();
        verifyNoMoreInteractions(daoMock);
    }

    @Test
    public void addWordSuccessful() throws Exception {

        when(databaseContents.contains(any(Word.class))).thenReturn(false);

        assertTrue(testDictionary.addWord("elephant"));

        InOrder inOrder = Mockito.inOrder(daoMock);
        inOrder.verify(daoMock, times(1)).getWords();
        inOrder.verify(daoMock, times(1)).addWord(any(Word.class));
        verifyNoMoreInteractions(daoMock);
    }

    @Test
    public void addWordDAOException() throws Exception {

        when(databaseContents.contains(any(Word.class))).thenReturn(false);
        doThrow(DAOException.class).when(daoMock).addWord(any(Word.class));

        assertFalse(testDictionary.addWord("elephant"));
    }

    @Test
    public void removeWordNullOrInvalid() throws Exception {

        assertFalse(testDictionary.removeWord(null));
        assertFalse(testDictionary.removeWord("panda!"));
        assertFalse(testDictionary.removeWord("this is sentence"));
        verifyNoMoreInteractions(daoMock);
    }

    @Test
    public void removeWordNotExisting() throws Exception {

        when(databaseContents.contains(any(Word.class))).thenReturn(false);

        assertFalse(testDictionary.removeWord("elephant"));

        verify(daoMock, times(1)).getWords();
        verifyNoMoreInteractions(daoMock);
    }

    @Test
    public void removeWordSuccessful() throws Exception {

        when(databaseContents.contains(any(Word.class))).thenReturn(true);

        assertTrue(testDictionary.removeWord("elephant"));

        InOrder inOrder = Mockito.inOrder(daoMock);
        inOrder.verify(daoMock, times(1)).getWords();
        inOrder.verify(daoMock, times(1)).removeWord(any(Word.class));
        verifyNoMoreInteractions(daoMock);
    }

    @Test
    public void removeWordDAOException() throws Exception {

        when(databaseContents.contains(any(Word.class))).thenReturn(true);
        doThrow(DAOException.class).when(daoMock).removeWord(any(Word.class));

        assertFalse(testDictionary.removeWord("elephant"));
    }

    @Test
    public void getAllWordsSuccessful() throws Exception {

        assertTrue(databaseContents == testDictionary.getAllWords());
    }

    @Test
    public void getAllWordsDAOException() throws Exception {

        doThrow(DAOException.class).when(daoMock).getWords();

        assertEquals(null, testDictionary.getAllWords());
    }
}