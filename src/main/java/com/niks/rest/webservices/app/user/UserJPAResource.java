package com.niks.rest.webservices.app.user;

import java.beans.MethodDescriptor;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJPAResource {
	
	@Autowired
	private UserDaoService service;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAll(){
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public Resource<User> retrieveOne(@PathVariable int id){
		Optional<User> user=userRepository.findById(id);
		if(!user.isPresent()){
			throw new UserNotFoundException("id-"+id);
		}
		//hateaos:
		//when we retrieve a particular user we also want to send a link to display all users
		//hateaos does this for us
		//it returns the user along with a link to retrieve the list of all users
		Resource<User> resource=new Resource<User>(user.get());
		ControllerLinkBuilder linkTo=linkTo(methodOn(this.getClass()).retrieveAll());
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
		User savedUser=service.save(user);
		//We need to set Response status as CREATED
		//We need to get back the URI we just created
		//--> /users/4
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteOne(@PathVariable int id){
		User user=service.deleteById(id);
		if(user==null){
			throw new UserNotFoundException("id-"+id);
		}
		
	}

}
