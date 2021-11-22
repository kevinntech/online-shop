package me.kevinntech.modules.main;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "ERROR-001", "Invalid Input Value"),
    NOT_FOUND_VALUE(404, "ERROR-002", "Data Not Found"),
    DUPLICATION(400, "ERROR-003", "Data is Duplication");

    private final int status;
    private final String code;
    private final String message;

}
