/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.beans;

import br.sca.email.EmailUtils;
import br.sca.email.Mensagem;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.mail.EmailException;

/**
 *
 * @author Paulo
 */
@ViewScoped
@ManagedBean
public class EmailBean implements Serializable{

 private Mensagem mensagem = new Mensagem();

 public Mensagem getMensagem() {
    return mensagem;
 }

 public void setMensagem(Mensagem mensagem) {
    this.mensagem = mensagem;
 }

 public void enviaEmail() {
 try {
    EmailUtils.enviaEmail(mensagem);
 } catch (EmailException ex) {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro! Occoreu um erro ao enviar a mensagem." + ex.getMessage(), "Erro"));
 
 }
 }

 public void limpaCampos() {
     mensagem = new Mensagem();
 }

}

