package com.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MultivaluedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.user.domain.Status;
import com.user.domain.User;
import com.user.domain.UserResponse;
import com.user.repo.UserRepositoryImpl;

@Component
public class UserSVCImpl implements UserSVC {

	@Autowired
	UserRepositoryImpl userRepository;

	@Override
	public UserResponse createUser(User entity) {
		User user = userRepository.find(entity.getId());
		UserResponse userResponse = new UserResponse();
		System.out.println("user "+ user);
		if (user != null) {
			if (!(user.getEmail().equals(entity.getEmail())) && !user.isActive()) {
			    entity.setActive(true); 
				userRepository.save(entity);
				userResponse.setUserId(entity.getId());
				userResponse.setResMsg("User has been added sucessfully");
				userResponse.setValError(null);
				return userResponse;

			}

			userResponse.setResMsg("User hasn't been added sucessfully");
			userResponse.setUserId(entity.getId());
			List<Status> validationError = new ArrayList<>();
			if (user.getEmail().equals(entity.getEmail())) {
				Status emailIdStatus = new Status();
				emailIdStatus.setCode("E01");
				emailIdStatus.setField("emailId");
				emailIdStatus.setMessage("Email Id is same");
				validationError.add(emailIdStatus);
			}
			if (user.isActive()) {
				Status activeUserStatus = new Status();
				activeUserStatus.setCode("E02");
				activeUserStatus.setField("isActive");
				activeUserStatus.setMessage("This id has an active user");
				validationError.add(activeUserStatus);

			}
			userResponse.setValError(validationError);
			return userResponse;

		}

		userRepository.save(entity);
		userResponse.setUserId(entity.getId());
		userResponse.setResMsg("User has been added sucessfully");
		userResponse.setValError(null);

		return userResponse;

	}

	@Override
	public UserResponse updateUser(String id,String pincode,Date birthdate) {
		UserResponse userResponse=new UserResponse();
		User user= userRepository.find(id);

		
		if(user !=null)
		{
			userRepository.updateUser(id, pincode, birthdate);
			
			userResponse.setResMsg("User pincod and birthdate have been updated");
			userResponse.setUserId(id);
			userResponse.setValError(null);
			
			return userResponse;
		}
		
		userResponse.setUserId(id);
		userResponse.setResMsg("User has not been updated");
		List<Status> validationError=new ArrayList<>();
		Status status=new Status();
		status.setCode("E03");
		status.setField("No User");
		status.setMessage("User is not available");
		validationError.add(status);
		userResponse.setValError(validationError);
		
		return userResponse;
	}

	@Override
	public UserResponse deleteUser(String id) {
		UserResponse userResponse=new UserResponse();
		
		User user= userRepository.find(id);
		if(user!=null)
		{
			userRepository.delete(id);
			userResponse.setResMsg("User has been deactivated");
			userResponse.setUserId(id);
			userResponse.setValError(null);
			return userResponse;
		}
		
		userResponse.setResMsg("User hasn't been deactivated");
		userResponse.setUserId(id);
		List<Status> validationError=new ArrayList<>();
		Status updateStatus=new Status();
		updateStatus.setCode("E04");
		updateStatus.setField("id");
		updateStatus.setMessage("User not found");
		validationError.add(updateStatus);
		return userResponse;
		
		}

}
