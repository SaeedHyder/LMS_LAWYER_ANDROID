package com.ingic.lmslawyer.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseWrapper<T> {

	@SerializedName("Message")
	@Expose
	private String message;
	@SerializedName("Code")
	@Expose
	private Integer code;
	@SerializedName("Result")
	@Expose
	private T Result;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getResponse() {
		return code;
	}

	public void setResponse(Integer code) {
		this.code = code;
	}

	public T getResult() {
		return Result;
	}

	public void setResult(T result) {
		Result = result;
	}
}
