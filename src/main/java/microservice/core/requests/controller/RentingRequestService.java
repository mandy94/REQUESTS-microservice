package microservice.core.requests.controller;

import java.util.List;

import microservice.core.requests.model.RentingRequest;


public interface RentingRequestService {
	List<RentingRequest>findAll();
	RentingRequest findById(Long id);
	void save(RentingRequest r);
	List<RentingRequest> findByUser(Long id);
	RentingRequest checkIfIAlreadyRequestedFrom(Long whoasked, Long whoposted);
	List<RentingRequest> findMyRequestByStatus(Long id, String string);
}
