package microservice.core.requests.controller;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import microservice.core.requests.model.BundleRequest;

@Repository
public interface RentingRequestRepository extends JpaRepository<BundleRequest, Long> {

	@Query("Select u from BundleRequest u where u.whoasked = :un")
	List<BundleRequest> findByUser(@Param(value="un") Long username);

	@Query("Select u from BundleRequest u where u.whoposted = :wh")
	List<BundleRequest> findByWhoPosted(@Param(value="wh") Long whoposted);

	@Query("Select u from BundleRequest u where u.whoasked = :wa and u.whoposted = :wp")
	BundleRequest findMyRequestsFrom(@Param(value="wa") Long whoasked,@Param(value="wp")  Long whoposted);

//	@Query("Select u from BundleRequest u where u.whoasked = :id and u.status = :status")
//	List<BundleRequest> findMyRequestByStatus(@Param(value="id")Long id, @Param(value="status")String status);

	
	
	
	
}
