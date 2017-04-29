package com.romanmarkunas.hangman.domain;

import java.time.LocalDateTime;

public class HangmanGameState {

    private final int id;
    private final String secretWord;
    private final String revealedWord;
    private final int triesLeft;
    private final String notUsedChars;
    private final LocalDateTime expiryDate;


    public HangmanGameState(int id, String secretWord, String revealedWord, int triesLeft, String notUsedChars,
                            LocalDateTime expiryDate) {

        this.id = id;
        this.secretWord = secretWord;
        this.revealedWord = revealedWord;
        this.triesLeft = triesLeft;
        this.notUsedChars = notUsedChars;
        this.expiryDate = expiryDate;
    }


    public int getId() { return id; }

    public String getSecretWord() { return secretWord; }

    public String getRevealedWord() { return revealedWord; }

    public int getTriesLeft() { return triesLeft; }

    public String getNotUsedChars() { return notUsedChars; }

    public LocalDateTime getExpiryDate() { return expiryDate; }
}
