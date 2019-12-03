/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.mei.futbol.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ivan
 */
@Entity
public class Partido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Estadio estadio;

    @ManyToOne
    private Equipo local;

    @ManyToOne
    private Equipo visitante;

    @ManyToOne
    private Competicion competicion;

    @Embedded
    private Resultado resultado; //local, visitante

    @Temporal(TemporalType.DATE)
    private Date fecha;

    public Partido() {

    }

    public Partido(Estadio estadio, Equipo local, Equipo visitante, Competicion competicion, Resultado resultado, Date fecha) {
        this.estadio = estadio;
        this.local = local;
        this.visitante = visitante;
        this.competicion = competicion;
        this.resultado = resultado;
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    public Equipo getLocal() {
        return local;
    }

    public void setLocal(Equipo local) {
        this.local = local;
    }

    public Equipo getVisitante() {
        return visitante;
    }

    public void setVisitante(Equipo visitante) {
        this.visitante = visitante;
    }

    public Competicion getCompeticion() {
        return competicion;
    }

    public void setCompeticion(Competicion competicion) {
        this.competicion = competicion;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 83 * hash + Objects.hashCode(this.estadio);
        hash = 83 * hash + Objects.hashCode(this.local);
        hash = 83 * hash + Objects.hashCode(this.visitante);
        hash = 83 * hash + Objects.hashCode(this.competicion);
        hash = 83 * hash + Objects.hashCode(this.resultado);
        hash = 83 * hash + Objects.hashCode(this.fecha);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Partido other = (Partido) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.estadio, other.estadio)) {
            return false;
        }
        if (!Objects.equals(this.local, other.local)) {
            return false;
        }
        if (!Objects.equals(this.visitante, other.visitante)) {
            return false;
        }
        if (!Objects.equals(this.competicion, other.competicion)) {
            return false;
        }
        if (!Objects.equals(this.resultado, other.resultado)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Partido{" + "id=" + id + ", estadio=" + estadio + ", local=" + local + ", visitante=" + visitante + ", competicion=" + competicion + ", resultado=" + resultado + ", fecha=" + fecha + '}';
    }

}
