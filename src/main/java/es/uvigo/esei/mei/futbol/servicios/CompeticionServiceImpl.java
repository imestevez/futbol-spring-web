package es.uvigo.esei.mei.futbol.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.esei.mei.futbol.daos.CompeticionDAO;
import es.uvigo.esei.mei.futbol.entidades.Competicion;
import es.uvigo.esei.mei.futbol.servicios.CompeticionService;
import java.util.Date;

@Service
public class CompeticionServiceImpl implements CompeticionService {

    @Autowired
    CompeticionDAO dao;

    @Override
    @Transactional
    public Competicion crear(Competicion competicion) {
        return dao.save(competicion);
    }

    @Override
    @Transactional
    public Competicion modificar(Competicion competicion) {
        return dao.save(competicion);
    }

    @Override
    @Transactional
    public void eliminar(Competicion competicion) {
        dao.delete(competicion);
    }

    @Override
    @Transactional(readOnly = true)
    public Competicion buscarPorID(Long id) {
        return dao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Competicion> buscarTodos() {
        return dao.findAll();
    }

    @Override
    public List<Competicion> buscarNombreTipo(String nombre, String tipo) {
        return dao.findByNombreTipo(nombre,tipo);
    }

}
