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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ivan
 */
@Entity
public class Estadio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;

    private int aforo;

    @Temporal(TemporalType.DATE)
    private Date construido;

    @Embedded
    private Direccion direccion; //calle, numero

    public Estadio() {

    }

    public Estadio(String nombre, int aforo, Date construido,
            Direccion direccion) {
        this.nombre = nombre;
        this.aforo = aforo;
        this.construido = construido;
        this.direccion = direccion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public Date getConstruido() {
        return construido;
    }

    public void setConstruido(Date construido) {
        this.construido = construido;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 17 * hash + Objects.hashCode(this.nombre);
        hash = 17 * hash + this.aforo;
        hash = 17 * hash + Objects.hashCode(this.construido);
        hash = 17 * hash + Objects.hashCode(this.direccion);
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
        final Estadio other = (Estadio) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.aforo != other.aforo) {
            return false;
        }
        if (this.construido != other.construido) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.direccion, other.direccion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Estadio{" + "id=" + id + ", nombre=" + nombre + ", aforo=" + aforo + ", construido=" + construido + ", direccion=" + direccion + '}';
    }
}
