package es.uvigo.esei.mei.futbol.servicios;

import java.util.List;

import es.uvigo.esei.mei.futbol.entidades.Equipo;
import es.uvigo.esei.mei.futbol.entidades.Estadio;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public interface EquipoService {

    public Equipo crear(Equipo equipo);

    public Equipo modificar(Equipo equipo);

    public void eliminar(Equipo equipo);

    public Equipo buscarPorID(Long id);

    public List<Equipo> buscarPorNombre(String nombre);

    public List<Equipo> buscarPorCiudad(String ciudad);

    public List<Equipo> buscarTodos();

    public List<Equipo> buscarNombreCiudad(String nombre, String ciudad);

    public List<Equipo> buscarPorEstadio(Estadio estadio);

}
