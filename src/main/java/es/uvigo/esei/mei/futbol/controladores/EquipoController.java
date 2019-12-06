package es.uvigo.esei.mei.futbol.controladores;

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

import es.uvigo.esei.mei.futbol.entidades.Equipo;
import es.uvigo.esei.mei.futbol.entidades.Estadio;
import es.uvigo.esei.mei.futbol.servicios.EquipoService;
import es.uvigo.esei.mei.futbol.servicios.EstadioService;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Controller
@RequestMapping("/equipos")
public class EquipoController {

    @Autowired
    EquipoService equipoService;
    @Autowired
    EstadioService estadioService;

    /**
     * Model encapsula el modelo (en este caso sera un Model vacio para ser
     * inicializado)
     */
    @GetMapping
    public String prepararListarEquipos(Model modelo) {
        List<Equipo> equipos = equipoService.buscarTodos();
        modelo.addAttribute("equipos", equipos);
        modelo.addAttribute("nombreEquipo", "");
        modelo.addAttribute("nombreCiudad", "");
        return "equipos/listado_equipos";
    }

    @PostMapping("buscar")
    public String prepararEquiposSearch(@RequestParam(required = false) String nombreEquipo,
            @RequestParam(required = false) String nombreCiudad, Model modelo) {
        List<Equipo> equipos = equipoService.buscarNombreCiudad(nombreEquipo, nombreCiudad);
        modelo.addAttribute("equipos", equipos);
        modelo.addAttribute("nombreEquipo", "");
        modelo.addAttribute("nombreCiudad", "");
        return "equipos/listado_equipos";
    }

    /**
     * @RequestParam captura los parámetros de la peticion (en este caso cuerpo
     * del POST) cuyo nombre coincida con el nombre de los parametros
     */
    /* @PostMapping
    public String actualizarListarEquipos(@RequestParam(required = false) String nombreEquipo,
            @RequestParam(required = false) String ciudad, Model modelo) {
        List<Equipo> equipos;
        if ((nombreEquipo != null) && !nombreEquipo.isEmpty()) {
            equipos = equipoService.buscarPorNombre(nombreEquipo);
        } else if ((ciudad != null) && !ciudad.isEmpty()) {
            equipos = equipoService.buscarPorCiudad(ciudad);
        } else {
            equipos = equipoService.buscarTodos();
        }
        modelo.addAttribute("equipos", equipos);
        return "equipo/listado_equipos";
    }*/
    /**
     * @param id
     * @param modelo
     * @return View
     * @PathVariable vincula el parametro a un segmento de la URI
     */
    @GetMapping("{id}/eliminar")
    public String borrarEquipo(@PathVariable("id") Long id, Model modelo) {
        Equipo equipo = equipoService.buscarPorID(id);
        if (equipo != null) {
            equipoService.eliminar(equipo);
            Estadio estadio = estadioService.buscarPorID(equipo.getEstadio().getId());
            modelo.addAttribute(equipo);
            modelo.addAttribute("nombreEstadio", estadio.getNombre());
            modelo.addAttribute("return", "/equipos");
            modelo.addAttribute("message", "Eliminado correctamente");
            return "equipos/detalle_equipo";
        } else {
            modelo.addAttribute("path", id + "/eliminar");
            modelo.addAttribute("message", "No se ha podido eliminar el equipo");
            modelo.addAttribute("return", "/equipos");
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
    public ModelAndView prepararNuevoEquipo() {
        Equipo equipo = new Equipo();
        List<Estadio> estadios = estadioService.buscarTodos();

        ModelAndView result = new ModelAndView();
        result.addObject("equipo", equipo);
        result.addObject("estadios", estadios);
        result.addObject("esNuevo", true);
        result.setViewName("equipos/editar_equipo");
        return result;
    }

    /**
     * @param equipo
     * @param fundado
     * @param resultado
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
    public String crearEquipo(@Valid @ModelAttribute("equipo") Equipo equipo,
            @DateTimeFormat(iso = ISO.DATE) Date fundado,
            BindingResult resultado, Model modelo) {
        if (!resultado.hasErrors()) {
            equipoService.crear(equipo);
            return "redirect:/equipos";

        } else {
            modelo.addAttribute("path", "/equipos/nuevo");
            modelo.addAttribute("message", "No se ha podido insertar el equipo");
            modelo.addAttribute("binding_result", resultado.toString());
            modelo.addAttribute("return", "/equipos");
            return "error_message";
        }

    }

    @GetMapping("{id}/editar")
    public String prepararEditarEquipo(@PathVariable("id") Long id, Model modelo) {
        try {
            Equipo equipo = equipoService.buscarPorID(id);
            List<Estadio> estadios = estadioService.buscarDistintoId(equipo.getEstadio().getId());
            List<Estadio> sortEstadios = new ArrayList<>();
            sortEstadios.add(equipo.getEstadio());
            for (Estadio e : estadios) {
                sortEstadios.add(e);
            }
            modelo.addAttribute("equipo", equipo);
            modelo.addAttribute("estadios", sortEstadios);
            modelo.addAttribute("esNuevo", false);
            return "equipos/editar_equipo";
        } catch (EntityNotFoundException e) {
            modelo.addAttribute("error", "Equipo no encontrado");
            return "error";
        }
    }

    @PostMapping("{id}/editar")
    public String actualizarEquipo(@Valid @ModelAttribute Equipo equipo, BindingResult resultado) {
        if (!resultado.hasErrors()) {
            equipoService.modificar(equipo);
            return "redirect:/equipos";
        } else {
            return null;
        }
    }

}
