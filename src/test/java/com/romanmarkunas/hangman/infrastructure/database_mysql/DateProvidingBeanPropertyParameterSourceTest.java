package com.romanmarkunas.hangman.infrastructure.database_mysql;

import com.romanmarkunas.hangman.domain.HangmanGameState;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DateProvidingBeanPropertyParameterSourceTest {

    @Test
    public void getValue() throws Exception {

        LocalDateTime time = LocalDateTime.now();
        int expectedId = 40;
        Timestamp expectedTime = Timestamp.valueOf(time);

        HangmanGameState gameStateMock = mock(HangmanGameState.class);
        when(gameStateMock.getExpiryDate()).thenReturn(time);
        when(gameStateMock.getId()).thenReturn(expectedId);

        BeanPropertySqlParameterSource testInstance = new DateProvidingBeanPropertyParameterSource(gameStateMock);

        assertEquals(expectedTime, testInstance.getValue("expiryDate"));
        assertEquals(Timestamp.class, testInstance.getValue("expiryDate").getClass());
        assertEquals(expectedId, testInstance.getValue("id"));
    }
}