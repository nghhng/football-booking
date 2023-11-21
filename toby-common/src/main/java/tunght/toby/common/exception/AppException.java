package tunght.toby.common.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final ErrorCommon errorCommon;
    public AppException(ErrorCommon errorCommon) {
        super(errorCommon.getMessage());
        this.errorCommon = errorCommon;
    }
}
