package com.mkyong.mqtt;

import com.mkyong.bd.entidades.Métricas;
import com.mkyong.bd.repositorios.DispositivoRepository;
import com.mkyong.bd.repositorios.MétricasRepository;
import com.mkyong.bd.repositorios.SalaRepository;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
//import com.mkyong.bd.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MqttSubscriber implements MqttCallback {

    public static List<Long> tempos = Collections.synchronizedList(new ArrayList<>());
    private final String clientId;
    private final String topic;
    private final String broker;
    private final DispositivoRepository dispositivoRepository;
    private final MétricasRepository metricasRepository;
    private final SalaRepository salaRepository;
    public static Long startTime = null;

//    static synchronized Long getMedia(List<Long> Lista){
//        long acc = 0;
//        for (Long a : Lista){
//            acc += a;
//        }
//        return acc/Lista.size();
//    }
    private DispositivoRepository DispositivoRepository;
//    private static final AtomicInteger totalMessagesReceived = new AtomicInteger(0);

    public MqttSubscriber(String clientId, String topic, String broker, DispositivoRepository dispositivoRepository, MétricasRepository metricasRepository, SalaRepository salaRepository ) {
        this.clientId = clientId;
        this.topic = topic;
        this.broker = broker;
        this.dispositivoRepository = dispositivoRepository;
        this.metricasRepository = metricasRepository;
        this.salaRepository = salaRepository;
    }

//    public static int getTotalMessagesReceived() {
//        return totalMessagesReceived.get();
//    }

    public void start() {
         Thread mqttThread = new Thread(() -> {
            try {
                runSubscriber();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mqttThread.start();
    }


    private void runSubscriber() throws Exception {

        MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);

        client.setCallback(this);

//        System.out.println("Connecting to broker: " + broker);
        client.connect(connOpts);
//        System.out.println("Connected");

        client.subscribe(topic);
//        System.out.println("Subscribed to topic: " + topic);


        // Mantém a thread viva
        while (true) {
            Thread.sleep(1000);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {

        String payload = new String(message.getPayload());
        String[] topicoSeparado = topic.split("/");
        String[] content = payload.split(";");

        try {
            double temperatura = Double.parseDouble(content[1]);
            double humidade = Double.parseDouble(content[3]);
            long timestamp = Long.parseLong(content[5]);

//            System.out.println("Temperatura: " + temperatura);
//            System.out.println("Humidade: " + humidade);
//            System.out.println("Timestamp: " + timestamp);

            long enviado = timestamp;
            long recebido = Instant.now().toEpochMilli();
            if(startTime == null){
                startTime = recebido;
            }
            Long diff = recebido - enviado;
            System.out.println("Recebido : " + recebido + " Enviado: " + enviado + " Diff: " + diff);
            tempos.add(diff);

//            if(tempos.size() % 100 == 0){
//                System.out.println("---------------------------------------------------------------------------------");
//                System.out.println("Média dos tempos (MQTT): " + getMedia(tempos));
//                System.out.println("---------------------------------------------------------------------------------");
//            }

            if (dispositivoRepository.existsByTipoAndSalaIdAndEstado("MQTT",Integer.parseInt(topicoSeparado[1]),"Ativo")){
                metricasRepository.save(new Métricas(humidade,temperatura,dispositivoRepository.findByTipoAndSalaIdAndEstado("MQTT",Integer.parseInt(topicoSeparado[1]),"Ativo"), salaRepository.findByNumero(Integer.parseInt(topicoSeparado[1]))));
            }
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter números na mensagem: " + payload);
        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar mensagem: " + payload);
            e.printStackTrace();
        }
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}

