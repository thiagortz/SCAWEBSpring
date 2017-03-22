/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.model;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Entity;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
//  @Inheritance(strategy=InheritanceType.JOINED)
public abstract class Pessoa implements Serializable{

    @Id
    @SequenceGenerator(name="codigo",sequenceName="pessoa_codigo_seq",
		           allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="codigo")
    private Integer codigo;

    private String nome;
    private String sexo; 
    private String identidade;
    private String orgaoExpedidor;
    private String ufIdentidade;
    private String cpf;
    @Temporal(TemporalType.DATE)
    private Calendar dataNascimento = new GregorianCalendar();
   // private String dataNascimento;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String email;
    private String telResidencial;
    private String telComercial;
    private String telCelular;

    
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
      
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome.trim();
    }    
    
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String nome) {
        this.sexo = nome.trim();
    }    

    public String getIdentidade() {
        return identidade;
    }

    public void setIdentidade(String identidade) {
        this.identidade = identidade.trim();
    }    

    public String getOrgaoExpedidor() {
        return orgaoExpedidor;
    }

    public void setOrgaoExpedidor(String orgaoExpedidor) {
        this.orgaoExpedidor = orgaoExpedidor.trim();
    }

    public String getUfIdentidade() {
        return ufIdentidade;
    }

   public void setUfIdentidade(String ufIdentidade) {
        this.ufIdentidade = ufIdentidade.trim();
    }    

   public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf.trim();
    }

    public Calendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Calendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

  /* public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento.trim();
    } */

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro.trim();
    } 

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero.trim();
    }    

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento.trim();
    }     

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro.trim();
    }   

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade.trim();
    } 

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf.trim();
    } 

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public String getTelResidencial() {
        return telResidencial;
    }

    public void setTelResidencial(String telResidencial) {
        this.telResidencial = telResidencial.trim();
    }

    public String getTelComercial() {
        return telComercial;
    }

    public void setTelComercial(String telComercial) {
        this.telComercial = telComercial.trim();
    }

    public String getTelCelular() {
        return telCelular;
    }

    public void setTelCelular(String telCelular) {
        this.telCelular = telCelular.trim();
    }
}
