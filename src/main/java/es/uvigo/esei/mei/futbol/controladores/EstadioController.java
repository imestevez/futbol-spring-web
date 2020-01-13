package es.uvigo.esei.mei.futbol.controladores;

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

import es.uvigo.esei.mei.futbol.entidades.Estadio;
import es.uvigo.esei.mei.futbol.entidades.Estadio;
import es.uvigo.esei.mei.futbol.entidades.Partido;
import es.uvigo.esei.mei.futbol.servicios.EquipoService;
import es.uvigo.esei.mei.futbol.servicios.EstadioService;
import es.uvigo.esei.mei.futbol.servicios.PartidoService;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Controller
@RequestMapping("/estadios")
public class EstadioController {

    @Autowired
    EstadioService estadioService;
    @Autowired
    PartidoService partidoService;
    @Autowired
    EquipoService equipoService;

    /**
     * Model encapsula el modelo (en este caso sera un Model vacio para ser
     * inicializado)
     */
    @GetMapping
    public String prepararListarEstadios(Model modelo) {
        List<Estadio> estadios = estadioService.buscarTodos();
        modelo.addAttribute("estadios", estadios);
        modelo.addAttribute("nombreEstadio", "");
        return "estadios/listado_estadios";
    }

    @PostMapping("buscar")
    public String prepararEstadiosSearch(@RequestParam(required = false) String nombreEstadio, Model modelo) {
        List<Estadio> estadios = estadioService.buscarNombre(nombreEstadio);
        modelo.addAttribute("estadios", estadios);
        modelo.addAttribute("nombreEstadio", "");
        modelo.addAttribute("nombreCiudad", "");
        return "estadios/listado_estadios";
    }

    /**
     * @param id
     * @param modelo
     * @return View
     * @PathVariable vincula el parametro a un segmento de la URI
     */
    @GetMapping("{id}/eliminar")
    public String borrarEstadio(@PathVariable("id") Long id, Model modelo) {
        Estadio estadio = estadioService.buscarPorID(id);
        if (estadio != null) {
            List<Partido> partidos = partidoService.buscarPorEstadio(estadio);
            String msg = "";
            if (partidos.isEmpty()) {
                List<Equipo> equipos = equipoService.buscarPorEstadio(estadio);
                if (equipos.isEmpty()) {
                    estadioService.eliminar(estadio);
                    msg = "Eliminado Correctamente";
                } else {
                    msg = "ERROR: Elimina primero los equipos";
                }
            } else {
                msg = "ERROR: Elimina primero los partidos";
            }
            modelo.addAttribute(estadio);
            modelo.addAttribute("return", "/estadios");
            modelo.addAttribute("message", msg);
            return "estadios/detalle_estadio";
        } else {
            modelo.addAttribute("path", id + "/eliminar");
            modelo.addAttribute("message", "No se ha podido eliminar el estadio");
            modelo.addAttribute("return", "/estadios");
            return "error_message";
        }
    }

    @GetMapping("{id}")
    public String verEstadio(@PathVariable("id") Long id, Model modelo) {
        Estadio estadio = estadioService.buscarPorID(id);
        if (estadio != null) {
            modelo.addAttribute(estadio);
            modelo.addAttribute("return", "/estadios");
            modelo.addAttribute("message", "Vista en Detalle");
            return "estadios/detalle_estadio";
        } else {
            modelo.addAttribute("path", id + "/eliminar");
            modelo.addAttribute("message", "No se ha podido encontrar el estadio");
            modelo.addAttribute("return", "/estadios");
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
    public ModelAndView prepararNuevoEstadio() {
        Estadio estadio = new Estadio();
        List<Estadio> estadios = estadioService.buscarTodos();

        ModelAndView result = new ModelAndView();
        result.addObject("estadio", estadio);
        result.addObject("estadios", estadios);
        result.addObject("esNuevo", true);
        result.setViewName("estadios/editar_estadio");
        return result;
    }

    /**
     * @param estadio
     * @param fundado
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
    public String crearEstadio(@Valid @ModelAttribute("estadio") Estadio estadio,
            BindingResult resultado, Model modelo) {
        if (!resultado.hasErrors()) {
            estadioService.crear(estadio);
            return "redirect:/estadios";

        } else {
            modelo.addAttribute("path", "/estadios/nuevo");
            modelo.addAttribute("message", "No se ha podido insertar el estadio");
            modelo.addAttribute("binding_result", resultado.toString());
            modelo.addAttribute("return", "/estadios");
            return "error_message";
        }

    }

    @GetMapping("{id}/editar")
    public String prepararEditarEstadio(@PathVariable("id") Long id, Model modelo) {
        try {
            Estadio estadio = estadioService.buscarPorID(id);
            List<Estadio> sortEstadios = new ArrayList<>();
            modelo.addAttribute("estadio", estadio);
            modelo.addAttribute("esNuevo", false);
            return "estadios/editar_estadio";
        } catch (EntityNotFoundException e) {
            modelo.addAttribute("error", "Estadio no encontrado");
            return "error";
        }
    }

    @PostMapping("{id}/editar")
    public String actualizarEstadio(@Valid @PathVariable("id") Long id,
            @ModelAttribute Estadio estadio, BindingResult resultado) {
        if (!resultado.hasErrors()) {
            estadioService.modificar(estadio);
            return "redirect:/estadios";
        } else {
            return null;
        }
    }

}
