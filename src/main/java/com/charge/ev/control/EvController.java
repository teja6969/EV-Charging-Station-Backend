package com.charge.ev.control;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import com.charge.ev.EmailService;
import com.charge.ev.entries.Entries;
import com.charge.ev.entries.Reservation;
import com.charge.ev.entries.VendorDetails;
import com.charge.ev.service.EvService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class EvController {
    @Autowired
    EvService evService;
    @Autowired
    EmailService em;
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
    public ResponseEntity<?> registration(@RequestBody Entries register) throws MailException, MessagingException {
        if (evService.registercheck(register.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("Content-Type", "application/json")
                    .body("{\"message\": \"UserID already exists\"}");
        } else {      	
        	evService.registerService(register);
        	userid=register.getUserId();
        	email=register.getEmail();
        	//Email should be sent once registered
        	em.sendEmail(email, "Welcome to our ChargeEV!", 
        		    "Hello " + register.getUsername() + ",<br><br>"
        		    + "We are excited to have you join our community. Here's how we can help you:<br><br>"
        		    + "1. Locate and reserve EV charging stations easily and quickly.<br>"
        		    + "2. Get real-time updates on charging station availability.<br>"
        		    + "3. Enjoy a seamless and convenient charging experience.<br><br>"
        		    + "Your User ID: " + userid + "<br><br>"
        		    + "Feel free to explore and make the most of our platform. If you have any questions or need support, we're just a click away.<br><br>"
        		    + "Thank you for choosing us to power your journey. Drive electric, drive the future!<br><br>"
        		    + "Best regards,<br>ChargeEV");
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
    
    
    @PostMapping("/viewSlotAvailability")
    public List<Reservation> viewSlotAvailability(@RequestBody
    	Map<String, Object> viewslot)
    {
    	String vendorid = (String) viewslot.get("vendorid");
    	String availabledate = (String) viewslot.get("availabledate");
    	return evService.viewSlotAvailability(vendorid,availabledate);
    }
    
    @PostMapping("/predictTime")
    public ResponseEntity<String> calculateChargingTime(@RequestBody Map<String, Object> uicalcharge) {
        try {
            String vendorid = (String) uicalcharge.get("vendorid");
            int stationid = (int) uicalcharge.get("stationid");
            
            // Safely convert to Double
            double batteryCapacity = ((Number) uicalcharge.get("batterycapacity")).doubleValue();
            double currentBattery = ((Number) uicalcharge.get("currentcharge")).doubleValue();
            
            System.out.println(batteryCapacity + "  " + currentBattery);
            double chargingRate = Double.parseDouble(evService.getCapacityByVendorId(vendorid, stationid));

            // Validate inputs
            if (currentBattery < 0 || currentBattery > 100 || batteryCapacity <= 0 || chargingRate <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .header("Content-Type", "application/json")
                        .body("{\"message\": \"Invalid input. Please enter valid values.\"}");
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

            // Create JSON response
            String responseBody = String.format(
                    "{\"message\": \"Time required to fully charge the battery: %d hours and %d minutes.\"}",
                    hours, minutes);

            // Return ResponseEntity with JSON body
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(responseBody);

        } catch (Exception e) {
            // Handle errors
            String errorMessage = String.format("{\"message\": \"Error processing the request: %s\"}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body(errorMessage);
        }
    }



  @PostMapping("/slotbooking")
   public ResponseEntity<String> slotbooking(@RequestBody Reservation rev, HttpSession session){
	  rev.setUserId("EV002");
	  System.out.println(rev);
	  evService.slotbooking(rev);
  	return ResponseEntity.status(HttpStatus.OK)
  		//Email should be sent once Slot Booked
  			.header("Content-Type", "application/json")
  			.body("{\"message\": \"Slot Booked Successfully\"}"); 
  }
  
  @GetMapping("/userbookinghistory")
	  public List<Reservation> userbookinghistrory(HttpSession session){
		  String userId = (String) session.getAttribute("userid");
		  return evService.userbookinghistory("EV002");
	  }
  @GetMapping("/vendorbookinghistory")
  public List<Reservation> vendorbookinghistrory(HttpSession session){
	  String vendorid = (String) session.getAttribute("vendorid");
	  return evService.vendorbookinghistrory("EVD001");
  }
  @GetMapping("/retriveuserdetails")
  public Entries retriveuserdetails(HttpSession session) {
  	return evService.retriveuserdetails("EV002");
  }
  @PutMapping("/validateandupdatepassword")
   public void updatepassowrd(@RequestBody Map<String,String> up, HttpSession session) {
	  String userId = (String) session.getAttribute("userid");
	  String newPassword = up.get("newPassword");
	  evService.userupdatepassword(newPassword, "EV002");
	  
  }
}
