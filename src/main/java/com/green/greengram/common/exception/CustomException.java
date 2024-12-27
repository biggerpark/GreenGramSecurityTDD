package com.green.greengram.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString

//500단위 에러: 서버측 에러
// 400단위 에러: 클라이언트측 에러

public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}
