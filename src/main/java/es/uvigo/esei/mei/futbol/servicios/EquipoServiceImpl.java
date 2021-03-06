package es.uvigo.esei.mei.futbol.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.esei.mei.futbol.daos.EquipoDAO;
import es.uvigo.esei.mei.futbol.entidades.Equipo;
import es.uvigo.esei.mei.futbol.entidades.Estadio;
import es.uvigo.esei.mei.futbol.servicios.EquipoService;
import java.util.Date;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    EquipoDAO dao;

    @Override
    @Transactional
    public Equipo crear(Equipo estadio) {
        return dao.save(estadio);
    }

    @Override
    @Transactional
    public Equipo modificar(Equipo estadio) {
        return dao.save(estadio);
    }

    @Override
    @Transactional
    public void eliminar(Equipo estadio) {
        dao.delete(estadio);
    }

    @Override
    @Transactional(readOnly = true)
    public Equipo buscarPorID(Long id) {
        return dao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipo> buscarPorCiudad(String ciudad) {
        return dao.findByCiudadContaining(ciudad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipo> buscarPorNombre(String nombre) {
        return dao.findByNombreContaining(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipo> buscarTodos() {
        return dao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipo> buscarNombreCiudad(String nombre, String ciudad) {
        return dao.findByNombreCiudad(nombre, ciudad);

    }

    @Override
    public List<Equipo> buscarPorEstadio(Estadio estadio) {
        return dao.getByEstadio(estadio);
    }
}
