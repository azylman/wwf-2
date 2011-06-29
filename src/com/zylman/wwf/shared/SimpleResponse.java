package com.zylman.wwf.shared;

public final class SimpleResponse {
	private String message;
	private int errorCode;

	private SimpleResponse(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public static SimpleResponse build(String message) {
		return new SimpleResponse(0, message);
	}

	public static SimpleResponse build(int errorCode, String message) {
		return new SimpleResponse(errorCode, message);
	}

	public String getMessage() {
		return message;
	}

	public int getErrorCode() {
		return errorCode;
	}
}