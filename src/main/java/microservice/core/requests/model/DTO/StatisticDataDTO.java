package microservice.core.requests.model.DTO;

public class StatisticDataDTO {

	float milage;
	
	Long advertId;
	Long ownerId;

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

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
}
