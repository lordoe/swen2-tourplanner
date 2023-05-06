package at.fhtw.swen2.tutorial.presentation.utils;

public class MissingParamException extends Exception{
    public MissingParamException(String errorMessage){
        super(errorMessage);
    }
}
