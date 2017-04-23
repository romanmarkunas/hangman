package com.romanmarkunas.hangman.applications.dictionary;

import com.romanmarkunas.hangman.domain.Word;
import com.romanmarkunas.hangman.services.DAOException;
import com.romanmarkunas.hangman.services.WordDAO;

import java.util.List;

public class Dictionary {

    private WordDAO wordDao;


    public Dictionary(WordDAO wordDao) {

        this.wordDao = wordDao;
    }


    public List<Word> getAllWords() {

        try {

            return wordDao.getWords();

        }
        catch (DAOException exc) {

            return null;
        }
    }

    public boolean addWord(String word) {

        if (word != null && validWord(word)) {

            try {

                Word newWord = new Word(word.toLowerCase());
                List<Word> words = wordDao.getWords();

                if (!words.contains(newWord)) {

                    wordDao.addWord(newWord);
                    return true;
                }

            } catch (DAOException exc) {

                return false;
            }
        }

        return false;
    }

    public boolean removeWord(String word) {

        if (word != null && validWord(word.toLowerCase())) {

            try {

                Word wordToRemove = new Word(word.toLowerCase());
                List<Word> words = wordDao.getWords();

                if (words.contains(wordToRemove)) {

                    wordDao.removeWord(wordToRemove);
                    return true;
                }

            }
            catch (DAOException exc) {

                return false;
            }
        }

        return false;
    }


    private boolean validWord(String word) {

        return word.matches("[a-zA-Z]+");
    }
}
