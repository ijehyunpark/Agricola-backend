package com.semoss.agricola.GamePlay.exception;

/**
 * 조건과 불일치하는 요청
 */
public class IllegalRequestException extends RuntimeException{
    public IllegalRequestException(){
        super("부적절한 입력입니다.");

    }
    public IllegalRequestException(String msg) {
        super(msg);
    }
}
