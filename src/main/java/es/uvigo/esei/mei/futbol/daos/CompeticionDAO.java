/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.mei.futbol.daos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import es.uvigo.esei.mei.futbol.entidades.Competicion;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ivan
 */
public interface CompeticionDAO extends JpaRepository<Competicion, Long> {

    public List<Competicion> findAll();

    public Competicion getById(Long id);

    @Query(value = "SELECT * FROM Competicion  WHERE nombre LIKE CONCAT('%',:nombre,'%') "
            + "AND tipo LIKE  CONCAT('%',:tipo,'%')",
            nativeQuery = true)
    public List<Competicion> findByNombreTipo(@Param("nombre") String nombre,
            @Param("tipo") String tipo);

}
