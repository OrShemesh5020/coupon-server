package exceptions;

public class CouponsSystemExceptions extends Exception {

	private SystemExceptions errorCode;
	private String message = null;

	@Override
	public String toString() {
		if (message == null) {
			return "ErrorCode: " + errorCode;
		}
		return "ErrorCode: " + errorCode + "\nMessage: " + message;
	}

	public CouponsSystemExceptions(SystemExceptions errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public CouponsSystemExceptions(SystemExceptions errorCode) {
		this.errorCode = errorCode;
	}

	public SystemExceptions getErrorCode() {
		return errorCode;

	}

	public String getMessage() {
		return message;
	}

}
