package com.github.alissonydev.screenmatch.principal;

import com.github.alissonydev.screenmatch.services.ConsumoApi;

import java.util.Scanner;

public class Principal {

    final ConsumoApi consumoApi = new ConsumoApi();
    private static final String URL = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = System.getenv("YOUR_API_KEY");
    //gilmore+girls&apikey

    public void exibeMenu() {

        System.out.println();
        System.out.println("=== MENU ===");
        System.out.println("Digite um nome de um filme");
        final Scanner leitura = new Scanner(System.in);
        final String nomeSerie = leitura.nextLine();
        String urlCompleta = URL + nomeSerie.replace(" ", "+") + "&apikey=" + API_KEY;
        System.out.println(urlCompleta);


        var json = consumoApi.obterDados(urlCompleta);
        System.out.println(json);

        // "https://www.omdbapi.com/?t=gilmore+girls&season=6585022c"
//        System.out.println("Digite o nome da série ou filme que deseja buscar:");
//        System.out.println("=== MENU ===");
//        System.out.println("1. Adicionar filme");
//        System.out.println("2. Listar filmes");
//        System.out.println("3. Sair");
    }
}
