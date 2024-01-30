package cn.murky.core.exception;

import cn.murky.core.record.ErrorRecord;
import lombok.Getter;

import java.io.Serial;

@Getter
public class ServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final Integer DEFAULT_CODE = 500;
    private final Integer code;

    public ServiceException() {
        this.code = DEFAULT_CODE;
    }

    public ServiceException(ErrorRecord errorRecord) {
        super(errorRecord.message());
        this.code = errorRecord.errCode();
    }

}
