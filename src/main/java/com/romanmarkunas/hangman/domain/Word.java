package com.romanmarkunas.hangman.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Simple object that is word for game. Not left as simple String for future extensions, i.e. could contain hint also.
 * No reason to check for String validity here, as when word is retrieved from database, it is safely assumed to be
 * valid.
 *
 * Internal structure is List as it maintains order, which makes it easy for random word retrieval. ArrayList
 * implementation is chosen since it saves time on word retrieval knowing its index, which is the most frequent
 * operation. CUD operations are expected to be rare. - TODO - move to DAO
 */
public class Word {

    private String string;


    public Word(String string) {

        this.string = string;
    }


    public String getString() { return string; }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || string == null || getClass() != o.getClass()) return false;

        Word other = (Word) o;

        return string.equals(other.string);
    }

    @Override
    public int hashCode() {

        return string != null ? string.hashCode() : 0;
    }
}
