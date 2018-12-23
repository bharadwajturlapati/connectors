package com.company.fileservice.response;

public class FileServiceOperationsResponse<T> {

	private int opstatus;
	private T newInstance;

	public FileServiceOperationsResponse(T t) {
		newInstance = t;
	}

	public int getOpstatus() {
		return opstatus;
	}

	public void setOpstatus(int opstatus) {
		this.opstatus = opstatus;
	}

	public T getOperationType() {
		return newInstance;
	}

	public void setOperationType(T operationType) {
		this.newInstance = operationType;
	}

}
