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
import java.util.Date;
/**
 *
 * @author ivan
 */

public interface EquipoDAO extends JpaRepository<Equipo, Long> {
   /* public List<Equipo> findEquipoByNombre(String nombre);
    @Query("SELECT e FROM Equipo e WHERE e.nombre LIKE %?1") 
    public List<Equipo> findAll();
    //public List<Equipo> findbyFundado(Date fundado);
    public List<Equipo> findByNombreContaining(String patron);
    public List<Equipo> findByCiudad(String ciudad);
    public List<Equipo> findByPais(String pais);
    public List<Equipo> findByEstadio(String estadio);*/
    
   public Equipo getById(String id);
   public List<Equipo> findByPais(String pais);

}