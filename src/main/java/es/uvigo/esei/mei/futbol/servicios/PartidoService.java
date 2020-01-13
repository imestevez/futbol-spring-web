package es.uvigo.esei.mei.futbol.servicios;

import es.uvigo.esei.mei.futbol.entidades.Competicion;
import es.uvigo.esei.mei.futbol.entidades.Equipo;
import es.uvigo.esei.mei.futbol.entidades.Estadio;
import java.util.List;

import es.uvigo.esei.mei.futbol.entidades.Partido;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public interface PartidoService {

    public Partido crear(Partido equipo);

    public Partido modificar(Partido equipo);

    public void eliminar(Partido equipo);

    public Partido buscarPorID(Long id);

    public List<Partido> buscarTodos();

    public List<Partido> buscarPorLocal(Equipo equipo);

    public List<Partido> buscarPorVisitante(Equipo equipo);

    public List<Partido> buscarPorCompeticion(Competicion competicion);

    public List<Partido> buscarPorEstadio(Estadio estadio);
}
