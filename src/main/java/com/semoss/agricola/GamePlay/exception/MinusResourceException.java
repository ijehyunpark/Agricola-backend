package com.semoss.agricola.GamePlay.exception;

/**
 * 자원 음수시 발생
 */
public class MinusResourceException extends RuntimeException{
    public MinusResourceException(){
        super("자원은 음수가 될 수 없습니다.");

    }
    public MinusResourceException(String msg) {
        super(msg);
    }
}
