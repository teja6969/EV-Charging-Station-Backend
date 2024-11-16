package com.charge.ev.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.charge.ev.entries.Entries;
import com.charge.ev.service.EvService;

@RestController
public class EvController {
@Autowired //Spring will inject the service object
	EvService es;
	@GetMapping(value = "/login/{username}/{password}")
	public ResponseEntity<Entries> loginEntries(@PathVariable("username") String username, @PathVariable("password") String password) //Store userid and password
	{
		Entries e = es.loginService(username,password);
		e.setUsername(username);
		e.setPassword(password);
		if(e != null) {
			return new ResponseEntity<>(e, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
}
