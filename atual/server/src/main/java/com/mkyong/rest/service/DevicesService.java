package com.mkyong.rest.service;

import com.mkyong.bd.entidades.Dispositivo;
import com.mkyong.bd.entidades.Sala;
import com.mkyong.bd.repositorios.DispositivoRepository;
import com.mkyong.bd.repositorios.MétricasRepository;
import com.mkyong.bd.repositorios.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevicesService {

    private final MétricasRepository metricRepo;
    private final SalaRepository salaRepo;
    private final DispositivoRepository dispRepo;


    public DevicesService(MétricasRepository metricRepo, SalaRepository salaRepo, DispositivoRepository dispRepo) {
        this.metricRepo = metricRepo;
        this.salaRepo = salaRepo;
        this.dispRepo = dispRepo;
    }

    public void activate(int id) {
        System.out.println("Este e o id do dispositivo mqtt" + id);
        Dispositivo d = dispRepo.findById(id);
        d.setEstado("Ativo");
        dispRepo.save(d);
    }
    public List<Dispositivo> getAllDisp(){
        return dispRepo.findAllByEstado("Ativo");
    }
    public Dispositivo getById(int id){
        return dispRepo.findById(id);
    }
    public boolean update(int id, Dispositivo novo){
        Dispositivo d = dispRepo.findById(id);
        d.setNome(novo.getNome());
        d.setEstado(novo.getEstado());
        d.setSala(novo.getSala());
        d.setTipo(novo.getTipo());
        dispRepo.save(d);
        return  true;
    }

    public boolean delete(int id){
        Dispositivo d = dispRepo.findById(id);
        d.setEstado("Inativo");
        dispRepo.save(d);
        return true;
    }
    public List<Integer> comMetricas(){
        return metricRepo.findDistinctDispIds();
    }

    public List <Dispositivo> ativos(){
        return dispRepo.findAllByEstado("Ativo");
    }
}
