package com.example.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.checkerframework.checker.units.qual.Current;

import java.time.Instant;

public class GrpcClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 1234;


    public void run(int idSala, int idDispositivo) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();


        MetricsServiceGrpc.MetricsServiceBlockingStub stub = MetricsServiceGrpc.newBlockingStub(channel);
        double temperatura = (double) (Math.random() * 15.00) + 15.00;
        double humidade = (double) (Math.random() * 50.00) + 30.00;



        while (true) {
            Long iso = Instant.now().toEpochMilli();
            Metric metric = Metric.newBuilder()
                    .setTemperatura(temperatura)
                    .setHumidade(humidade)
                    .setIdSala(idSala)
                    .setIdDispositivo(idDispositivo)
                    .setTimestamp(iso)
                    .build();
            MetricResponse response = stub.sendMetric(metric);
            System.out.println("Resposta do servidor: " + response.getMessage());

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

            int idSala = 1 + i;
            int idDispositivo = ((i + 1) * 3);

            Thread t = new Thread(() -> {
                try {
                    new GrpcClient().run(idSala, idDispositivo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }
}