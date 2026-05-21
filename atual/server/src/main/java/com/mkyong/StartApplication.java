package com.mkyong;

import com.mkyong.bd.entidades.Dispositivo;
import com.mkyong.bd.entidades.Sala;
import com.mkyong.bd.repositorios.DispositivoRepository;
import com.mkyong.bd.repositorios.MétricasRepository;
import com.mkyong.bd.repositorios.SalaRepository;
import com.mkyong.grpc.MetricsServiceImpl;
import com.mkyong.mqtt.MqttSubscriber;
import com.mkyong.rest.service.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class StartApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StartApplication.class);

    @Autowired
    private SalaRepository SalaRepository;

    @Autowired
    private DispositivoRepository DispositivoRepository;

    @Autowired
    private MétricasRepository MétricasRepository;

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Override
    public void run(String... args) {

        Thread thread = new Thread(() -> {
            try {
                Server server = ServerBuilder
                        .forPort(1234)
                        .addService(new MetricsServiceImpl(MétricasRepository, SalaRepository, DispositivoRepository))
                        .build();
                server.start();
                System.out.println("Servidor gRPC iniciado na porta 1234");
                server.awaitTermination();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();


        System.out.println("Preencher base de dados");

        List<String> departamentos = new ArrayList<>(Arrays.asList( "Informatica", "Matematica", "Fisica", "Quimica", "Desporto", "Psicologia", "Sociologia", "Geografia", "Historia" ));
        List<String> edificios = new ArrayList<>(Arrays.asList("Colegio_Luis_Antonio_Verney","Polo_da_Mitra", "Colegio_dos_Leoes", "Colegio_Pedro_da_Fonseca","Colegio_do_Espirito_Santo"));
        List<String> tipo = new ArrayList<>(Arrays.asList("SalaDeAulas", "Laboratorio", "Auditorio", "Gabinete"));
        int numSalas = 100;
        int andares = 2;

        List<Sala> todas = SalaRepository.findAll();

        int i = 1;
        for (Sala s : todas) {
            String topic = s.getEdificio() + "/" + s.getNumero();
            String clientId = String.valueOf(i);
            new MqttSubscriber(clientId, topic, "tcp://localhost:1883", DispositivoRepository, MétricasRepository, SalaRepository).start();

            i++;
        }
    }
}