package com.mkyong.bd.repositorios;

import com.mkyong.bd.entidades.Métricas;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MétricasRepository extends CrudRepository<Métricas, Integer> {

    List<Métricas> findByDispositivoIdAndTimestampBetween(int dispositivoId, LocalDateTime from, LocalDateTime to);

    List<Métricas> findByDispositivoId(int dispositivoId);

    boolean existsMétricasBySala_Id(int id);
    List<Métricas> findBySala_DepartamentoAndTimestampBetween(String salaDepartamento, LocalDateTime from, LocalDateTime to );
    List<Métricas> findBySala_EdificioAndTimestampBetween( String edificio, LocalDateTime from, LocalDateTime to );
    List<Métricas> findBySala_AndarAndTimestampBetween( int andar, LocalDateTime from, LocalDateTime to );
    List<Métricas> findBySala_IdAndTimestampBetween( int salaId, LocalDateTime from, LocalDateTime to );
    @Query("SELECT DISTINCT m.sala.departamento FROM Métricas m")
    List<String> findDistinctDepartamentos();

    @Query("SELECT DISTINCT m.sala.andar FROM Métricas m")
    List<Integer> findDistinctAndar();

    @Query("SELECT DISTINCT m.sala.edificio FROM Métricas m")
    List<String> findDistinctEdificio();

    @Query("SELECT DISTINCT m.dispositivo.id FROM Métricas m")
    List<Integer> findDistinctDispIds();

}

