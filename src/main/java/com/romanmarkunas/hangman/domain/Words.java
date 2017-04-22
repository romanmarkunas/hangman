package com.romanmarkunas.hangman.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Simple object that hold available words for games. Contains rules which words do.
 *
 * Internal structure is List as it maintains order, which makes it easy for random word retrieval. ArrayList
 * implementation is chosen since it saves time on word retrieval knowing its index, which is the most frequent
 * operation. CUD operations are expected to be rare.
 */
public class Words {

    private List<String> words;


    public Words() {

        words = Collections.synchronizedList(new ArrayList<>());
    }


    public void add(String word) {

        if (word != null && validWord(word) && !words.contains(word)) {

            words.add(word);
        }
    }

    public String get(int index) {

        if (index > words.size()) {

            return null;
        }

        return words.get(index);
    }

    public void update(String oldWord, String newWord) {

        if (oldWord != null && newWord != null && validWord(newWord)) {

            int oldIndex = words.indexOf(oldWord);

            if (oldIndex != -1) {

                words.set(oldIndex, newWord);
            }
        }
    }

    public void remove(String word) {

        words.remove(word);
    }

    public int count() {

        return words.size();
    }


    private boolean validWord(String word) {

        return word.matches("[a-zA-Z]+");
    }
}
