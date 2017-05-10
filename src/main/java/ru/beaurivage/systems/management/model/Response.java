package ru.beaurivage.systems.management.model;

import lombok.Getter;
import lombok.Setter;

public class Response {

	@Getter @Setter private String message;
	@Getter @Setter private int errcode;

	public Response() {

	}

	public Response(String message, int errcode) {
		this.message = message;
		this.errcode = errcode;
	}
}
