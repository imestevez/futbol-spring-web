/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.mei.futbol.daos;

import es.uvigo.esei.mei.futbol.entidades.Equipo;
import es.uvigo.esei.mei.futbol.entidades.Estadio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ivan
 */
public interface EstadioDAO extends JpaRepository<Estadio, Long> {

    public List<Estadio> findAll();

    public Estadio getById(Long id);

    @Query(value = "SELECT * FROM Estadio WHERE id != :id", nativeQuery = true)
    public List<Estadio> findByDisctictId(@Param("id") Long id);

    @Query(value = "SELECT * FROM Estadio  WHERE nombre LIKE CONCAT('%',:nombre,'%') "
            + "AND ciudad LIKE  CONCAT('%',:ciudad,'%')",
            nativeQuery = true)
    public List<Estadio> findByNombreCiudad(@Param("nombre") String nombre,
            @Param("ciudad") String ciudad);

}
