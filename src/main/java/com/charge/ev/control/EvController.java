package com.charge.ev.control;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Entries loginRequest) {
        Entries user = evService.loginService(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody Entries register){
    	
    	//Entries user = evService.registerService(register.getUsername(), register.getPassword(), register.getEmail(), register.getRole(), register.getCreatedAt(), register.getPhone());
    	if (evService.registercheck(register.getUsername()) == true) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UserID is already EXIST's");
        } else {
        	evService.registerService(register);
        	return ResponseEntity.status(HttpStatus.OK).body("User is Registered Successfully");
        }
    }
    
    @PostMapping("/vd")
public ResponseEntity<?> vd(@RequestBody VendorDetails vd){

    		evService.registerVendor(vd);
        	return ResponseEntity.status(HttpStatus.OK).body("Vendor Registered Successfully with the ID: "+vd.getVendorid());
        
    } 
}
