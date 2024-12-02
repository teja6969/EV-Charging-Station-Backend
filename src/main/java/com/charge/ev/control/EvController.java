package com.charge.ev.control;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.charge.ev.entries.Entries;
import com.charge.ev.entries.SlotType;
import com.charge.ev.entries.VendorDetails;
import com.charge.ev.service.EvService;

@RestController
@RequestMapping("/api/auth")
public class EvController {
    @Autowired
    EvService evService;
    
    String userid;
    String email;
    long stationid;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Entries loginRequest) {
        Entries user = evService.loginService(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
        	userid=user.getUserId();
          	email=loginRequest.getEmail();
            return new ResponseEntity<String>("User Logged in Successfully", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody Entries register){
    	System.out.println(register.toString());
    	
    	//Entries user = evService.registerService(register.getUsername(), register.getPassword(), register.getEmail(), register.getRole(), register.getCreatedAt(), register.getPhone());
    	if (evService.registercheck(register.getEmail()) != null) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UserID is already EXIST's");
        } else {
        	
        	evService.registerService(register);
        	userid=register.getUserId();
        	email=register.getEmail();
        	return ResponseEntity.status(HttpStatus.OK).body("User is Registered Successfully");
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
		VendorDetails vd=evService.vdupdateretrieve(vendorid).get();
		this.stationid =  vd.getstationID();
    	return evService.vdupdateretrieve(vendorid);
    }
    
    @PutMapping("/vupdate")
    public ResponseEntity<String> vupdate(@RequestBody VendorDetails vu) {
    	VendorDetails vd=evService.vupdate(stationid).get();
    	evService.deleteSlottype(vd);
    	vd.setCapacity(vu.getCapacity());
    	vd.setCity(vu.getCity());
    	vd.setStationName(vu.getStationName());
    	vd.setPincode(vu.getPincode());
    	vd.setLandmark(vu.getLandmark());
    	vd.setState(vu.getState());
    	vd.setSlot(vu.getSlot());
    	vd.setvType(vu.getvType());
    	vd.setPhone(vu.getPhone());
    	vd.setEmail(vu.getEmail());
    	vd.setSl(vu.getSl());
    	vd.setVendorid(vu.getVendorid());
    	vd.setSl(vu.getSl());
    	vd.setstationID(1);
    	
    	evService.vupdatedeatils(vd);
    	
    	return new ResponseEntity<String>("Updated successfully",HttpStatus.OK);
    }
    
}
