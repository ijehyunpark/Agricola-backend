package com.semoss.agricola.GamePlay.exception;

/**
 * 없는 객체에 요청
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(){
        super("해당 객체를 찾을 수 없습니다.");

    }
    public NotFoundException(String msg) {
        super(msg);
    }
}
