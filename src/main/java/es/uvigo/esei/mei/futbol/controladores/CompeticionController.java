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

import es.uvigo.esei.mei.futbol.entidades.Competicion;
import es.uvigo.esei.mei.futbol.entidades.Competicion;
import es.uvigo.esei.mei.futbol.servicios.CompeticionService;
import es.uvigo.esei.mei.futbol.servicios.CompeticionService;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Controller
@RequestMapping("/competiciones")
public class CompeticionController {

    @Autowired
    CompeticionService competicionService;

    /**
     * Model encapsula el modelo (en este caso sera un Model vacio para ser
     * inicializado)
     */
    @GetMapping
    public String prepararListarCompeticiones(Model modelo) {
        List<Competicion> competiciones = competicionService.buscarTodos();
        modelo.addAttribute("competiciones", competiciones);
        modelo.addAttribute("nombreCompeticion", "");
        modelo.addAttribute("nombreTipo", "");
        return "competiciones/listado_competiciones";
    }

    @PostMapping("buscar")
    public String prepararCompeticionesSearch(@RequestParam(required = false) String nombreCompeticion,
            @RequestParam(required = false) String nombreTipo, Model modelo) {
        List<Competicion> competiciones = competicionService.buscarNombreTipo(nombreCompeticion, nombreTipo);
        modelo.addAttribute("competiciones", competiciones);
        modelo.addAttribute("nombreCompeticion", "");
        modelo.addAttribute("nombreTipo", "");
        return "competiciones/listado_competiciones";
    }

    /**
     * @RequestParam captura los parámetros de la peticion (en este caso cuerpo
     * del POST) cuyo nombre coincida con el nombre de los parametros
     */
    /* @PostMapping
    public String actualizarListarCompeticiones(@RequestParam(required = false) String nombreCompeticion,
            @RequestParam(required = false) String tipo, Model modelo) {
        List<Competicion> competiciones;
        if ((nombreCompeticion != null) && !nombreCompeticion.isEmpty()) {
            competiciones = competicionService.buscarPorNombre(nombreCompeticion);
        } else if ((tipo != null) && !tipo.isEmpty()) {
            competiciones = competicionService.buscarPorTipo(tipo);
        } else {
            competiciones = competicionService.buscarTodos();
        }
        modelo.addAttribute("competiciones", competiciones);
        return "competicion/listado_competiciones";
    }*/
    /**
     * @param id
     * @param modelo
     * @return View
     * @PathVariable vincula el parametro a un segmento de la URI
     */
    @GetMapping("{id}/eliminar")
    public String borrarCompeticion(@PathVariable("id") Long id, Model modelo) {
        Competicion competicion = competicionService.buscarPorID(id);
        if (competicion != null) {
            competicionService.eliminar(competicion);
            modelo.addAttribute(competicion);
            modelo.addAttribute("return", "/competiciones");
            modelo.addAttribute("message", "Eliminado correctamente");
            return "competiciones/detalle_competicion";
        } else {
            modelo.addAttribute("path", id + "/eliminar");
            modelo.addAttribute("message", "No se ha podido eliminar el competicion");
            modelo.addAttribute("return", "/competiciones");
            return "error_message";
        }
    }
    @GetMapping("{id}")
    public String verCompeticion(@PathVariable("id") Long id, Model modelo) {
        Competicion competicion = competicionService.buscarPorID(id);
        if (competicion != null) {
            modelo.addAttribute(competicion);
            modelo.addAttribute("return", "/competiciones");
            modelo.addAttribute("message", "Vista en Detalle");
            return "competiciones/detalle_competicion";
        } else {
            modelo.addAttribute("path", id + "/eliminar");
            modelo.addAttribute("message", "No se ha podido encontrar el competicion");
            modelo.addAttribute("return", "/competiciones");
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
    public ModelAndView prepararNuevoCompeticion() {
        Competicion competicion = new Competicion();
        List<Competicion> competiciones = competicionService.buscarTodos();

        ModelAndView result = new ModelAndView();
        result.addObject("competicion", competicion);
        result.addObject("competiciones", competiciones);
        result.addObject("esNuevo", true);
        result.setViewName("competiciones/editar_competicion");
        return result;
    }

    /**
     * @param competicion
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
    public String crearCompeticion(@Valid @ModelAttribute("competicion") Competicion competicion,
            @DateTimeFormat(iso = ISO.DATE) Date fundado,
            BindingResult resultado, Model modelo) {
        if (!resultado.hasErrors()) {
            competicionService.crear(competicion);
            return "redirect:/competiciones";

        } else {
            modelo.addAttribute("path", "/competiciones/nuevo");
            modelo.addAttribute("message", "No se ha podido insertar el competicion");
            modelo.addAttribute("binding_result", resultado.toString());
            modelo.addAttribute("return", "/competiciones");
            return "error_message";
        }

    }

    @GetMapping("{id}/editar")
    public String prepararEditarCompeticion(@PathVariable("id") Long id, Model modelo) {
        try {
            Competicion competicion = competicionService.buscarPorID(id);
            List<Competicion> sortCompeticiones = new ArrayList<>();
            modelo.addAttribute("competicion", competicion);
            modelo.addAttribute("esNuevo", false);
            return "competiciones/editar_competicion";
        } catch (EntityNotFoundException e) {
            modelo.addAttribute("error", "Competicion no encontrado");
            return "error";
        }
    }

    @PostMapping("{id}/editar")
    public String actualizarCompeticion(@Valid @ModelAttribute Competicion competicion, BindingResult resultado) {
        if (!resultado.hasErrors()) {
            competicionService.modificar(competicion);
            return "redirect:/competiciones";
        } else {
            return null;
        }
    }

}
