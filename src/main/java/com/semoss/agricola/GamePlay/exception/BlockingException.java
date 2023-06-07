package com.semoss.agricola.GamePlay.exception;

public class BlockingException extends RuntimeException {
    public BlockingException(){
        super("게임 진행이 중단되었습니다.");

    }
    public BlockingException(String msg) {
        super(msg);
    }
}
