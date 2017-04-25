package com.romanmarkunas.hangman.infrastructure.console;

import com.romanmarkunas.hangman.applications.dictionary.Dictionary;
import com.romanmarkunas.hangman.domain.Word;
import com.romanmarkunas.hangman.infrastructure.database_mysql.MySQLWordDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class DictionaryManager {

    private static String dbURL = "jdbc:mysql://localhost/hangman";
    private static String sslURL = "?verifyServerCertificate=true&useSSL=false&requireSSL=false";
    private static String user = "root";
    private static String password = "testpass";


    public static void main(String[] args) {

        try {

            Connection connection = DriverManager.getConnection(dbURL+sslURL, user, password);
            MySQLWordDAO wordDao = new MySQLWordDAO(connection);
            Dictionary dictionary = new Dictionary(wordDao);

            // should print chicken, ostrich and penguin
            printWordList(dictionary.getAllWords());

            if(dictionary.removeWord("penguin")) System.out.println("removed");;
            printWordList(dictionary.getAllWords());

            if(dictionary.addWord("penguin")) System.out.println("added");;
            printWordList(dictionary.getAllWords());

            connection.close();

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
