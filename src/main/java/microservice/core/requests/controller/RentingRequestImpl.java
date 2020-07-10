package microservice.core.requests.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import microservice.core.requests.model.BundleRequest;
import microservice.core.requests.model.RequestedCarTerm;


@Service
public class RentingRequestImpl implements RentingRequestService {
	@Autowired
	private RentingRequestRepository repo;
	

	@Override
	public List<BundleRequest> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BundleRequest findById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public List<BundleRequest> findByUser(Long id) {
		return repo.findByUser(id);
	}
	@Override
	public void save(BundleRequest r) {
		repo.save(r);
		
	}


	@Override
	public BundleRequest checkIfIAlreadyRequestedFrom(Long whoasked, Long whoposted) {
		BundleRequest req = repo.findMyRequestsFrom(whoasked, whoposted);
		return req;
	}

	@Override
	public List<BundleRequest> findMyRequestByStatus(Long id, String status) {
//		return repo.findMyRequestByStatus(id, status);
		return null;
	}

	@Override
	public List<BundleRequest> findForUser(Long id) {
		return repo.findByWhoPosted(id);
	}

	@Override
	public void deleteBundle(Long id) {
		repo.deleteById(id);
		
	}

	



	

}
