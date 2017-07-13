package com.user.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value="ignoreUnknown")
public class UserResponse {

	private String resMsg;
	private String userId;
	private List<Status> valError;

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Status> getValError() {
		return valError;
	}

	public void setValError(List<Status> valError) {
		this.valError = valError;
	}

}
