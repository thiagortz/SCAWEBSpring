/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.email;

/**
 *
 * @author Paulo
 */
public class Mensagem {

 private String destino;
 private String titulo;
 private String mensagem;

 public Mensagem() {
     destino="paulomsfreire@gmail.com";
 }

 public String getDestino() {
    return destino;
 }

 public void setDestino(String destino) {
    this.destino = destino;
 }

 public String getMensagem() {
    return mensagem;
 }

 public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
 }

 public String getTitulo() {
    return titulo;
 }

 public void setTitulo(String titulo) {
    this.titulo = titulo;
 }
}

