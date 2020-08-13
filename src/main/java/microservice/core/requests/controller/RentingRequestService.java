package microservice.core.requests.controller;

import java.util.List;

import microservice.core.requests.model.BundleRequest;
import microservice.core.requests.model.RequestedCarTerm;


public interface RentingRequestService {
	List<BundleRequest>findAll();
	BundleRequest findById(Long id);
	void save(BundleRequest r);
	List<BundleRequest> findByUser(Long id);
	BundleRequest checkIfIAlreadyRequestedFrom(Long whoasked, Long whoposted);
	List<RequestedCarTerm> findMyRequestByStatus(Long id, String string);
	List<BundleRequest> findForUser(Long id);
	void deleteBundle(Long id);
	Long findOwnerOfTheRequest(Long id);
}
