package ua.project.exceptions;

public class UserAlreadyExistException extends RuntimeException{


    public UserAlreadyExistException(String message) {
        super(message);
    }


}