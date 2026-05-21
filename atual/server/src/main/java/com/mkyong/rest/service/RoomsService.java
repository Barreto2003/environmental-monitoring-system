package com.mkyong.rest.service;

import com.mkyong.bd.entidades.Dispositivo;
import com.mkyong.bd.entidades.Sala;
import com.mkyong.bd.repositorios.DispositivoRepository;
import com.mkyong.bd.repositorios.MétricasRepository;
import com.mkyong.bd.repositorios.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomsService {

    private final MétricasRepository metricRepo;
    private final SalaRepository salaRepo;
    private final DispositivoRepository dispRepo;

    public RoomsService(MétricasRepository metricRepo, SalaRepository salaRepo, DispositivoRepository dispRepo) {
        this.metricRepo = metricRepo;
        this.salaRepo = salaRepo;
        this.dispRepo = dispRepo;
    }

    public List<Sala> salasLivres() {

        List<Sala> todas = salaRepo.findAll();
        List<Dispositivo> todosDisp = dispRepo.findAll();
        List<Sala> livres = new ArrayList<>();

//        System.out.println("todas as salas" + todas);

        for (Sala s : todas) {
            boolean temD = false;

            for (Dispositivo d : todosDisp) {

                if (d.getSala().getId() == s.getId() && d.getEstado().equals("Ativo")) {
                    temD = true;
                    break;
                }
            }
            if (!temD) {
                livres.add(s);
            } else {
                temD = false;
            }
        }
//        System.out.println("apenas as livres " + livres);
        return livres;
    }
//    public boolean addSubscriber(Topico topico){
//
//        System.out.println("isto e o topico ");
//        System.out.println(topico.getSalaId());
//        System.out.println(topico.getTipo());
//        System.out.println(topico.getTipo());

    //        Sala s = salaRepo.findById(topico.getSalaId()).orElseThrow(() -> new RuntimeException("Sala não encontrada"));
//        MqttSubscriber subscriber = new MqttSubscriber(String.valueOf(s.getNumero()), topico.getTopico(), "tcp://localhost:1883",dispRepo);
//        dispRepo.save(new Dispositivo(topico.getTipo(), s, "Ativo"));
//        return true;
//    }
    public List<Sala> salasQueTemMetricas() {


        List<Sala> todas = salaRepo.findAll();
        List<Dispositivo> todosDisp = dispRepo.findAll();
        List<Sala> salaComMetricas = new ArrayList<>();
        for (Sala s : todas) {
            if (metricRepo.existsMétricasBySala_Id(s.getId())) {
                salaComMetricas.add(s);
            }
        }
        return salaComMetricas;
    }

}
