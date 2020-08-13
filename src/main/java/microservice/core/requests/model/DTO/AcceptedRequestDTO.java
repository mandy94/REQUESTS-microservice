package microservice.core.requests.model.DTO;

import java.util.ArrayList;
import java.util.List;

import microservice.core.requests.model.RequestedCarTerm;

public class AcceptedRequestDTO {
	private Long id;
	private List<RequestedCarTerm> conflict = new ArrayList<RequestedCarTerm>();
	private String advertManufacturer;
	private Long advertId;
	public AcceptedRequestDTO() {}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<RequestedCarTerm> getConflict() {
		return conflict;
	}

	public void setConflict(List<RequestedCarTerm> conflict) {
		this.conflict = conflict;
	}

	public String getAdvertManufacturer() {
		return advertManufacturer;
	}

	public void setAdvertManufacturer(String advertManufacturer) {
		this.advertManufacturer = advertManufacturer;
	}

	public Long getAdvertId() {
		return advertId;
	}

	public void setAdvertId(Long advertId) {
		this.advertId = advertId;
	}



	@Override
	public String toString() {
		return "AcceptedRequestDTO [id=" + id + ", conflict=" + conflict + ", advertManufacturer=" + advertManufacturer
				+ ", advertId=" + advertId + "]";
	}

}
