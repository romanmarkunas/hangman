package com.romanmarkunas.hangman.infrastructure.console;

import com.romanmarkunas.hangman.applications.dictionary.Dictionary;
import com.romanmarkunas.hangman.domain.Word;
import com.romanmarkunas.hangman.services.WordDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.List;

public class DictionaryManager {

    public static void main(String[] args) {

        try {

            ApplicationContext context = new FileSystemXmlApplicationContext(
                    "src/main/java/com/romanmarkunas/hangman/infrastructure/console/beans.xml");
            WordDAO wordDao = (WordDAO) context.getBean("wordDao");
            Dictionary dictionary = new Dictionary(wordDao);

            // should print chicken, ostrich and penguin
            printWordList(dictionary.getAllWords());

            if(dictionary.removeWord("penguin")) System.out.println("removed");;
            printWordList(dictionary.getAllWords());

            if(dictionary.addWord("penguin")) System.out.println("added");;
            printWordList(dictionary.getAllWords());

        }
        catch (Exception exc) {

            exc.printStackTrace();
        }
    }

    private static void printWordList(List<Word> words) {

        for (Word word : words) {

            System.out.println(word.getString());
        }
    }
}
