package ma.project.polynome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceCalculPolynomialApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceCalculPolynomialApplication.class, args);
	}

}
