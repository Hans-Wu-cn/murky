package cn.poem.solon.core.exception;

public class ServiceException extends RuntimeException{
    public final Integer CODE=500;

    public ServiceException() {

    }
    public ServiceException(String msg) {
        super(msg);
    }
}
