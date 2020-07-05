package microservice.core.requests.model.DTO;

import java.util.ArrayList;
import java.util.List;

public class BundleRequestsDTO {

	UserDTO owner;
	UserDTO client;
	List<RentingRequestDTO> content = new ArrayList<>();

	public UserDTO getOwner() {
		return owner;
	}

	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}

	public UserDTO getClient() {
		return client;
	}

	public void setClient(UserDTO client) {
		this.client = client;
	}

	public List<RentingRequestDTO> getContent() {
		return content;
	}

	public void setContent(List<RentingRequestDTO> content) {
		this.content = content;
	}
}
