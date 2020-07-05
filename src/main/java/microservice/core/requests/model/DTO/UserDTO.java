package microservice.core.requests.model.DTO;

import microservice.core.requests.model.User;

public class UserDTO {

	Long id;
	String username;
	String fullName;
	public UserDTO() {}
	public UserDTO(User body) {
		this.username = body.getUsername();
		this.fullName = body.getFirstName() + " " + body.getLastName();
		this.id = body.getId();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
