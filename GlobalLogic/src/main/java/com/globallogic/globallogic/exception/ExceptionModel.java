package com.globallogic.globallogic.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ExceptionModel {
    private List<Error> error;

    @Data
    public static class Error {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "America/Santiago")
        private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        private int codigo;
        private String detail;
    }
}
