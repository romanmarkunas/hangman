package com.romanmarkunas.hangman.services;

import com.romanmarkunas.hangman.domain.Word;

import java.util.List;
import java.util.Random;

public class RandomWord {

    private final List<Word> words;
    private final Random rn;


    public RandomWord(List<Word> words) {

        this.words = words;
        this.rn = new Random(System.currentTimeMillis());
    }


    public Word getNext() {

        return words.get(rn.nextInt(words.size()));
    }
}
