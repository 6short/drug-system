package cn.bugstack.exception;

public class AdminNotFoundException extends BaseException {
    public AdminNotFoundException(){}

    public AdminNotFoundException(String message) {
        super(message);
    }
}
