package es.uvigo.esei.mei.futbol.servicios;

import java.util.List;

import es.uvigo.esei.mei.futbol.entidades.Estadio;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public interface EstadioService {

    public Estadio crear(Estadio equipo);

    public Estadio modificar(Estadio equipo);

    public void eliminar(Estadio equipo);

    public Estadio buscarPorID(Long id);

    public List<Estadio> buscarTodos();

    public List<Estadio> buscarDistintoId(Long id);

    public List<Estadio> buscarNombre(String nombre);

}
