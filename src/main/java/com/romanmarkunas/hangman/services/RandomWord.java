package com.romanmarkunas.hangman.services;

import com.romanmarkunas.hangman.domain.Words;

import java.util.Random;

public class RandomWord {

    private final Words words;
    private final Random rn;


    public RandomWord(Words words) {

        this.words = words;
        this.rn = new Random(System.currentTimeMillis());
    }


    public String getNext() throws DomainAccessException {

        String randomWord;
        int repeatTimes = 5;

        do {
            int wordIndex = rn.nextInt(words.count());

            randomWord = words.get(wordIndex);

            if (randomWord != null) {

                return randomWord;
            }

            repeatTimes--;
        }
        while (repeatTimes > 0);

        throw new DomainAccessException("Unable to retrieve random word. Database is empty or concurrently modified");
    }
}
