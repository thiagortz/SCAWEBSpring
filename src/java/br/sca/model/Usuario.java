/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


/**
 *
 * @author Usuario
 */
@Entity
public class Usuario implements Serializable{
    
    @Id
    @SequenceGenerator(name="id",sequenceName="usuario_id_seq")
    @GeneratedValue(strategy=GenerationType.AUTO,generator="id")
    private Long id;

    private String nome;
    private String senha;

    public Long getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public String getSenha(){
        return senha;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }
    
}
