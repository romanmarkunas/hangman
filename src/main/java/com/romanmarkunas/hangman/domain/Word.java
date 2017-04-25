package com.romanmarkunas.hangman.domain;

/*
 * Simple object that is word for game. Not left as simple String for future extensions, i.e. could contain hint also.
 * No reason to check for String validity here, as when word is retrieved from database_mysql, it is safely assumed to
 * be valid.
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
