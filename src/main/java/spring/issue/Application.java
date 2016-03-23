package spring.issue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@RestController
@Configuration
public class Application {

	Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired
	private RestTemplate rest;

	@RequestMapping("/")
	public String endpoint_1(){
		rest.getForObject("http://localhost:8080/2", String.class);
		return "1";
	}

	@RequestMapping("/2")
	public String endpoint_2(@RequestHeader HttpHeaders headers){
		log.info("Endpoint 2 received headers " + headers);
		return "1";
	}

	@Bean
	public Sampler shouldNeverSample(){
		return span -> false;
	}

	@Bean
	public ZipkinSpanReporter reporter(){
		return span -> log.info("Should have reported span to Zipkin: " + span);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
