package com.romanmarkunas.hangman.infrastructure.database_mysql;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import java.sql.Timestamp;
import java.time.LocalDateTime;

class DateProvidingBeanPropertyParameterSource extends BeanPropertySqlParameterSource {

    DateProvidingBeanPropertyParameterSource(Object object) {

        super(object);
    }


    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {

        Object value = super.getValue(paramName);

        if (value != null && value instanceof LocalDateTime) {

            return Timestamp.valueOf((LocalDateTime)value);
        }
        else {
            return value;
        }
    }
}
