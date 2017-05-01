package com.romanmarkunas.hangman.infrastructure.database_mysql;

import com.romanmarkunas.hangman.domain.Word;
import com.romanmarkunas.hangman.services.DAOException;
import com.romanmarkunas.hangman.services.WordDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component("wordDao")
public class MySQLWordDAO implements WordDAO {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Word> mapper;


    public MySQLWordDAO(DataSource source) {

        jdbcTemplate = new NamedParameterJdbcTemplate(source);
        mapper = (rs, rowNum) -> new Word(rs.getString("word"));
    }


    @Override
    public List<Word> getWords() throws DAOException {

        return jdbcTemplate.query("SELECT * FROM words", mapper);
    }

    @Override
    public void addWord(Word word) throws DAOException {

        jdbcTemplate.update("INSERT INTO words (word) values (:string)", getParamsFromWord(word));
    }

    @Override
    public void removeWord(Word word) throws DAOException {

        jdbcTemplate.update("DELETE FROM words WHERE word = :string", getParamsFromWord(word));
    }


    private BeanPropertySqlParameterSource getParamsFromWord(Word word) {

        return new BeanPropertySqlParameterSource(word);
    }
}
