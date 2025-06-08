package com.globallogic.globallogic.exception;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ExceptionModel {
    private List<Error> error;

    @Data
    public static class Error {
        private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        private int codigo;
        private String detail;
    }
}
