package com.semoss.agricola.GamePlay.exception;

public class ServerError extends RuntimeException{
    public ServerError(){
        super("로직에서 벗어난 에러");

    }
    public ServerError(String msg) {
        super(msg);
    }
}
