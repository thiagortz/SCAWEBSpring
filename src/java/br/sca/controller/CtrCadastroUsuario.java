/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.controller;

import br.sca.utils.*;
import br.sca.dao.*;
import br.sca.model.Usuario;
import java.io.Serializable;

/**
 *
 * @author Usuario
 */
public class CtrCadastroUsuario{
    
    private UsuarioDAO usuarioDAO = null;
   
     public void setUsuarioDAO(UsuarioDAO usuarioDAO)
    {	 
         this.usuarioDAO = usuarioDAO;
    }
     

    public boolean existe(Usuario usuario) throws ExcecaoSCA {
        boolean encontrado;
        try {
            encontrado = usuarioDAO.existe(usuario);
        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }

        return encontrado;
    }
    
   

        
        
    }
        
        
    
   