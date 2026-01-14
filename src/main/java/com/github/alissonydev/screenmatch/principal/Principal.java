package com.github.alissonydev.screenmatch.principal;

import com.github.alissonydev.screenmatch.models.DadosEpisodio;
import com.github.alissonydev.screenmatch.models.DadosSerie;
import com.github.alissonydev.screenmatch.models.DadosTemporada;
import com.github.alissonydev.screenmatch.models.Episodio;
import com.github.alissonydev.screenmatch.services.ConsumoApi;
import com.github.alissonydev.screenmatch.services.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

         // Pegando os 5 episódios mais bem avaliados da nossa Série
        // --------------------------------
        final List<DadosEpisodio> dadosEpisodio = temporadas.stream()
                .flatMap(t -> t.episodios()
                        .stream())
                .collect(Collectors.toList());// transforma em uma lista só e pode editar.
                // .toList(); // transforma em uma lista imutável

//        System.out.println();
//        System.out.println("=== TOP 10 EPISÓDIOS MAIS BEM AVALIADOS ===");
//        //dadosEpisodio.forEach(System.out::println);
//        dadosEpisodio.stream()
//                .filter(e -> !e.avaliacao().equals("N/A"))
//                .peek(e -> System.out.println("Primeiro Filtro (N/A) " + e))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(e -> System.out.println("\nOrdenação " + e))
//                .limit(10)
//                .peek(e -> System.out.println("\nLimite " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("\nMapeamento " + e))
//                //.forEach(e -> System.out.println(e.titulo() + " - Avaliação: " + e.avaliacao()));
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        System.out.println();
        episodios.forEach(System.out::println);

        System.out.println();
        System.out.println("Digite um titulo para buscar o episódio:");
        String tituloBusca = leitura.nextLine().toLowerCase();
        final Optional<Episodio> episodioBuscado
                = episodios.stream()
                .filter(e -> e.getTitulo().toLowerCase().contains(tituloBusca))
                .findFirst();

        if (episodioBuscado.isPresent()) {
            System.out.println("Episódio encontrado: " + episodioBuscado.get());
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
        } else {
            System.out.println("Episódio não encontrado.");
        }
//
//
//        System.out.println();
//        System.out.println("A partir de que ano você deseja ver os episódios?");
//        int anoFiltro = Integer.parseInt(leitura.nextLine());
//
//        final LocalDate dataBusca = LocalDate.of(anoFiltro , 1 , 1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(episodio -> System.out.println(
//                        "Temporada: " + episodio.getTemporada() +
//                                " - Episódio: " + episodio.getNumeroEpisodio() +
//                                " | " + episodio.getTitulo() +
//                                " - Data de Lançamento: " + episodio.getDataLancamento().format(formatador)));


//        Arrays.asList("A", "B", "C").forEach(System.out::println);
//        final List<String> nomes = Arrays.asList("Jacque" , "Maria" , "Pedro");
//
//        nomes.stream().sorted().limit(3).filter(n -> n.startsWith("M")).map(String::toUpperCase)
//                .forEach(System.out::println);


        // "https://www.omdbapi.com/?t=gilmore+girls&season=6585022c"
//        System.out.println("Digite o nome da série ou filme que deseja buscar:");
//        System.out.println("=== MENU ===");
//        System.out.println("1. Adicionar filme");
//        System.out.println("2. Listar filmes");
//        System.out.println("3. Sair");
    }
}
