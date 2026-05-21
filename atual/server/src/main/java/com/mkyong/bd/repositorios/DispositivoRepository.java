package com.mkyong.bd.repositorios;

import com.mkyong.bd.entidades.Dispositivo;
import com.mkyong.bd.entidades.Sala;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DispositivoRepository extends CrudRepository<Dispositivo, Integer> {
    Dispositivo findDispositivoBySala(Sala s);
    List<Dispositivo> findAll();
    int countDispositivoByTipoEquals(String a);
    boolean existsByTipoAndSalaIdAndEstado(String tipo, int salaId, String estado);
    Dispositivo findByTipoAndSalaIdAndEstado(String tipo, int salaId, String estado);
    Dispositivo findById(int id);
    List<Dispositivo> findAllByEstado(String estado);
    Dispositivo findByTipoAndSala(String tipo, Sala sala);


}
