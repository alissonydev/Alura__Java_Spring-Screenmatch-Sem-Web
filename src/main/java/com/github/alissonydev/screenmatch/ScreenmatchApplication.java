package com.github.alissonydev.screenmatch;

import com.github.alissonydev.screenmatch.services.ConsumoApi;
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

        final String url = "https://www.omdbapi.com/?t=gilmore+girls&Seanson=1&apikey=";

        final ConsumoApi consumoApi = new ConsumoApi();
        var json = consumoApi.obterDados(url + YOUR_API_KEY);
        System.out.println("-----------------------------------");
        System.out.println(json);
        System.out.println("-----------------------------------");

        final String coffee_img = "https://coffee.alexflipnote.dev/random.json";
        json = consumoApi.obterDados(coffee_img);
        System.out.println(json);
    }
}
