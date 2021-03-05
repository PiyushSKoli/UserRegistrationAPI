package com.registration.RegistrationApp.Dto;

import java.util.List;

public class ResultModel {

	private volatile String message;
	
	private volatile Object data;
	
	private volatile List<String> messageList;

	public synchronized String getMessage() {
		return message;
	}

	public synchronized void setMessage(String message) {
		this.message = message;
	}

	public synchronized Object getData() {
		return data;
	}

	public synchronized void setData(Object data) {
		this.data = data;
	}

	public synchronized List<String> getMessageList() {
		return messageList;
	}

	public synchronized void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}
	
	
}
