package microservice.core.requests.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import microservice.core.requests.model.Advert;
import microservice.core.requests.model.BundleRequest;
import microservice.core.requests.model.RequestedCarTerm;
import microservice.core.requests.model.User;
import microservice.core.requests.model.DTO.BundleRequestsDTO;
import microservice.core.requests.model.DTO.RentingRequestDTO;
import microservice.core.requests.model.DTO.UserDTO;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RentingRequestController {

	@Autowired
	private RentingRequestService reqService;
	
	@Autowired
	private RequestedAdvertsRepository reqRepo; // for single requests
	
	private String adServiceUrl   = "http://localhost:8180/adverts-ms/api/";
	private String whoamiuserServiceUrl = "http://localhost:8180/users-ms/api/whoami";
	private String userServiceUrl = "http://localhost:8180/users-ms/api/user/";
	RestTemplate restTemplate = new RestTemplate();
	
	
	@PutMapping(value="/accept-request")
	void acceptRequest(@RequestBody Long id) {
		RequestedCarTerm term = reqRepo.findById(id).orElse(null);
		term.setStatus("RESERVED");
		reqRepo.save(term);		
	}
	@PutMapping(value="/decline-request")
	void declineRequest(@RequestBody Long id) {
		RequestedCarTerm term = reqRepo.findById(id).orElse(null);
		term.setStatus("CANCELED");
		reqRepo.save(term);
		
	}
	
	@GetMapping(value="/user-kart/{id}")
	List<BundleRequestsDTO> getUserKart(@PathVariable String id){
		List<BundleRequestsDTO> asBundle = new ArrayList<>();

		for(BundleRequest req :  reqService.findByUser(Long.parseLong(id))) {
			
			
			BundleRequestsDTO bundle = new BundleRequestsDTO();
			List<RequestedCarTerm> other_reqs = reqRepo.findTermByRequestId(req.getId());		
			User owner = restTemplate.getForObject(userServiceUrl + req.getWhoposted() , User.class);
			User client = restTemplate.getForObject(userServiceUrl + req.getWhoasked() , User.class);
			bundle.setClient(new UserDTO(client));
			bundle.setOwner(new UserDTO(owner));
			for(RequestedCarTerm term : other_reqs) {
				RentingRequestDTO temp = new RentingRequestDTO();
				
				temp.setRentingDate(term.getRentingDate());
				temp.setRentingTime(term.getRentingTime());
				temp.setReturningDate(term.getRentingDate());
				temp.setReturningTime(term.getReturningTime());
				temp.setAdvert(term.getAdvert());
				temp.setStatus(term.getStatus());
				temp.setId(term.getId());
				bundle.getContent().add(temp);
			}
			asBundle.add(bundle);
		}
		
		return asBundle;
	}
	
	
	@GetMapping(value="/my-requests")
	List<BundleRequestsDTO> getMyRequests(@RequestHeader("Authorization") String header) {
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", header );
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);		    
		ResponseEntity<User> me = restTemplate.exchange(whoamiuserServiceUrl , HttpMethod.GET, entity, User.class);
		
		List<BundleRequestsDTO> asBundle = new ArrayList<>();

		for(BundleRequest req :  reqService.findForUser(me.getBody().getId())) {
			
			BundleRequestsDTO bundle = new BundleRequestsDTO();
			List<RequestedCarTerm> other_reqs = reqRepo.findTermByRequestId(req.getId());		
			User owner = restTemplate.getForObject(userServiceUrl + req.getWhoposted() , User.class);
			User client = restTemplate.getForObject(userServiceUrl + req.getWhoasked() , User.class);
			bundle.setOwner(new UserDTO(client));
			bundle.setClient(new UserDTO(owner));
			
			for(RequestedCarTerm term : other_reqs) {
				RentingRequestDTO temp = new RentingRequestDTO();
				
				temp.setRentingDate(term.getRentingDate());
				temp.setRentingTime(term.getRentingTime());
				temp.setReturningDate(term.getRentingDate());
				temp.setReturningTime(term.getReturningTime());
				temp.setAdvert(term.getAdvert());
				temp.setStatus(term.getStatus());
				temp.setId(term.getId());
				bundle.getContent().add(temp);
			}
			asBundle.add(bundle);
		}
		
		return asBundle;
		
	}
	
	@PostMapping(value="/new-request")
	void createNewRequest(@RequestBody RentingRequestDTO data,@RequestHeader("Authorization") String header) {
		BundleRequest req = new BundleRequest();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", header );
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);		    
		ResponseEntity<User> me = restTemplate.exchange(whoamiuserServiceUrl , HttpMethod.GET, entity, User.class);
		
		Advert reqAdvert = restTemplate.getForObject(adServiceUrl+data.getAdvert().getId(), Advert.class);
		
		req.setWhoasked(me.getBody().getId());
		req.setWhoposted(reqAdvert.getUser_id());
		
		
		BundleRequest multiple = reqService.checkIfIAlreadyRequestedFrom(req.getWhoasked(), req.getWhoposted());

		RequestedCarTerm newTerm ;
		
		if(multiple== null){ // jos nismo narucivali od ovog vlasnika
				reqService.save(req);
				newTerm = new RequestedCarTerm(reqAdvert, req);
				
			}else
				newTerm = new RequestedCarTerm(reqAdvert, multiple);
		 
		
		newTerm.setRentingDate(data.getRentingDate());
		newTerm.setRentingTime(data.getRentingTime());
		newTerm.setReturningDate(data.getReturningDate());
		newTerm.setReturningTime(data.getReturningTime());
		reqRepo.save(newTerm);
		
			
	}
	@DeleteMapping(value="/delete-request/{id}")
	void deletRequest(@PathVariable Long id) {
		RequestedCarTerm term = reqRepo.findById(id).orElse(null);
		if(term!=null) {
			if(!isThisRequestLast(term.getRent().getId()))
				reqRepo.delete(term);
			else{
				reqRepo.delete(term);
				reqService.deleteBundle(term.getRent().getId());
			}
		}
	}
	
	private boolean isThisRequestLast(Long bundleid) {
		List<RequestedCarTerm> termsInBundle = reqRepo.findTermByRequestId(bundleid);
		if(termsInBundle.size()==1)
			return true;
		else
			return false;
		
}
}