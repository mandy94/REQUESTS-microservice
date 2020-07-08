package microservice.core.requests.model.DTO;

import microservice.core.requests.model.Advert;
import microservice.core.requests.model.BundleRequest;

public class RentingRequestDTO {

	Long id;
	String rentingDate;
	String returningDate;
	String rentingTime;
	String returningTime;

	Advert advert;
	String status;
	
	public RentingRequestDTO() {}
	 
	public RentingRequestDTO(BundleRequest req) {
	}
	public String getRentingDate() {
		return rentingDate;
	}
	public void setRentingDate(String rentingDate) {
		this.rentingDate = rentingDate;
	}
	public String getReturningDate() {
		return returningDate;
	}
	public void setReturningDate(String returningDate) {
		this.returningDate = returningDate;
	}
	public String getRentingTime() {
		return rentingTime;
	}
	public void setRentingTime(String rentingTime) {
		this.rentingTime = rentingTime;
	}
	public String getReturningTime() {
		return returningTime;
	}
	public void setReturningTime(String returningTime) {
		this.returningTime = returningTime;
	}
	
	public Advert getAdvert() {
		return advert;
	}
	public void setAdvert(Advert advert) {
		this.advert = advert;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	
}
