package dev.satwant.springbootMongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ServerApplication{

public static void main(String[] args){
	SpringApplication.run(ServerApplication.class, args);
}

@GetMapping("/")
public String apiRoot(){
	return "server side of springbootMongodb todo application. Programmed by Satwant Kumar (satwant.ug21.ec@nitp.ac.in).";
}
}
