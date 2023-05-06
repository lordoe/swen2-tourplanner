package at.fhtw.swen2.tutorial.presentation.utils;

public class InvalidParamException extends Exception{
    public InvalidParamException(String errorMessage){
        super(errorMessage);
    }
}
