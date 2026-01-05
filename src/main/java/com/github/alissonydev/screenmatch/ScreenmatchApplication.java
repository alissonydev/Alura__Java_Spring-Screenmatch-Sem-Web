package com.github.alissonydev.screenmatch;

import com.github.alissonydev.screenmatch.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    private static final String YOUR_API_KEY = System.getenv("YOUR_API_KEY");

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        final Principal principal = new Principal();
        principal.exibeMenu();
    }
}
