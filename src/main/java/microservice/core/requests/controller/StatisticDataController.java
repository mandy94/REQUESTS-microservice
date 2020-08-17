package microservice.core.requests.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import microservice.core.requests.model.Advert;
import microservice.core.requests.model.StatisticData;
import microservice.core.requests.model.User;
import microservice.core.requests.model.DTO.StatisticDataDTO;
import microservice.core.requests.repository.StatisticDataRepository;

@RestController
@RequestMapping(value = "/api/statistic", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticDataController {

	@Autowired
	private StatisticDataRepository dataRepo;
	
	@PostMapping(value="/add")
	void declineRequest(@RequestHeader("Authorization") String header,@RequestBody StatisticDataDTO data) {
		StatisticData newData = new StatisticData();
		newData.setMilage(data.getMilage());
		
		
		String serviceUrl = "http://localhost:8184/api/" + data.getAdvertId();;
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", header );
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		RestTemplate restTemplate = new RestTemplate();
		Advert advert = restTemplate.exchange(serviceUrl, HttpMethod.GET,entity , Advert.class).getBody();
		advert.updateMilage(data.getMilage()); 
		dataRepo.save(newData);		
	}
	
}
