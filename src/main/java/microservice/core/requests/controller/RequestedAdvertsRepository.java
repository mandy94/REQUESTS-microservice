package microservice.core.requests.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import microservice.core.requests.model.RequestedCarTerm;

@Repository
public interface RequestedAdvertsRepository extends JpaRepository<RequestedCarTerm, Long> {

	@Query("Select u from RequestedCarTerm u where u.rent_id.id = :id")
	List<RequestedCarTerm> findTermByRequestId(@Param(value="id") Long id);
	

	@Query("Select u from RequestedCarTerm u where u.rent_id.id = :id and u.status = :status")
	List<RequestedCarTerm> findTermsByRequestIdAndStatus(@Param(value="id") Long id, @Param(value="status") String status);
	

	
}
