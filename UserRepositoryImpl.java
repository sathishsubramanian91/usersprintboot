package com.user.repo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.user.domain.User;

@Component
public class UserRepositoryImpl implements UserRepo {

	@Autowired
	MongoOperations mongoOperation;
	

	public void save(User entity)
	{
		mongoOperation.save(entity);	
		
	}
	
	public void delete(String id)
	{
		Query query=new Query();
		query.addCriteria(Criteria.where("id").is(id));
		
		Update update=new Update();
		update.set("isActive", false);
		mongoOperation.updateFirst(query, update, User.class);
		
	}
	
	public User find(String id)
	{
		Query query=new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoOperation.findOne(query, User.class);		
	}
	
	public void updateUser(String id,String pinCode,Date birthdDate)
	{
		Query query=new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Update update=new Update();
		update.set("birthDate", birthdDate);
		update.set("pinCode",pinCode);
		mongoOperation.updateFirst(query, update, User.class);
		
	}

}
