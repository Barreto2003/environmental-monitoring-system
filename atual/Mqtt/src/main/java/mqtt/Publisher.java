package mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import mqtt.classes.Métrica;

import java.lang.reflect.Array;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Publisher {
    public void run(String topic, String clientId) throws MqttException, InterruptedException {
        String broker = "tcp://localhost:1883";
        int qos = 0;

        MqttClient sampleClient = new MqttClient(broker, clientId + "-pub", new MemoryPersistence());
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);

        double temperatura = (double) (Math.random() * 15.00) + 15.00;
        double humidade = (double) (Math.random() * 50.00) + 30.00;

        sampleClient.connect(connOpts);


        while (true) {

            long iso = Instant.now().toEpochMilli();
            Métrica m = new Métrica(temperatura, humidade, iso);

            MqttMessage message = new MqttMessage(m.getBytes());
            message.setQos(qos);

            sampleClient.publish(topic, message);


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

    public static void main(String[] args) throws MqttException, InterruptedException {
        String clientId = "1";
        String broker = "tcp://localhost:1883";
        String topic = "Colégio dos Leões/96";
        String content = "toto";
        int qos = 2;

        List<String[]> listaPublishers = new ArrayList<>();
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/1", "1"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/2", "2"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/3", "3"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/4", "4"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/5", "5"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/6", "6"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/7", "7"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/8", "8"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/9", "9"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/10", "10"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/11", "11"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/12", "12"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/13", "13"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/14", "14"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/15", "15"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/16", "16"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/17", "17"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/18", "18"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/19", "19"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/20", "20"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/21", "21"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/22", "22"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/23", "23"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/24", "24"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/25", "25"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/26", "26"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/27", "27"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/28", "28"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/29", "29"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/30", "30"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/31", "31"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/32", "32"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/33", "33"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/34", "34"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/35", "35"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/36", "36"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/37", "37"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/38", "38"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/39", "39"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/40", "40"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/41", "41"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/42", "42"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/43", "43"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/44", "44"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/45", "45"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/46", "46"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/47", "47"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/48", "48"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/49", "49"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/50", "50"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/51", "51"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/52", "52"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/53", "53"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/54", "54"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/55", "55"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/56", "56"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/57", "57"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/58", "58"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/59", "59"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/60", "60"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/61", "61"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/62", "62"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/63", "63"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/64", "64"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/65", "65"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/66", "66"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/67", "67"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/68", "68"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/69", "69"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/70", "70"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/71", "71"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/72", "72"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/73", "73"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/74", "74"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/75", "75"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/76", "76"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/77", "77"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/78", "78"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/79", "79"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/80", "80"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/81", "81"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/82", "82"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/83", "83"});
        listaPublishers.add(new String[]{"Colegio_Pedro_da_Fonseca/84", "84"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/85", "85"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/86", "86"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/87", "87"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/88", "88"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/89", "89"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/90", "90"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/91", "91"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/92", "92"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/93", "93"});
        listaPublishers.add(new String[]{"Colegio_do_Espirito_Santo/94", "94"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/95", "95"});
        listaPublishers.add(new String[]{"Polo_da_Mitra/96", "96"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/97", "97"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/98", "98"});
        listaPublishers.add(new String[]{"Colegio_Luis_Antonio_Verney/99", "99"});
        listaPublishers.add(new String[]{"Colegio_dos_Leoes/100", "100"});

        for (int i = 0; i < 100; i++) {
            String topico = listaPublishers.get(i)[0];
            String clienteId = listaPublishers.get(i)[1];
            Thread t = new Thread(() -> {
                try {
                    new Publisher().run(topico, clienteId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }

    }
}
