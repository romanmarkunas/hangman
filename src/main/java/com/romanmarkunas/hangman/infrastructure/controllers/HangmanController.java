package com.romanmarkunas.hangman.infrastructure.controllers;

import com.romanmarkunas.hangman.applications.dictionary.Dictionary;
import com.romanmarkunas.hangman.infrastructure.database_mysql.MySQLWordDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HangmanController {

    private static String dbURL = "jdbc:mysql://localhost/hangman";
    private static String sslURL = "?verifyServerCertificate=true&useSSL=false&requireSSL=false";
    private static String user = "root";
    private static String password = "testpass";


    @RequestMapping(value={"", "/", "game"}, method=RequestMethod.GET)
    public String getGameLayout() {

        return "layout";
    }

    // TODO - handle exceptions
    @RequestMapping(value="gamestats", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public Map<String, Object> getGameState() throws SQLException {

        Connection connection = DriverManager.getConnection(dbURL+sslURL, user, password);
        MySQLWordDAO wordDao = new MySQLWordDAO(connection);
        Dictionary dictionary = new Dictionary(wordDao);

        Map<String, Object> data = new HashMap<>();
        data.put("words", dictionary.getAllWords());
        data.put("wordcount", dictionary.getAllWords().size());

        System.out.println("Sending JSON: " + data);

        return data;
    }
}
