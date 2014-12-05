package com.wb.wizapp.ex;

public class WizUnmatchingRouteeException extends Exception {

	private static final long serialVersionUID = 1L;

	public WizUnmatchingRouteeException() {
		super();
	}

	public WizUnmatchingRouteeException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public WizUnmatchingRouteeException(String detailMessage) {
		super(detailMessage);
	}

	public WizUnmatchingRouteeException(Throwable throwable) {
		super(throwable);
	}

}
