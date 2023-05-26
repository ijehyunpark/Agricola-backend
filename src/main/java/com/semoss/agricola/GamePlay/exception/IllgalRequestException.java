package com.semoss.agricola.GamePlay.exception;

/**
 * 조건과 불일치하는 요청
 */
public class IllgalRequestException extends RuntimeException{
    public IllgalRequestException(){
        super("부적절한 입력입니다.");

    }
    public IllgalRequestException(String msg) {
        super(msg);
    }
}
