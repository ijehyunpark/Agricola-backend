package com.semoss.agricola.GamePlay.exception;

public class AnimalOverflowException extends RuntimeException {
    public AnimalOverflowException(){
        super("더 이상 동물을 배치할 수 없습니다. 다시 배치해야합니다.");

    }
    public AnimalOverflowException(String msg) {
        super(msg);
    }
}
