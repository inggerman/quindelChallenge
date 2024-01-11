package com.quindel.quindelgenesis.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ResponseError {

    private UUID errorId;
    private String errorCode;
    private String errorMessage;
    private String errorDetail;

}
