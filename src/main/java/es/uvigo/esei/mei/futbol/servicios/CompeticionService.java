package es.uvigo.esei.mei.futbol.servicios;

import java.util.List;

import es.uvigo.esei.mei.futbol.entidades.Competicion;
import org.springframework.stereotype.Service;

@Service
public interface CompeticionService {

    public Competicion crear(Competicion competicion);

    public Competicion modificar(Competicion competicion);

    public void eliminar(Competicion competicion);

    public Competicion buscarPorID(Long id);

    public List<Competicion> buscarTodos();
    
    public List<Competicion> buscarNombreTipo(String nombre, String tipo);


}
