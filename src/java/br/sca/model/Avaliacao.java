/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;



/**
 *
 * @author Usuario
 */
@Entity
public class Avaliacao{

    @Id
    @SequenceGenerator(name="codigo",sequenceName="avaliacao_codigo_seq")
    @GeneratedValue(strategy=GenerationType.AUTO,generator="codigo")
    private Integer codigo;


    @ManyToOne
    private Aluno aluno;

    @ManyToOne
    private Turma turma;

    private Float npc1;
    private Float npc2;
    private Float nef;
    private Integer freq;
    private Float segCham;
    private String resultado;

    public Avaliacao() {
        npc1 = new Float(0);
        npc2 = new Float(0);
        nef = new Float(0);
        freq = new Integer(0);
        segCham = new Float(0);
        
    }

    
    
    public Aluno getAluno() {
        return aluno;
    }

    public Integer getFreq() {
        return freq;
    }

    public Float getNef() {
        return nef;
    }

    public Float getNpc1() {
        return npc1;
    }

    public Float getNpc2() {
        return npc2;
    }

    public String getResultado() {
        return resultado;
    }

    public Float getSegCham() {
        return segCham;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public void setFreq(Integer freq) {
        this.freq = freq;
    }

    public void setNef(Float nef) {
        this.nef = nef;
    }

    public void setNpc1(Float npc1) {
        this.npc1 = npc1;
    }

    public void setNpc2(Float npc2) {
        this.npc2 = npc2;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public void setSegCham(Float segCham) {
        this.segCham = segCham;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }
   
    
    
 
    
     
    
}
