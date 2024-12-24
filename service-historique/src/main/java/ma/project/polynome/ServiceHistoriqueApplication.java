package ma.project.polynome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "ma.project.polynome")
@EnableFeignClients(basePackages = "ma.project.polynome")
public class ServiceHistoriqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceHistoriqueApplication.class, args);
	}

}
