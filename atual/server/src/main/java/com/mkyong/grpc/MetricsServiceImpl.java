package com.mkyong.grpc;

import com.example.grpc.*;
import com.mkyong.bd.entidades.Métricas;
import com.mkyong.bd.repositorios.DispositivoRepository;
import com.mkyong.bd.repositorios.MétricasRepository;
import com.mkyong.bd.repositorios.SalaRepository;
import io.grpc.stub.StreamObserver;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class MetricsServiceImpl extends MetricsServiceGrpc.MetricsServiceImplBase {
    public static final List<Long> tempos = java.util.Collections.synchronizedList(new java.util.ArrayList<>());

    private final MétricasRepository metricRepo;
    private final SalaRepository salaRepo;
    private final DispositivoRepository dispRepo;
    public static  Long startTime = null;

    public MetricsServiceImpl(MétricasRepository metricRepo, SalaRepository salaRepo, DispositivoRepository dispRepo) {
        this.metricRepo = metricRepo;
        this.salaRepo = salaRepo;
        this.dispRepo = dispRepo;
    }
    @Override
    public void sendMetric(Metric request, StreamObserver<MetricResponse> responseObserver) {
//        System.out.println("Temperatura: " + request.getTemperatura());
//        System.out.println("Humidade: " + request.getHumidade());
//        System.out.println("IdSala: " + request.getIdSala());
//        System.out.println("IdDispositivo: " + request.getIdDispositivo());
        int idSala = request.getIdSala();
        double humidade = request.getHumidade();
        double temperatura = request.getTemperatura();
        int idDispositivo = request.getIdDispositivo();

        long enviado = request.getTimestamp();
        long now = Instant.now().toEpochMilli();
        if(startTime == null){
            startTime = now;
        }
        long diff = now - enviado;
//        System.out.println("now: " + now + " enviado : " + enviado + " diff : " + diff);

        tempos.add(diff);

        if(dispRepo.existsByTipoAndSalaIdAndEstado("GRPC",idSala,"Ativo")){
            metricRepo.save(new Métricas(humidade, temperatura, dispRepo.findById(idDispositivo),salaRepo.findSalasById(idSala)));
            MetricResponse response = MetricResponse
                    .newBuilder()
                    .setMessage("Métrica recebida com sucesso e inserida!")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }else{
            MetricResponse response = MetricResponse
                    .newBuilder()
                    .setMessage("Métrica recebida com sucesso porém não inserida!")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
