package microservice.core.requests.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.Parent;
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
import microservice.core.requests.model.DTO.AcceptedRequestDTO;
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
	void acceptRequest(@RequestHeader("Authorization") String header,@RequestBody AcceptedRequestDTO req) {
		
		System.out.println(">> " + req);
		
//		RequestedCarTerm thisterm = reqRepo.findById(id).orElse(null);
//		thisterm.setStatus("RESERVED");		
//		
//		User owner = authorizeMe(header).getBody();
//		List<RequestedCarTerm>  others =  reqService.findMyRequestByStatus(owner.getId(), "PENDING");
//			for(RequestedCarTerm term : others) {
//				if(term.getId() != thisterm.getId() && term.getAdvert().getId() == thisterm.getAdvert().getId())
////				if(doesItCover(term.getRentingDate(),term.getReturningDate(),thisterm.getRentingDate(),thisterm.getReturningDate()))
//				{
//					term.setStatus("CANCELED");
//					reqRepo.save(term);
//				}
//			}
//		
//		reqRepo.save(thisterm);		
		
	}
	private String userSrviceUrl = "http://localhost:8183/api/user/";
	
	@GetMapping(value="/check-cover/{id}")
	List<RentingRequestDTO> checkIfTermCovers(@RequestHeader("Authorization") String header,@PathVariable Long id){
		System.out.println("Unitar check if cover func smo");
		return null;
		}
//		RequestedCarTerm thisterm = reqRepo.findById(id).orElse(null);
//		
//		User owner = authorizeMe(header).getBody();
//		List<RequestedCarTerm>  others =  reqService.findMyRequestByStatus(owner.getId(), "PENDING");
//		List<RentingRequestDTO>  returns = new ArrayList<RentingRequestDTO>();
//		
//		for(RequestedCarTerm term : others) {
//				if(term.getId() != thisterm.getId() && term.getAdvert().getId() == thisterm.getAdvert().getId())
//				if(doesItCover(term.getRentingDate(),term.getReturningDate(),thisterm.getRentingDate(),thisterm.getReturningDate()))
//				{
//					RentingRequestDTO dto =new RentingRequestDTO(term);
//					UserDTO who = new UserDTO();
//					who.setId(reqService.findOwnerOfTheRequest(term.getRent_id().getId()));
//					HttpHeaders headers = new HttpHeaders();
//					headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//					headers.add("Authorization", header );
//					HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
//					restTemplate = new RestTemplate();
//					User retUser = restTemplate.exchange(userServiceUrl +who.getId(), HttpMethod.GET,entity , User.class).getBody();
//					who.setFullName(retUser.getFirstName() + " " + retUser.getLastName());
//					
//					
//					dto.setWhoasked(who);
//					returns.add(dto);					
//				}
//			}
//		return returns;		
	
	// da li se poreklapaju datumi
	// sart1,end1 su od kondaidata
	// start2,end2 su od vlasnika/referencntog
	
//	boolean doesItCover(String start1, String end1, String start2, String end2)
//	{
//		int[] rent1 = parseDate(start1);
//		int[] ret1 = parseDate(end1);
//		
//		int[] rent2 = parseDate(start2);
//		int[] ret2 = parseDate(end2);
//		System.out.println("Unitar does it cover funckije");
//		if(isItEarlier(rent1[1], rent2[1], rent1[0], rent2[0]))
//		{
//			if(isItEarlier(ret1[1], ret2[1], ret1[0], ret2[0]))
//				return true;
//		}
		
//		if(isItEarlier(MM, mm, DD, dd))
//		return true;
//	}
//	int[] parseDate(String date) {
//		int[] rez = new int[2];
//		String[] parts = date.split("\\.");
//		rez[0] = Integer.parseInt(parts[0]);
//		rez[1] = Integer.parseInt(parts[1]);
//		return rez;
//	}
//	boolean isItEarlier(int MM, int mm, int DD, int dd)
//	{
//		if(MM >= mm && DD > dd)
//			return true;
//		return false;
//		
//	}
//	boolean isItLater(int MM,int mm, int DD, int dd)
//	{
//		if( MM <= mm && DD < dd)
//			return true;
//		return false;
//	}
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
				temp.setReturningDate(term.getReturningDate());
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
				temp.setReturningDate(term.getReturningDate());
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
	ResponseEntity<User> authorizeMe(String header){
		String serviceUrl = "http://localhost:8183/api/whoami";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", header );
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		restTemplate = new RestTemplate();
		return restTemplate.exchange(serviceUrl, HttpMethod.GET,entity , User.class);
	}
	
	private boolean isThisRequestLast(Long bundleid) {
		List<RequestedCarTerm> termsInBundle = reqRepo.findTermByRequestId(bundleid);
		if(termsInBundle.size()==1)
			return true;
		else
			return false;
		
}
}