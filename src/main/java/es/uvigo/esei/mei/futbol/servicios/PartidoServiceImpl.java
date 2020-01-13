package es.uvigo.esei.mei.futbol.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.esei.mei.futbol.daos.PartidoDAO;
import es.uvigo.esei.mei.futbol.entidades.Partido;
import es.uvigo.esei.mei.futbol.servicios.PartidoService;
import java.util.Date;

@Service
public class PartidoServiceImpl implements PartidoService {

    @Autowired
    PartidoDAO dao;

    @Override
    @Transactional
    public Partido crear(Partido partido) {
        return dao.save(partido);
    }

    @Override
    @Transactional
    public Partido modificar(Partido partido) {
        return dao.save(partido);
    }

    @Override
    @Transactional
    public void eliminar(Partido partido) {
        dao.delete(partido);
    }

    @Override
    @Transactional(readOnly = true)
    public Partido buscarPorID(Long id) {
        return dao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Partido> buscarTodos() {
        return dao.findAll();
    }

}
