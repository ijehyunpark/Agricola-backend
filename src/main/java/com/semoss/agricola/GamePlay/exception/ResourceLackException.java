package com.semoss.agricola.GamePlay.exception;

/**
 * 자원 부족 요청
 */
public class ResourceLackException extends RuntimeException {
    public ResourceLackException(){
        super("자원이 부족합니다.");
    }
    public ResourceLackException(String msg) {
        super(msg);
    }
}
