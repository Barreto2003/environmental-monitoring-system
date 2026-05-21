package com.example.rest;

import com.example.rest.classes.PreMetrica;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.time.Instant;

public class MetricsRestClient {
    private final RestClient client;

    public MetricsRestClient() {
        this.client = RestClient.builder().build();
    }



    public void postMetric(PreMetrica m){
        String resposta = client.post().uri("http://localhost:8080/api/metrics/ingest")
                .body(m)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);
        System.out.println("Resposta do servidor: " + resposta);
    }

    public void run(int id, int idDispositivo) throws InterruptedException {
        double temperatura = (double) (Math.random() * 15.00) + 15.00;
        double humidade = (double) (Math.random() * 50.00) + 30.00;

        String iso = Instant.now().toString();
        while (true) {

            PreMetrica m = new PreMetrica(temperatura,humidade,Instant.now().toEpochMilli(),id, idDispositivo);
            postMetric(m);

            double variacao = (double) (Math.random() * 1.50);
            int desce = (int) (Math.random() * 2);

            if (desce == 1) {
                if (temperatura - variacao < 15 && humidade - variacao < 30) {
                    temperatura += variacao;
                    humidade += variacao;
                } else if (temperatura - variacao < 15) {
                    temperatura += variacao;
                    humidade -= variacao;
                } else if (humidade - variacao < 30) {
                    temperatura -= variacao;
                    humidade += variacao;
                }
            } else if (desce == 0) {
                if (temperatura + variacao > 30 && humidade + variacao > 80) {
                    temperatura -= variacao;
                    humidade -= variacao;
                } else if (temperatura + variacao > 30) {
                    temperatura -= variacao;
                    humidade += variacao;
                } else if (humidade + variacao < 80) {
                    temperatura += variacao;
                    humidade -= variacao;
                }
            }
           Thread.sleep(5000);
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {

            int idSala = 1+i;
            int idDispositivo= ((i+1) * 3)-1;

            Thread t = new Thread(() -> {
                try {
                    new MetricsRestClient().run(idSala, idDispositivo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }
}
