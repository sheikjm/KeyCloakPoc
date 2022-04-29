package core.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController("/v1")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@GetMapping("/login")
	private String login(){
		return "login is successfull";
	}
	@GetMapping("/dashboard")
	public String dashboard(){
		return "Welcome to dashboard";
	}

	@GetMapping("/myAccount")
	public String myAccount(){
		return "Welcome to myAccount";
	}



}
