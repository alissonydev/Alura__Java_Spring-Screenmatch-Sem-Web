package com.github.alissonydev.screenmatch.principal;

import com.github.alissonydev.screenmatch.models.DadosEpisodio;
import com.github.alissonydev.screenmatch.models.DadosSerie;
import com.github.alissonydev.screenmatch.models.DadosTemporada;
import com.github.alissonydev.screenmatch.services.ConsumoApi;
import com.github.alissonydev.screenmatch.services.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    final Scanner leitura = new Scanner(System.in);
    final ConsumoApi consumoApi = new ConsumoApi();
    final ConverteDados conversor = new ConverteDados();
    private static final String URL = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = System.getenv("YOUR_API_KEY");

    public void exibeMenu() {

        System.out.println();
        System.out.println("=== MENU ===");
        System.out.println("Digite um nome de um filme para busca:");

        final String nomeSerie = leitura.nextLine();

        var json = consumoApi
                .obterDados(URL + nomeSerie.replace(" ", "+") + "&apikey=" + API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        System.out.println();
        System.out.println("WAIT... Buscando temporadas...");

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            var url = "https://www.omdbapi.com/?t=gilmore+girls&season=" + i +"&apikey=";
            json = consumoApi.obterDados(url + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
        //    System.out.println(dadosTemporada);
            temporadas.add(dadosTemporada);
        }
        System.out.println("-----------------------------------");
        temporadas.forEach(System.out::println);

        System.out.println();
        System.out.println("=== LISTA DE TEMPORADAS E EPISÓDIOS ===");
        temporadas.forEach(x -> {
            System.out.println("Temporada: " + x.numero());
            x.episodios().forEach(epis ->
                System.out.println("Episódio: " + epis.numero() + " - " + epis.titulo())
            );
        });

        System.out.println();
        System.out.println("--------------------------------------");

        for (int i = 0; i < dados.totalTemporadas(); i++) {
            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
               // DadosEpisodio episodio = episodiosTemporada.get(j);
//                System.out.println("Temporada " + (i + 1) + " - Episódio " + episodio.numero() +
//                        ": " + episodio.titulo() + " | Avaliação: " + episodio.avaliacao() +
//                        " | Data de Lançamento: " + episodio.dataLancamento());
            }
        }

        // usando lambda
        System.out.println();
        System.out.println("=== DETALHES DOS EPISÓDIOS ===");
         temporadas.forEach(temporada -> {
             temporada.episodios().forEach(episodio -> {
                 System.out.println("Temporada " + temporada.numero() + " - Episódio " + episodio.numero() +
                         ": " + episodio.titulo() + " | Avaliação: " + episodio.avaliacao() +
                         " | Data de Lançamento: " + episodio.dataLancamento());
             });
         });


        // "https://www.omdbapi.com/?t=gilmore+girls&season=6585022c"
//        System.out.println("Digite o nome da série ou filme que deseja buscar:");
//        System.out.println("=== MENU ===");
//        System.out.println("1. Adicionar filme");
//        System.out.println("2. Listar filmes");
//        System.out.println("3. Sair");
    }
}
