package com.romanmarkunas.hangman.infrastructure.database_mysql;

import com.romanmarkunas.hangman.domain.HangmanGameState;
import com.romanmarkunas.hangman.services.DAOException;
import com.romanmarkunas.hangman.services.HangmanGameStateDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.*;
import java.util.List;

@Component("gameStateDao")
public class MySQLHangmanGameStateDAO implements HangmanGameStateDAO {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<HangmanGameState> mapper;


    @Autowired
    public MySQLHangmanGameStateDAO(DataSource source) {

        jdbcTemplate = new NamedParameterJdbcTemplate(source);

        mapper = (rs, rowNum) ->

                new HangmanGameState(
                        rs.getInt("id"),
                        rs.getString("secretword"),
                        rs.getString("revealedword"),
                        rs.getInt("triesleft"),
                        rs.getString("notusedchars"),
                        LocalDateTime.ofInstant(rs.getTimestamp("expirydate").toInstant(), ZoneId.systemDefault())
                );
    }

    // TODO - catch Spring DataAccessException
    @Override
    public List<HangmanGameState> getGames() throws DAOException {

        return jdbcTemplate.query("SELECT * FROM games", mapper);
    }

    @Override
    public HangmanGameState getGame(int id) throws DAOException {

        return jdbcTemplate.queryForObject("SELECT * FROM games WHERE id = :id", getParamsFromId(id), mapper);
    }

    @Override
    public int createNewGame(HangmanGameState state) throws DAOException {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql =
                "INSERT INTO games (secretword, revealedword, triesleft, notusedchars, expirydate) values" +
                "(:secretWord, :revealedWord, :triesLeft, :notUsedChars, :expiryDate)";

        jdbcTemplate.update(sql, getParamsFromGameState(state), keyHolder);

        Number key = keyHolder.getKey();

        return key != null ? key.intValue() : 0;
    }

    @Override
    public void updateGame(HangmanGameState state) throws DAOException {

        String sql =
                "UPDATE games SET revealedword = :revealedWord, triesleft = :triesLeft, notusedchars = :notUsedChars, " +
                "expirydate = :expiryDate WHERE id = :id";

        jdbcTemplate.update(sql, getParamsFromGameState(state));
    }

    @Override
    public void removeGame(int id) throws DAOException {

        jdbcTemplate.update("DELETE FROM games WHERE id = :id", getParamsFromId(id));
    }

    @Override
    public void removeAllOutdated() throws DAOException {

        MapSqlParameterSource params = new MapSqlParameterSource("timestamp", Timestamp.from(Instant.now()));

        jdbcTemplate.update("DELETE FROM games WHERE expirydate < :timestamp", params);
    }


    private MapSqlParameterSource getParamsFromId(int id) {

        return new MapSqlParameterSource("id", id);
    }

    private BeanPropertySqlParameterSource getParamsFromGameState(HangmanGameState state) {

        return new DateProvidingBeanPropertyParameterSource(state);
    }
}
