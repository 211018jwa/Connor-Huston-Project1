package com.revature.dto;

import java.util.Objects;

public class MessageDto {
	
	private String message;
	
	public MessageDto() {
		
	}
	
	public MessageDto(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		return Objects.hash(message);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageDto other = (MessageDto) obj;
		return Objects.equals(message, other.message);
	}

	@Override
	public String toString() {
		return "MessageDto [message=" + message + "]";
	}
	
	

}
