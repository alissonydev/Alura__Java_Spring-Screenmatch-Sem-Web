package com.github.alissonydev.screenmatch;

import com.github.alissonydev.screenmatch.models.DadosEpisodio;
import com.github.alissonydev.screenmatch.models.DadosSerie;
import com.github.alissonydev.screenmatch.models.DadosTemporada;
import com.github.alissonydev.screenmatch.services.ConsumoApi;
import com.github.alissonydev.screenmatch.services.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    private static final String YOUR_API_KEY = System.getenv("YOUR_API_KEY");

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        String url = "https://www.omdbapi.com/?t=gilmore+girls&Seanson=1&apikey=";

        final ConsumoApi consumoApi = new ConsumoApi();
        var json = consumoApi.obterDados(url + YOUR_API_KEY);
        System.out.println("-----------------------------------");
        System.out.println(json);
        System.out.println("-----------------------------------");

        final String coffee_img = "https://coffee.alexflipnote.dev/random.json";
        json = consumoApi.obterDados(coffee_img);
        System.out.println(json);

        url = "https://www.omdbapi.com/?t=gilmore+girls&apikey=";
        json = consumoApi.obterDados(url + YOUR_API_KEY);
        System.out.println(url + YOUR_API_KEY);
        System.out.println(json);

        final ConverteDados conversor = new ConverteDados();
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        url = "https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=";
        json = consumoApi.obterDados(url + YOUR_API_KEY);
        DadosEpisodio episodio = conversor.obterDados(json, DadosEpisodio.class);
        System.out.println("-----------------------------------");
        System.out.println(episodio);
        System.out.println();
        System.out.println();



        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            url = "https://www.omdbapi.com/?t=gilmore+girls&season=" + i +"&apikey=";
            json = consumoApi.obterDados(url + YOUR_API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            System.out.println(dadosTemporada);
            temporadas.add(dadosTemporada);
        }

        System.out.println();
        System.out.println("-----------------------------------");

        temporadas.forEach(System.out::println);

        System.out.println();
        System.out.println("-----------------------------------");

        temporadas.forEach(x -> {
            System.out.println("Temporada: " + x.numero());
            x.episodios().forEach(epis ->
                System.out.println("Episódio: " + epis.numero() + " - " + epis.titulo())
            );
        });
    }
}
