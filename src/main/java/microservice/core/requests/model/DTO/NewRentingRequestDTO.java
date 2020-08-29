package microservice.core.requests.model.DTO;

import microservice.core.requests.model.RequestedCarTerm;

public class NewRentingRequestDTO {

	
	String rentingDate;
	String returningDate;
	String rentingTime;
	String returningTime;

	
	Long advertid;	
	Long whoasked; // for promting in requests
	
	public NewRentingRequestDTO() {}
	 
	public NewRentingRequestDTO(RequestedCarTerm term) {
		this.rentingDate =term.getRentingDate();
		this.returningDate = term.getReturningDate();
		this.rentingTime = term.getRentingTime();
		this.returningTime = term.getReturningTime();
				
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
	


	public Long getAdvertid() {
		return advertid;
	}

	public void setAdvertid(Long advertid) {
		this.advertid = advertid;
	}

	@Override
	public String toString() {
		return "NewRentingRequestDTO [rentingDate=" + rentingDate + ", returningDate=" + returningDate
				+ ", rentingTime=" + rentingTime + ", returningTime=" + returningTime + ", advertid=" + advertid
				+ ", whoasked=" + whoasked + "]";
	}

	

	
}
