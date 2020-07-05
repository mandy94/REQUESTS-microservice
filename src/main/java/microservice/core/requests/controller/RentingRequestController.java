package microservice.core.requests.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import net.bytebuddy.implementation.bytecode.Multiplication;


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
	
	@GetMapping(value="/user-kart/{id}")
	List<BundleRequestsDTO> getUserKart(@PathVariable String id){
		List<BundleRequestsDTO> asBundle = new ArrayList<>();

		for(BundleRequest req :  reqService.findByUser(Long.parseLong(id))) {
			
			RentingRequestDTO temp = new RentingRequestDTO();
			BundleRequestsDTO bundle = new BundleRequestsDTO();
			List<RequestedCarTerm> other_reqs = reqRepo.findTermByRequestId(req.getId());		
			User owner = restTemplate.getForObject(userServiceUrl + req.getWhoposted() , User.class);
			User client = restTemplate.getForObject(userServiceUrl + req.getWhoasked() , User.class);
			bundle.setClient(new UserDTO(client));
			bundle.setOwner(new UserDTO(owner));
			
			for(RequestedCarTerm term : other_reqs) {
				
				temp.setRentingDate(term.getRentingDate());
				temp.setRentingTime(term.getRentingTime());
				temp.setReturningDate(term.getRentingDate());
				temp.setReturningTime(term.getReturningTime());
				temp.setAdvert(term.getAdvert());
				temp.setStatus(term.getStatus());
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
			
			RentingRequestDTO temp = new RentingRequestDTO();
			BundleRequestsDTO bundle = new BundleRequestsDTO();
			List<RequestedCarTerm> other_reqs = reqRepo.findTermByRequestId(req.getId());		
			User owner = restTemplate.getForObject(userServiceUrl + req.getWhoposted() , User.class);
			User client = restTemplate.getForObject(userServiceUrl + req.getWhoasked() , User.class);
			bundle.setOwner(new UserDTO(client));
			bundle.setClient(new UserDTO(owner));
			
			for(RequestedCarTerm term : other_reqs) {
				
				temp.setRentingDate(term.getRentingDate());
				temp.setRentingTime(term.getRentingTime());
				temp.setReturningDate(term.getRentingDate());
				temp.setReturningTime(term.getReturningTime());
				temp.setAdvert(term.getAdvert());
				temp.setStatus(term.getStatus());
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
}
