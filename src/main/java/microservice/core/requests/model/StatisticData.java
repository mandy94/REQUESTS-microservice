package microservice.core.requests.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="STATISTIC_DATA")
public class StatisticData {

	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
	
	@Column
	float milage;

	@Column
	Long advert_id;
	@Column	
	String advert_name;
	
	@Column 
	Integer rentNumber;
	
	@Column
	Long owner_id;
	
	
	public float getMilage() {
		return milage;
	}

	public void setMilage(float milage) {
		this.milage = milage;
	}

	public Long getAdvert_id() {
		return advert_id;
	}

	public void setAdvert_id(Long advert_id) {
		this.advert_id = advert_id;
	}

	public Long getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Long owner_id) {
		this.owner_id = owner_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdvert_name() {
		return advert_name;
	}

	public void setAdvert_name(String advert_name) {
		this.advert_name = advert_name;
	}

	public Integer getRentNumber() {
		return rentNumber;
	}

	public void setRentNumber(Integer rentNumber) {
		this.rentNumber = rentNumber;
	}

	public void incrementRentNumber() {
		this.rentNumber++;
		
	}
}
