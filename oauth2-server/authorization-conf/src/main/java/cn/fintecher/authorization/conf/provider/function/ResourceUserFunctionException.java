package cn.fintecher.authorization.conf.provider.function;

public class ResourceUserFunctionException extends RuntimeException {

	
	public ResourceUserFunctionException(String msg) {
        super(msg);
    }

    public ResourceUserFunctionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}