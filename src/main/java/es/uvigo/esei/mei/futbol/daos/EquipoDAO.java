/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.mei.futbol.daos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import es.uvigo.esei.mei.futbol.entidades.Equipo;
import es.uvigo.esei.mei.futbol.entidades.Estadio;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ivan
 */
public interface EquipoDAO extends JpaRepository<Equipo, Long> {

    public List<Equipo> findAll();

    public Equipo getById(Long id);

    public List<Equipo> findByNombreContaining(String nombre);

    public List<Equipo> findByCiudadContaining(String ciudad);

    public List<Equipo> getByEstadio(Estadio estadio);

    @Query(value = "SELECT * FROM Equipo  WHERE nombre LIKE CONCAT('%',:nombre,'%') "
            + "AND ciudad LIKE  CONCAT('%',:ciudad,'%')",
            nativeQuery = true)
    public List<Equipo> findByNombreCiudad(@Param("nombre") String nombre,
            @Param("ciudad") String ciudad);

}
