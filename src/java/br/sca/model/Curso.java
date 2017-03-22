/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


/**
 *
 * @author Usuario
 */
@Entity
public class Curso implements Serializable{
    @Id
    private Integer codigo;
    private Integer cargaHoraria;
    private String descricao;
    private Integer numPeriodos;

    @OneToMany(mappedBy="curso")
    private List<Aluno> alunos = new ArrayList<Aluno>();

    /*  @Version  // lock otimista
    private Integer versao; */

   

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    
    
    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }    

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao.trim();
    }

    public Integer getNumPeriodos() {
        return numPeriodos;
    }

    public void setNumPeriodos(Integer numPeriodos) {
        this.numPeriodos = numPeriodos;
    }

    public List<Aluno> getAlunos(){
		return alunos;
    }
    
}
