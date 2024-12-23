package com.charge.ev.control;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.charge.ev.entries.Entries;
import com.charge.ev.entries.Reservation;
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
    
    
    @PostMapping("/viewSlotAvailability/{vendorid}/{availabledate}")
    public List<Reservation> viewSlotAvailability(@PathVariable("vendorid") String vendorid,@PathVariable("availabledate") String availabledate){ 
    	return evService.viewSlotAvailability(vendorid,availabledate);
    }
    
    @PostMapping("/predictTime/{vendorid}/{batterycapacity}/{currentcharge}/{stationid}")

  //  @GetMapping("/calculate-charging-time")
    public String calculateChargingTime(
    		@PathVariable("stationid") long stationid,
            @PathVariable("vendorid") String vendorid,
            @PathVariable("batterycapacity") double batteryCapacity,
            @PathVariable("currentcharge") double currentBattery) {

    	double chargingRate=Double.parseDouble(evService.getCapacityByVendorId(vendorid,stationid));
    	
        // Validate inputs
        if (currentBattery < 0 || currentBattery > 100 || batteryCapacity <= 0 || chargingRate <= 0) {
            return "Invalid input. Please enter valid values.";
        }

        // Calculate the remaining battery percentage to reach 100%
        double remainingPercentage = 100 - currentBattery;

        // Calculate the remaining energy needed in kWh
        double remainingEnergy = (remainingPercentage / 100) * batteryCapacity;

        // Calculate the time required to charge in hours
        double chargingTimeHours = remainingEnergy / chargingRate;

        // Convert time to hours and minutes
        int hours = (int) chargingTimeHours;
        int minutes = (int) ((chargingTimeHours - hours) * 60);

        // Return the result as a string
        return "Time required to fully charge the battery: " + hours + " hours and " + minutes + " minutes.";
    }
    
    
  //  @PostMapping("/slotbooking")
    //public ResponseEntity<T>
    
}
