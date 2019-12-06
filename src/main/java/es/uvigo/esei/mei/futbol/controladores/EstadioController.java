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

import es.uvigo.esei.mei.futbol.entidades.Estadio;
import es.uvigo.esei.mei.futbol.entidades.Estadio;
import es.uvigo.esei.mei.futbol.servicios.EstadioService;
import es.uvigo.esei.mei.futbol.servicios.EstadioService;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Controller
@RequestMapping("/estadios")
public class EstadioController {

    @Autowired
    EstadioService estadioService;

    /**
     * Model encapsula el modelo (en este caso sera un Model vacio para ser
     * inicializado)
     */
    @GetMapping
    public String prepararListarEstadios(Model modelo) {
        List<Estadio> estadios = estadioService.buscarTodos();
        modelo.addAttribute("estadios", estadios);
        modelo.addAttribute("nombreEstadio", "");
        modelo.addAttribute("nombreCiudad", "");
        return "estadios/listado_estadios";
    }

    @PostMapping("buscar")
    public String prepararEstadiosSearch(@RequestParam(required = false) String nombreEstadio,
            @RequestParam(required = false) String nombreCiudad, Model modelo) {
        List<Estadio> estadios = estadioService.buscarNombreCiudad(nombreEstadio, nombreCiudad);
        modelo.addAttribute("estadios", estadios);
        modelo.addAttribute("nombreEstadio", "");
        modelo.addAttribute("nombreCiudad", "");
        return "estadios/listado_estadios";
    }

    /**
     * @RequestParam captura los parámetros de la peticion (en este caso cuerpo
     * del POST) cuyo nombre coincida con el nombre de los parametros
     */
    /* @PostMapping
    public String actualizarListarEstadios(@RequestParam(required = false) String nombreEstadio,
            @RequestParam(required = false) String ciudad, Model modelo) {
        List<Estadio> estadios;
        if ((nombreEstadio != null) && !nombreEstadio.isEmpty()) {
            estadios = estadioService.buscarPorNombre(nombreEstadio);
        } else if ((ciudad != null) && !ciudad.isEmpty()) {
            estadios = estadioService.buscarPorCiudad(ciudad);
        } else {
            estadios = estadioService.buscarTodos();
        }
        modelo.addAttribute("estadios", estadios);
        return "estadio/listado_estadios";
    }*/
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
            estadioService.eliminar(estadio);
            modelo.addAttribute(estadio);
            modelo.addAttribute("return", "/estadios");
            modelo.addAttribute("message", "Eliminado correctamente");
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
    public String crearEstadio(@Valid @ModelAttribute("estadio") Estadio estadio,
            @DateTimeFormat(iso = ISO.DATE) Date fundado,
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
    public String actualizarEstadio(@Valid @ModelAttribute Estadio estadio, BindingResult resultado) {
        if (!resultado.hasErrors()) {
            estadioService.modificar(estadio);
            return "redirect:/estadios";
        } else {
            return null;
        }
    }

}
