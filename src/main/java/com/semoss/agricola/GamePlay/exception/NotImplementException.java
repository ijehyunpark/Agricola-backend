package com.semoss.agricola.GamePlay.exception;

/**
 * 미구현 사항 접근
 */
public class NotImplementException extends RuntimeException{
    public NotImplementException(){
        super("미구현");

    }
    public NotImplementException(String msg) {
        super(msg);
    }
}
