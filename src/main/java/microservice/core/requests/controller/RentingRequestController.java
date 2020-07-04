package microservice.core.requests.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import microservice.core.requests.model.Advert;
import microservice.core.requests.model.RentingRequest;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RentingRequestController {

	@Autowired
	private RentingRequestService reqService;
	
	private String adServiceUrl = "http://localhost:8180/adverts-ms/api/";
	
	@GetMapping
	String test() {
		return "ovo je get iz request ms za prazan path";
	}
	@GetMapping(value="/user-kart/{id}")
	List<RentingRequest> getUserKart(@PathVariable Long id){
		return reqService.findByUser(id);
	}
	
	@PostMapping(value="/new-request")
	void createNewRequest(@RequestBody RentingRequestDTO data) {
		RentingRequest req = new RentingRequest();
		RestTemplate restTemplate = new RestTemplate();
		
		Advert reqAdvert = restTemplate.getForObject(adServiceUrl+data.getAdvertid(), Advert.class);
		
		req.setWhoasked(data.getUser_id());
		req.setWhoposted(reqAdvert.getUser_id());
		req.setStatus("PENDING");
		RentingRequest multiple = reqService.checkIfIAlreadyRequestedFrom(req.getWhoasked(), req.getWhoposted());
		if( multiple != null) // jos nismo narucivali od ovog vlasnika
		{
				multiple.getAdverts().add(reqAdvert);
				reqService.save(multiple);
		
		}else {
			req.getAdverts().add(reqAdvert);
			reqService.save(req);
		}
//		
	}
}
