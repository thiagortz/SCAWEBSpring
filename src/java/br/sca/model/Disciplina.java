/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



/**
 *
 * @author Usuario
 */
@Entity
public class Disciplina{
    
    @Id
    private Integer codigo;
    private String descricao;
    private Integer periodo;
    private Integer cargaHoraria;

    @ManyToOne
    private Curso curso;
    
    
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao.trim();
    }
    
    public Integer getPeriodo() {
        return periodo;
    } 
    
    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }
    
    public Integer getCargaHoraria() {
        return cargaHoraria;
    } 

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }    

    
    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
     
    
}
