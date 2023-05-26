package com.semoss.agricola.GamePlay.exception;

/**
 * 경계값을 벗어난 요청
 */
public class NotAllowRequestException extends RuntimeException{
    public NotAllowRequestException(){
        super("허용되지 않은 입력입니다.");

    }
    public NotAllowRequestException(String msg) {
        super(msg);
    }
}
