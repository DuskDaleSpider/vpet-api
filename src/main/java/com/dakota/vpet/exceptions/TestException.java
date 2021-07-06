package com.dakota.vpet.exceptions;

public class TestException extends RuntimeException{
    public TestException(){
        super();
    }

    public TestException(String message){
        super(message);
    }
}
