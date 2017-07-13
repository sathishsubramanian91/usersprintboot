package com.user.service;

import java.util.Date;

import javax.ws.rs.core.MultivaluedHashMap;

import com.user.domain.User;
import com.user.domain.UserResponse;

public interface UserSVC {
	
	public UserResponse createUser(User user);
	public UserResponse updateUser(String id,String pincode,Date birthdate);
	public UserResponse deleteUser(String id);

}
