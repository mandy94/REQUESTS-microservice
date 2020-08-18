package microservice.core.requests.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;

import microservice.core.requests.model.Advert;
import microservice.core.requests.model.StatisticData;
import microservice.core.requests.model.DTO.StatisticDataDTO;
import microservice.core.requests.repository.StatisticDataRepository;

@RestController
@RequestMapping(value = "/api/statistic", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticDataController {

	@Autowired
	private StatisticDataRepository dataRepo;
	
	@PostMapping(value="/add")
	void commitNewStat(@RequestHeader("Authorization") String header,@RequestBody StatisticDataDTO data) {
		
		String serviceUrl = "http://localhost:8184/api/" + data.getAdvertId();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", header );
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		RestTemplate restTemplate = new RestTemplate();
		Advert advert = restTemplate.exchange(serviceUrl, HttpMethod.GET,entity , Advert.class).getBody();
		advert.updateMilage(data.getMilage()); 
		serviceUrl = "http://localhost:8184/api/update-mil";
		HttpEntity<Advert> httpAdvert = new  HttpEntity<>(advert);		
		restTemplate.exchange(serviceUrl, HttpMethod.PUT , httpAdvert , void.class);
		StatisticData newData =  dataRepo.findByAdvertId(advert.getId()).orElse(null);
		if(newData == null)
			newData = new StatisticData();
		newData.setMilage(advert.getMilage());		
		newData.setAdvert_id(advert.getId());
		newData.setAdvert_name(advert.getTitle());
		newData.setOwner_id(data.getOwnerId());
		dataRepo.save(newData);		
	}
	
	private class Pair{
		public Pair(String x, float y) { this.label = x; this.y = y;}
		public String label;
		public float y;
	}
	
	@GetMapping(value="/milage")
	ArrayList<Pair> getAdvertsOrderByMilage(@RequestHeader("Authorization") String header){
		ArrayList<Pair> chartData = new ArrayList<>();
		for( StatisticData x: dataRepo.findAll()) {
			chartData.add(new Pair(x.getAdvert_name(), x.getMilage()));
		}
		return chartData;
	}
	
}
