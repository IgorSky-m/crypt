package it.academy.miner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MinerApplication {


	static CommandExecutor executor;


	public static void main(String[] args) {
		SpringApplication.run(MinerApplication.class, args);
		executor.run();

	}


	@Bean
	public static CommandLineRunner startApp(CommandExecutor commandExecutor) {
		return args -> executor = commandExecutor;
	}

}