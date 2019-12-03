/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.mei.futbol.entidades;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author ivan
 */
@Embeddable
public class Resultado implements Serializable {
    
    private int local;
    
    private int visitante;

    public Resultado() {
    }
    
    public Resultado(int local, int visitante){
        this.local=local;
        this.visitante=visitante;
    }

    public int getLocal() {
        return local;
    }

    public void setLocal(int local) {
        this.local = local;
    }

    public int getVisitante() {
        return visitante;
    }

    public void setVisitante(int visitante) {
        this.visitante = visitante;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.local;
        hash = 17 * hash + this.visitante;
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
        final Resultado other = (Resultado) obj;
        if (this.local != other.local) {
            return false;
        }
        if (this.visitante != other.visitante) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Resultado{" + "local=" + local + ", visitante=" + visitante + '}';
    }
    
}
