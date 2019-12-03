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
/**
 *
 * @author ivan
 */

public interface CompeticionDAO extends JpaRepository<Competicion, Long> {
        public List<Competicion> findAll();
}