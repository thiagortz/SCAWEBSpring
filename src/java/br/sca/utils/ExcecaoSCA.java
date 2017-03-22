/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.utils;

/**
 *
 * @author Usuario
 */


public class ExcecaoSCA extends Exception {
        
    private int codigo;
    private String msg;
    
    public ExcecaoSCA(String msg){
        this.setMsg(msg);
        
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg){
        this.msg=msg;
    }
   
}
