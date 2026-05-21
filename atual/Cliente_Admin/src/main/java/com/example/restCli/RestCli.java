package com.example.restCli;

import com.example.restCli.classes.Dispositivo;
import com.example.restCli.classes.Métrica;
import com.example.restCli.classes.Sala;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.BodyInserters;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class RestCli {
    private final RestClient client;

    public RestCli() {
        this.client = RestClient.builder().build();
    }

    public static void menu() {
        System.out.println("-----------------MENU-----------------\n" +
                "1 - Gestão de Dispositivos\n" +
                "2 - Consulta de Métricas\n" +
                "3 - Estatisticas do Sistema\n" +
                "1234 - Desligar cliente");
    }

    public static void gestaoDeDispositivos() {
        System.out.println("-----------------Gestão de Dispositivos-----------------\n" +
                "1 - Listar todos os dispositivos\n" +
                "2 - Adicionar novo dispositivo\n" +
                "3 - Atualizar dispositivo existente\n" +
                "4 - Remover dispositivo\n" +
                "5 - Visualizar detalhes\n" +
                "1234 - Sair de Gestão de Dispositivos");
    }

    public void activateDevice(int id) {
        String resposta = client.post().uri("http://localhost:8080/api/devices")
                .body(id)
                .retrieve()
                .body(String.class);
        System.out.println("Resposta do servidor: " + resposta);
    }

    public void associarDispositivo(Scanner sc) throws MqttException, InterruptedException {

        boolean sair = false;
        String protocolo = "";
        while (!sair) {
            System.out.println("Qual é o protocolo que pretende utilizar? \n" +
                    "Opções: [1] MQTT [2] gRPC [3] REST ");
            int opcao = Integer.parseInt(sc.nextLine().split(" ")[0]);
            protocolo = (opcao == 1) ? "MQTT" : (opcao == 2) ? "gRPC" : (opcao == 3) ? "REST" : "DESCONHECIDO";
            if (!protocolo.equals("DESCONHECIDO")) {
                sair = true;
            }
        }
        System.out.println("Estao aqui todas as salas livres");

        Sala[] salasLivres = getAllRooms();
        for (Sala s : salasLivres) {
            System.out.println(s);
        }

        sair = false;
        int id = -1;
        Sala aAdicionar = null;

        while (!sair) {
            System.out.println("Diga qual é o id da sala que pretende associar o dispositivo");
            id = Integer.parseInt(sc.nextLine().split(" ")[0]);
            boolean encontrou = false;
            for (Sala s : salasLivres) {
                if (id == s.getId()) {
                    encontrou = true;
                    aAdicionar = s;
                }
            }
            if (encontrou) {
                sair = true;
                break;
            } else {
                System.out.println("Ou esse id não existe ou não está livre");
                salasLivres = getAllRooms();
                for (Sala s : salasLivres) {
                    System.out.println(s);
                }
            }
        }
        if (protocolo.equals("MQTT")) {
            int id_mqtt = (id * 3) - 2;
            activateDevice(id_mqtt);
        } else if (protocolo.equals("gRPC")) {
            int id_grpc = id * 3;
            activateDevice(id_grpc);
        } else if (protocolo.equals("REST")) {
            int id_rest = (id * 3) - 1;
            activateDevice(id_rest);
        }
    }

    public Sala[] getAllRooms() {
        return client.get()
                .uri("http://localhost:8080/api/rooms/free")
                .retrieve()
                .body(Sala[].class);
    }

    public void vizualizarDetalhes(Scanner sc) {
        System.out.println("Diga o id do dispositivo");
        int id = Integer.parseInt(sc.nextLine().split(" ")[0]);
        String detalhes = getDetalhes(id).toString();
        System.out.println("----------------- DETALHES -----------------");
        System.out.println(detalhes);

    }

    public Dispositivo getDetalhes(int id) {
        return client.get()
                .uri("http://localhost:8080/api/devices/" + id)
                .retrieve()
                .body(Dispositivo.class);
    }

    public Dispositivo[] getAllDisp() {
        return client.get()
                .uri("http://localhost:8080/api/devices")
                .retrieve()
                .body(Dispositivo[].class);
    }

    public Dispositivo[] allActiveDisp() {
        Dispositivo[] dispositivos = getAllDisp();
        System.out.println("- Dispositivos ativos -");
        for (Dispositivo d : dispositivos) {
            System.out.println(d);
        }
        return dispositivos;
    }

    public boolean update(int deviceId, Dispositivo bodyObject) {
        try {
            client.put()
                    .uri("http://localhost:8080/api/devices/{id}", deviceId)
                    .body(bodyObject)
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void atualizar(Scanner sc) {
        System.out.println("-----------MENU ATUALIZAR-----------\n" +
                "1 - Mudar nome de Dispositivo\n");
        int opcao = Integer.parseInt(sc.nextLine().split(" ")[0]);

        switch (opcao) {

            case 1:
                Dispositivo[] ativos = allActiveDisp();
                System.out.println("Diga qual é o id que pretende alterar");
                int idDisp = Integer.parseInt(sc.nextLine().split(" ")[0]);
                boolean find = false;
                for (Dispositivo a : ativos) {
                    if (a.getId() == idDisp) {
                        find = true;
                    }
                }
                if (!find) {
                    System.out.println("Esse id não pertence a lista");
                    break;
                } else {
                    System.out.println("Insira o nome para o qual pretende mudar");
                    String nome = sc.nextLine().split(" ")[0];
                    Dispositivo d = getDetalhes(idDisp);
                    d.setNome(nome);
                    update(idDisp, d);
                }
                break;
            default:
                System.out.println("Inseriu uma opçao invalida");
                break;
        }

    }

    public void delete(Scanner sc) {
        Dispositivo[] ativos = allActiveDisp();
        System.out.println("Diga qual é o id que pretende alterar");
        int idDisp = Integer.parseInt(sc.nextLine().split(" ")[0]);
        boolean find = false;
        for (Dispositivo a : ativos) {
            if (a.getId() == idDisp) {
                find = true;
            }
        }
        if (!find) {
            System.out.println("Esse id não pertence a lista");
        } else {
            client.delete()
                    .uri("http://localhost:8080/api/devices/{id}", idDisp)
                    .retrieve()
                    .toBodilessEntity();
        }
    }

    public static void menuconsultaMetricas() {
        System.out.println("1 - Consultar por sala\n" +
                "2 - Consultar por departamento\n" +
                "3 - Consultar por piso\n" +
                "4 - Consultar por edifício\n" +
                "5 - Consultar de um dispositivo em especifico\n" +
                "1234 - Sair do menu das Métricas");
    }

    public Métrica[] metricaBruta(Scanner sc) {
        List<Integer> ids = dispositivosComMetrica();
        System.out.println(ids);
        System.out.println("Diga qual é o id que pretende consultar as metricas");
        int idd = Integer.parseInt(sc.nextLine().split(" ")[0]);
        boolean contains = false;
        for (Integer a : ids) {
            if (a == idd) {
                contains = true;
                break;
            }
        }
        if (contains) {
            System.out.println("Pretende filtrar por datas? (s/n)");
            String option = sc.nextLine().trim().toLowerCase();
            String from = null;
            String to = null;
            if (option.equals("s")) {
                System.out.println("Indique a data inicial (formato: yyyy-MM-ddTHH:mm:ss)");
                from = sc.nextLine().trim();
                System.out.println("Indique a data final (formato: yyyy-MM-ddTHH:mm:ss)");
                to = sc.nextLine().trim();
            }
            return client.get().uri("http://localhost:8080/api/metrics/raw?id=" + idd + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : ""))
                    .retrieve()
                    .body(Métrica[].class);
        }
        return null;
    }

    public Sala[] salaComMetricas() {
        return client.get().
                uri("http://localhost:8080/api/rooms/comMetricas")
                .retrieve()
                .body(Sala[].class);
    }
public Dispositivo[] dispositivosAtivos(){
        return client.get().
            uri("http://localhost:8080/api/devices/ativos")
            .retrieve()
            .body(Dispositivo[].class);
}


    public List<Integer> dispositivosComMetrica() {
        return client.get().uri("http://localhost:8080/api/devices/comMetricas").retrieve().body(new ParameterizedTypeReference<List<Integer>>() {
        });
    }

    public String getAverage(String level, String id, String from, String to) {
        return client.get().uri("http://localhost:8080/api/metrics/average?level=" + level + "&id=" + id + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : ""))
                .retrieve()
                .body(String.class);

    }

    public String getAllDepWithMetrics() {
        return client.get()
                .uri("http://localhost:8080/api/metrics/dep")
                .retrieve()
                .body(String.class);
    }

    public String getAllPisosComMetricas() {
        return client.get()
                .uri("http://localhost:8080/api/metrics/piso")
                .retrieve()
                .body(String.class);
    }

    public String getAllEdificiosComMetricas() {
        return client.get()
                .uri("http://localhost:8080/api/metrics/edificio")
                .retrieve()
                .body(String.class);
    }

    public static void main(String[] args) throws MqttException, InterruptedException {
        RestCli client = new RestCli();
        Scanner sc = new Scanner(System.in);
        boolean ciclo = true;
        while (ciclo) {
            menu();
            int opcao = Integer.parseInt(sc.nextLine().split(" ")[0]);
            switch (opcao) {
                case 1:

                    boolean cicloGestaoDispositivos = true;
                    while (cicloGestaoDispositivos) {
                        gestaoDeDispositivos();

                        int opcaoGestaoDispositivos = Integer.parseInt(sc.nextLine().split(" ")[0]);

                        switch (opcaoGestaoDispositivos) {

                            case 2:
                                client.associarDispositivo(sc);
                                break;
                            case 1:
                                client.allActiveDisp();
                                break;
                            case 5:
                                client.vizualizarDetalhes(sc);
                                break;
                            case 3:
                                client.atualizar(sc);
                                break;
                            case 4:
                                client.delete(sc);
                                break;
                            case 1234:
                                cicloGestaoDispositivos = false;
                                break;
                            default:
                        }

                    }
                    break;
                case 1234:
                    ciclo = false;
                    break;
                case 2:
                    boolean cicloConsultaMetricas = true;

                    while (cicloConsultaMetricas) {
                        menuconsultaMetricas();

                        int opcaoConsultaMetricas = Integer.parseInt(sc.nextLine().split(" ")[0]);

                        switch (opcaoConsultaMetricas) {
                            case 1:
                                System.out.println("Aqui estão as salas com métricas");
                                Sala[] salas = client.salaComMetricas();
                                for (Sala s : salas) {
                                    System.out.println(s);
                                }

                                System.out.println("Indique o id que pretende consultar as metricas");
                                int idSala = Integer.parseInt(sc.nextLine().split(" ")[0]);
                                boolean has = false;
                                for (Sala s : salas) {
                                    if (s.getId() == idSala) {
                                        has = true;
                                        break;
                                    }
                                }
                                if (!has) {
                                    System.out.println("Esse sala id nao pertence a lista");
                                    break;
                                }
                                System.out.println("Pretende filtrar por datas? (s/n)");
                                String option = sc.nextLine().trim().toLowerCase();
                                String from = null;
                                String to = null;
                                if (option.equals("s")) {
                                    System.out.println("Indique a data inicial (formato: yyyy-MM-ddTHH:mm:ss)");
                                    from = sc.nextLine().trim();
                                    System.out.println("Indique a data final (formato: yyyy-MM-ddTHH:mm:ss)");
                                    to = sc.nextLine().trim();
                                }
                                System.out.println(client.getAverage("sala", String.valueOf(idSala), from, to));
                                break;
                            case 2:
                                System.out.println("Departamentos com métricas :");
                                System.out.println(client.getAllDepWithMetrics());
                                System.out.println("Indique o departamento que pretende consultar as metricas");
                                String dep = (sc.nextLine().split(" ")[0]);
                                salas = client.salaComMetricas();
                                boolean has2 = false;
                                for (Sala s : salas) {
                                    if (s.getDepartamento().equals(dep)) {
                                        has2 = true;
                                        break;
                                    }
                                }
                                if (!has2) {
                                    System.out.println("Departamento nao tem  sala com metricas");
                                    break;
                                }
                                System.out.println("Pretende filtrar por datas? (s/n)");
                                String option2 = sc.nextLine().trim().toLowerCase();
                                String from2 = null;
                                String to2 = null;
                                if (option2.equals("s")) {
                                    System.out.println("Indique a data inicial (formato: yyyy-MM-ddTHH:mm:ss)");
                                    from2 = sc.nextLine().trim();
                                    System.out.println("Indique a data final (formato: yyyy-MM-ddTHH:mm:ss)");
                                    to2 = sc.nextLine().trim();
                                }
                                System.out.println(client.getAverage("departamento", dep, from2, to2));
                                break;
                            case 3:
                                System.out.println("Pisos com métricas :");
                                System.out.println(client.getAllPisosComMetricas());
                                System.out.println("Indique o piso que pretende consultar as metricas");
                                int piso = Integer.parseInt(sc.nextLine().split(" ")[0]);
                                salas = client.salaComMetricas();
                                boolean has3 = false;
                                for (Sala s : salas) {
                                    if (s.getAndar() == (piso)) {
                                        has3 = true;
                                        break;
                                    }
                                }
                                if (!has3) {
                                    System.out.println("Piso nao tem  sala com metricas");
                                    break;
                                }
                                System.out.println("Pretende filtrar por datas? (s/n)");
                                String option3 = sc.nextLine().trim().toLowerCase();
                                String from3 = null;
                                String to3 = null;
                                if (option3.equals("s")) {
                                    System.out.println("Indique a data inicial (formato: yyyy-MM-ddTHH:mm:ss)");
                                    from3 = sc.nextLine().trim();
                                    System.out.println("Indique a data final (formato: yyyy-MM-ddTHH:mm:ss)");
                                    to3 = sc.nextLine().trim();
                                }
                                System.out.println(client.getAverage("piso", String.valueOf(piso), from3, to3));
                                break;
                            case 4:
                                System.out.println("Edificios com métricas:");
                                System.out.println(client.getAllEdificiosComMetricas());
                                System.out.println("Indique o edificio que pretende consultar as metricas");
                                String edificio = sc.nextLine().split(" ")[0];
                                salas = client.salaComMetricas();
                                boolean has4 = false;
                                for (Sala s : salas) {
                                    if (s.getEdificio().equals(edificio)) {
                                        has4 = true;
                                        break;
                                    }
                                }
                                if (!has4) {
                                    System.out.println("Edificio nao tem  sala com metricas");
                                    break;
                                }
                                System.out.println("Pretende filtrar por datas? (s/n)");
                                String option4 = sc.nextLine().trim().toLowerCase();
                                String from4 = null;
                                String to4 = null;
                                if (option4.equals("s")) {
                                    System.out.println("Indique a data inicial (formato: yyyy-MM-ddTHH:mm:ss)");
                                    from4 = sc.nextLine().trim();
                                    System.out.println("Indique a data final (formato: yyyy-MM-ddTHH:mm:ss)");
                                    to4 = sc.nextLine().trim();
                                }
                                System.out.println(client.getAverage("edificio", edificio, from4, to4));
                                break;

                            case 5:
                                Métrica[] lista = client.metricaBruta(sc);
                                for (Métrica a : lista) {
                                    System.out.println(a);
                                }
                                break;
                            default:
                            case 1234:
                                cicloConsultaMetricas = false;
                                break;
                        }

                    }

                    break;
                case 3:
                    Dispositivo d[] = client.dispositivosAtivos();
                    System.out.println("Estes sao os dispositivos ativos");

                    for(Dispositivo a : d){
                        System.out.println(a);
                    }
                    break;
                default:
                    System.out.println("default");
                    break;
            }

        }

    }
}
