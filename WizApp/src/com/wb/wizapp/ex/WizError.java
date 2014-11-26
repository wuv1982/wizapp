package com.wb.wizapp.ex;

public class WizError extends Exception {

	private static final long serialVersionUID = 1L;

	public WizError() {
		super();
	}

	public WizError(String detailMessage) {
		super(detailMessage);
	}

	public WizError(Throwable throwable) {
		super(throwable);
	}

	public WizError(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
