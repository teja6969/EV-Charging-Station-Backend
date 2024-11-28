package com.charge.ev.control;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.charge.ev.entries.Entries;
import com.charge.ev.entries.VendorDetails;
import com.charge.ev.service.EvService;

@RestController
@RequestMapping("/api/auth")
public class EvController {
    @Autowired
    EvService evService;
    
    String userid;
    String email;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Entries loginRequest) {
    	Entries user = evService.loginService(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            		.header("Content-Type", "application/json")
            		.body("{\"message\": \"Invalid username or password.\"}");
        }
    }
    

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody Entries register) {
        if (evService.registercheck(register.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("Content-Type", "application/json")
                    .body("{\"message\": \"UserID already exists\"}");
        } else {      	
        	evService.registerService(register);
        	userid=register.getUserId();
        	email=register.getEmail();

            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Content-Type", "application/json")
                    .body("{\"message\": \"User is registered successfully\"}"); 

        }
    }
    
    @PostMapping("/vd")
    public ResponseEntity<?> vd(@RequestBody VendorDetails vd){    	
    		if(userid.substring(0, 3).equals("EVD")) {
    			vd.setVendorid(userid);
    			vd.setEmail(email);
    		}
    		evService.registerVendor(vd);
        	return ResponseEntity.status(HttpStatus.OK).body("Vendor station deatils added successfully");
        
    }
    
    @GetMapping("/vupdateretrieve")
    public Optional<VendorDetails> vupdateretrieve(@RequestBody Map<String,Object> ve) {
		String vendorid=(String) ve.get("vendorid");
		//long vendoridnew=Long.parseLong(vendorid.substring(4));
		
		//Optional<VendorDetails> v=evService.vdupdateretrieve(vendoridnew);
		//System.out.println(v.toString());
		
    	return evService.vdupdateretrieve(vendorid);
    	
    }
}
