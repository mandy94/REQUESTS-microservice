package microservice.core.requests.model.DTO;

public class StatisticDataDTO {

	float milage;
	
	Long advertId;

	public float getMilage() {
		return milage;
	}

	public void setMilage(float milage) {
		this.milage = milage;
	}

	public Long getAdvertId() {
		return advertId;
	}

	public void setAdvertId(Long advertId) {
		this.advertId = advertId;
	}
}
