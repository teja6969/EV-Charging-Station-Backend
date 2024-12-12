package com.charge.ev.control;

import java.util.List;
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
    long stationid;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Entries loginRequest) {
        Entries user = evService.loginService(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
        	userid=user.getUserId();
          	email=loginRequest.getEmail();
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

    @PostMapping("/saveVendorStationDetails")
    public ResponseEntity<?> vd(@RequestBody VendorDetails vd){    	
    		if(userid.substring(0, 3).equals("EVD")) {
    			vd.setVendorid(userid);
    			vd.setEmail(email);
    		}
    		evService.registerVendor(vd);
        	return ResponseEntity.status(HttpStatus.OK)
        			.header("Content-Type", "application/json")
        			.body("{\"message\": \"Vendor station deatils added successfully\"}"); 
    }
    
    @GetMapping("/retriveVendorStationDetails")
    public List<VendorDetails> vupdateretrieve() {
		//String vendorid=(String) ve.get("vendorid");
		//VendorDetails vd=evService.vdupdateretrieve(vendorid).get();
    	return evService.vdupdateretrieve(userid);
    }
    
    @PutMapping("/updateVendorStationDetails")
    public ResponseEntity<String> vupdate(@RequestBody VendorDetails vu) {
    	stationid = vu.getstationID();
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
    	vd.setstationID(vu.getstationID());
    	
    	evService.vupdatedeatils(vd);
    	
    	return ResponseEntity.status(HttpStatus.OK)
    			.header("Content-Type", "application/json")
    			.body("{\"message\": \"Updated successfully\"}"); 
    }
    
    @PostMapping("/uservendorretrieve")
    public List<VendorDetails> uservendorretrieve(@RequestBody VendorDetails vu){
    	
    	return evService.uservendorretrieve(vu.getCity(),vu.getPincode());
    }
    
}
