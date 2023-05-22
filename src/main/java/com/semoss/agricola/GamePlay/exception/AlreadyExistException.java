package com.semoss.agricola.GamePlay.exception;

/**
 * 이미 사용중인 공간에 할당 요청
 */
public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(){
        super("해당 위치는 사용중입니다.");

    }
    public AlreadyExistException(String msg) {
        super(msg);
    }
}
