package microservice.requests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class RequestsMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RequestsMicroserviceApplication.class, args);
	}

}
