package es.uvigo.esei.mei.futbol.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.esei.mei.futbol.daos.EstadioDAO;
import es.uvigo.esei.mei.futbol.entidades.Estadio;
import es.uvigo.esei.mei.futbol.servicios.EstadioService;
import java.util.Date;

@Service
public class EstadioServiceImpl implements EstadioService {

    @Autowired
    EstadioDAO dao;

    @Override
    @Transactional
    public Estadio crear(Estadio cliente) {
        return dao.save(cliente);
    }

    @Override
    @Transactional
    public Estadio modificar(Estadio cliente) {
        return dao.save(cliente);
    }

    @Override
    @Transactional
    public void eliminar(Estadio cliente) {
        dao.delete(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Estadio buscarPorID(Long id) {
        return dao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estadio> buscarTodos() {
        return dao.findAll();
    }

    @Override
    public List<Estadio> buscarDistintoId(Long id) {
        return dao.findByDisctictId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estadio> buscarNombreCiudad(String nombre, String ciudad) {
        return dao.findByNombreCiudad(nombre, ciudad);

    }
}
