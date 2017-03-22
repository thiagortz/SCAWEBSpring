/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;



/**
 *
 * @author Usuario
 */
@Entity
public class Aluno extends Pessoa implements Serializable{
    
    @Column(unique=true)
    private Integer matricula;
    private String anoSemestreInicio;
    
    @ManyToOne
    private Curso curso;
    
   
    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }
    
    public String getAnoSemestreInicio() {
        return anoSemestreInicio;
    }

    public void setAnoSemestreInicio(String anoSemestreInicio) {
        this.anoSemestreInicio = anoSemestreInicio.trim();
    } 

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }    
    
}
