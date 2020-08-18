package microservice.core.requests.repository;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import microservice.core.requests.model.StatisticData;

@Repository
public interface StatisticDataRepository extends JpaRepository<StatisticData, Long> {



	@Query("Select o from StatisticData o where advert_id = :id ")
	Optional<StatisticData> findByAdvertId(@Param("id") Long id);

	
	
	
	
	
}
