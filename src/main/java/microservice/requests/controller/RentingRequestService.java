package microservice.requests.controller;

import java.util.List;

import microservice.requests.model.RentingRequest;


public interface RentingRequestService {
	List<RentingRequest>findAll();
	RentingRequest findById(Long id);
	void save(RentingRequest r);
	List<RentingRequest> findByUsername(String username);
	RentingRequest checkIfIAlreadyRequestedFrom(Long whoasked, Long whoposted);
	List<RentingRequest> findMyRequestByStatus(Long id, String string);
}
