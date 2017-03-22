/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.model;

import javax.persistence.Column;
import javax.persistence.Entity;



/**
 *
 * @author Usuario
 */
@Entity
public class Professor extends Pessoa{
    
    @Column(unique=true)
    private Integer matricula;
    private String titulacaoMaxima;
    
    
   
    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }
    
    public String getTitulacaoMaxima() {
        return titulacaoMaxima;
    }

    public void setTitulacaoMaxima(String titulacaoMaxima) {
        this.titulacaoMaxima = titulacaoMaxima.trim();
    }

    
    
    
}
