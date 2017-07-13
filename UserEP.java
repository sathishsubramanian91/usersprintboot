package com.user.endpoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.user.domain.Status;
import com.user.domain.User;
import com.user.domain.UserResponse;
import com.user.service.UserSVC;

@Component
@RestController
@Produces(value=MediaType.APPLICATION_JSON)
@Consumes(value=MediaType.APPLICATION_JSON)
@RequestMapping(value = "/user")
public class UserEP {

	@Autowired
	UserSVC userSVC;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public UserResponse createUser(@RequestBody MultivaluedHashMap<String, String> userInfo) {

		UserResponse userResponse = new UserResponse();

		User user = new User();
		user.setId(userInfo.getFirst("id"));
		user.setfName(userInfo.getFirst("fName"));
		user.setlName(userInfo.getFirst("lName"));
		user.setEmail(userInfo.getFirst("email"));
		user.setPinCode(userInfo.getFirst("pinCode"));
		String birthdate = userInfo.getFirst("birthDate");

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Date birthday = null;

		try {
			birthday = sdf.parse(birthdate);
			if (birthday.after(new Date())) {
				// Check date is future date or not 
				userResponse.setResMsg("Date is future date");
				userResponse.setUserId(user.getId());
				List<Status> validationError = new ArrayList<>();
				Status futureStaus = new Status();
				futureStaus.setCode("E08");
				futureStaus.setField("birthdate");
				futureStaus.setMessage("Your birthda date is future date");
				validationError.add(futureStaus);

				userResponse.setValError(validationError);

				return userResponse;
			}
		} catch (ParseException e) {
			// Date fromat is correct or not
			userResponse.setResMsg("Date format is not correct");
			userResponse.setUserId(user.getId());
			List<Status> validationError = new ArrayList<>();

			Status dobStatus = new Status();
			dobStatus.setCode("E06");
			dobStatus.setField("birthDate");
			dobStatus.setMessage("Dob is not in correct format" + e.getMessage());
			validationError.add(dobStatus);
			userResponse.setValError(validationError);

			return userResponse;
		}
		user.setBirthDate(birthday);

		return userSVC.createUser(user);

	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public UserResponse updateUser(@QueryParam(value = "id") String id,
			@RequestBody MultivaluedHashMap<String, String> updateValue) {

		UserResponse userResponse = new UserResponse();

		String pincode = updateValue.getFirst("pincode");
		String birthdate = updateValue.getFirst("birthdate");

		Date birthday = null;

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			birthday = sdf.parse(birthdate);
			if (birthday.after(new Date())) {
				userResponse.setResMsg("Date is future date");
				userResponse.setUserId(id);
				List<Status> validationError = new ArrayList<>();
				Status futureStaus = new Status();
				futureStaus.setCode("E08");
				futureStaus.setField("birthdate");
				futureStaus.setMessage("Your birthda date is future date");
				validationError.add(futureStaus);

				userResponse.setValError(validationError);

				return userResponse;
			}
		} catch (ParseException e) {
			userResponse.setResMsg("Date format is not correct");
			userResponse.setUserId(id);
			List<Status> validationError = new ArrayList<>();

			Status dobStatus = new Status();
			dobStatus.setCode("E06");
			dobStatus.setField("birthDate");
			dobStatus.setMessage("Dob is not in correct format" + e.getMessage());
			validationError.add(dobStatus);
			userResponse.setValError(validationError);

			return userResponse;
		}

		return userSVC.updateUser(id, pincode, birthday);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.PUT)
	public UserResponse deleteUser(@QueryParam(value = "id") String id) {
		return userSVC.deleteUser(id);
	}

}
