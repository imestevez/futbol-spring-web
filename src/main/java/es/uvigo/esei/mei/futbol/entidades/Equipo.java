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
public class Equipo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    private Estadio estadio;
    
    private String nombre;
    
    private String ciudad;
    
    private String pais;
        
    @Temporal(TemporalType.DATE)
    private Date fundado;
    
    public Equipo(){
        
    }
    public Equipo(String nombre, Estadio estadio, String ciudad,                
                    String pais, Date fundado){
        this.estadio=estadio;
        this.nombre=nombre;
        this.ciudad=ciudad;
        this.pais=pais;
        this.fundado=fundado;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Date getFundado() {
        return fundado;
    }

    public void setFundado(Date fundado) {
        this.fundado = fundado;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 23 * hash + Objects.hashCode(this.estadio);
        hash = 23 * hash + Objects.hashCode(this.nombre);
        hash = 23 * hash + Objects.hashCode(this.ciudad);
        hash = 23 * hash + Objects.hashCode(this.pais);
        hash = 23 * hash + Objects.hashCode(this.fundado);
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
        final Equipo other = (Equipo) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.fundado != other.fundado) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.ciudad, other.ciudad)) {
            return false;
        }
        if (!Objects.equals(this.pais, other.pais)) {
            return false;
        }
        if (!Objects.equals(this.estadio, other.estadio)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Equipo{" + "id=" + id + ", estadio=" + estadio + ", nombre=" + nombre + ", ciudad=" + ciudad + ", pais=" + pais + ", fundado=" + fundado + '}';
    }

}
