package com.niks.rest.webservices.app.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	private static List<User> users=new ArrayList<>();
	private static int usersCount=3;
	
	static{
		users.add(new User(1,"Nikhil",new Date()));
		users.add(new User(2,"Atharva",new Date()));
		users.add(new User(3,"Saurabh",new Date()));
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user){
		if(user.getId()==null){
			user.setId(++usersCount);
		}
		users.add(user);
		return user;
	}
	
	public User findOne(int id){
		for(User user:users){
			if(user.getId()==id){
				return user;
			}
			
		}
		return null;
	}

}
