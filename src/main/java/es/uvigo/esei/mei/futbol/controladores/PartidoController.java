package es.uvigo.esei.mei.futbol.controladores;

import es.uvigo.esei.mei.futbol.entidades.Competicion;
import es.uvigo.esei.mei.futbol.entidades.Equipo;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.uvigo.esei.mei.futbol.entidades.Partido;
import es.uvigo.esei.mei.futbol.entidades.Estadio;
import es.uvigo.esei.mei.futbol.servicios.CompeticionService;
import es.uvigo.esei.mei.futbol.servicios.EquipoService;
import es.uvigo.esei.mei.futbol.servicios.PartidoService;
import es.uvigo.esei.mei.futbol.servicios.EstadioService;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Controller
@RequestMapping("/partidos")
public class PartidoController {

    @Autowired
    PartidoService partidoService;
    @Autowired
    EstadioService estadioService;
    @Autowired
    EquipoService equipoService;
    @Autowired
    CompeticionService competicionService;

    /**
     * Model encapsula el modelo (en este caso sera un Model vacio para ser
     * inicializado)
     */
    @GetMapping
    public String prepararListarPartidos(Model modelo) {
        List<Partido> partidos = partidoService.buscarTodos();
        modelo.addAttribute("partidos", partidos);
        return "partidos/listado_partidos";
    }

    /**
     * @param id
     * @param modelo
     * @return View
     * @PathVariable vincula el parametro a un segmento de la URI
     */
    @GetMapping("{id}/eliminar")
    public String borrarPartido(@PathVariable("id") Long id, Model modelo) {
        Partido partido = partidoService.buscarPorID(id);
        if (partido != null) {
            partidoService.eliminar(partido);
            Estadio estadio = estadioService.buscarPorID(partido.getEstadio().getId());
            modelo.addAttribute(partido);
            modelo.addAttribute("nombreEstadio", estadio.getNombre());
            modelo.addAttribute("return", "/partidos");
            modelo.addAttribute("message", "Eliminado correctamente");
            return "partidos/detalle_partido";
        } else {
            modelo.addAttribute("path", id + "/eliminar");
            modelo.addAttribute("message", "No se ha podido eliminar el partido");
            modelo.addAttribute("return", "/partidos");
            return "error_message";
        }
    }

    @GetMapping("{id}")
    public String verPartido(@PathVariable("id") Long id, Model modelo) {
        Partido partido = partidoService.buscarPorID(id);
        if (partido != null) {
            Estadio estadio = estadioService.buscarPorID(partido.getEstadio().getId());
            modelo.addAttribute(partido);
            modelo.addAttribute("nombreEstadio", estadio.getNombre());
            modelo.addAttribute("return", "/partidos");
            modelo.addAttribute("message", "Vista en Detalle");
            return "partidos/detalle_partido";
        } else {
            modelo.addAttribute("path", id + "/eliminar");
            modelo.addAttribute("message", "No se ha podido encontrar el partido");
            modelo.addAttribute("return", "/partidos");
            return "error_message";
        }
    }

    /**
     * ModelAndView encapsula (equivalente a modificar el Model recibido como
     * parametro y retornar un String con la siguiente vista)
     *
     * @return
     */
    @GetMapping("nuevo")
    public ModelAndView prepararNuevoPartido() {
        Partido partido = new Partido();
        List<Estadio> estadios = estadioService.buscarTodos();
        List<Equipo> equipos = equipoService.buscarTodos();
        List<Competicion> competiciones = competicionService.buscarTodos();
        ModelAndView result = new ModelAndView();
        result.addObject("partido", partido);
        result.addObject("estadios", estadios);
        result.addObject("equipos", equipos);
        result.addObject("competiciones", competiciones);
        result.addObject("esNuevo", true);
        result.setViewName("partidos/editar_partido");
        return result;
    }

    /**
     * @param partido
     * @param modelo
     * @return
     * @Valid indica que se apliquen las validaciones BeanValidation declaradas
     * en el correspondiente tipo
     * @ModelAttribute vincula con un atributo del Model con el mismo nombre de
     * la variable (es opcional, el comportamiento por defecto busca en el Model
     * atributos con los nombres de las variables) BindingRequest encapsula el
     * resultado del binding de parametros de la peticion o Model con atributos
     * de los objetos reales
     */
    @PostMapping("nuevo")
    public String crearPartido(@Valid @ModelAttribute("partido") Partido partido,
            BindingResult resultado, Model modelo) {
        if (!resultado.hasErrors()) {
            if (partido.getLocal().equals(partido.getVisitante())) {
                modelo.addAttribute("path", "/partidos/nuevo");
                modelo.addAttribute("message", "No se ha podido insertar el partido.");
                modelo.addAttribute("binding_result", "Los equipos que se enfrentan en un partido tienen que ser distintos");
                modelo.addAttribute("return", "/partidos");
                return "error_message";
            } else {
                partidoService.crear(partido);
                return "redirect:/partidos";
            }

        } else {
            modelo.addAttribute("path", "/partidos/nuevo");
            modelo.addAttribute("message", "No se ha podido insertar el partido.");
            modelo.addAttribute("binding_result", resultado.toString());
            modelo.addAttribute("return", "/partidos");
            return "error_message";
        }

    }

    @GetMapping("{id}/editar")
    public String prepararEditarPartido(@PathVariable("id") Long id, Model modelo) {
        try {
            Partido partido = partidoService.buscarPorID(id);
            List<Estadio> estadios = estadioService.buscarTodos();
            List<Equipo> equipos = equipoService.buscarTodos();
            List<Competicion> competiciones = competicionService.buscarTodos();
            ModelAndView result = new ModelAndView();
            result.addObject("partido", partido);
            result.addObject("estadios", estadios);
            result.addObject("equipos", equipos);
            result.addObject("competiciones", competiciones);
            modelo.addAttribute("esNuevo", false);
            return "partidos/editar_partido";
        } catch (EntityNotFoundException e) {
            modelo.addAttribute("error", "Partido no encontrado");
            return "error";
        }
    }

    @PostMapping("{id}/editar")
    public String actualizarPartido(@Valid @ModelAttribute Partido partido, BindingResult resultado) {
        if (!resultado.hasErrors()) {
            partidoService.modificar(partido);
            return "redirect:/partidos";
        } else {
            return null;
        }
    }

}
