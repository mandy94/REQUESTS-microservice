package microservice.core.requests.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import microservice.core.requests.model.BundleRequest;
import microservice.core.requests.model.StatisticData;

@Repository
public interface StatisticDataRepository extends JpaRepository<StatisticData, Long> {

	
	
	
	
	
}
