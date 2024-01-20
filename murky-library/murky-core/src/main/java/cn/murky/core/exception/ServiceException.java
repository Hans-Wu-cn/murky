package cn.murky.core.exception;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final Integer DEFAULT_CODE = 500;
    private final Integer code;

    public ServiceException() {
        this.code = DEFAULT_CODE;
    }

    public ServiceException(String msg) {
        super(msg);
        this.code = DEFAULT_CODE;
    }

    public Integer getCode() {
        return this.code;
    }
}
