package com.github.alissonydev.screenmatch;

import com.github.alissonydev.screenmatch.services.ConsumoApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        final String url = "https://www.omdbapi.com/?t=gilmore+girls&Seanson=1&apikey=";

        final ConsumoApi consumoApi = new ConsumoApi();
        final String json = consumoApi.obterDados(url);

        System.out.println(json);
    }
}
