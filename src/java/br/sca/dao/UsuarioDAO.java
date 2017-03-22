/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.dao;

import br.sca.utils.ExcecaoSCA;
import br.sca.model.Usuario;
import java.io.Serializable;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Usuario
 */
public class UsuarioDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public boolean existe(Usuario usuario) throws ExcecaoSCA {
        
       	Query q;

        try {
           
           String hql = "from Usuario u where u.nome = :pNome and u.senha = :pSenha";
	   q = sessionFactory.getCurrentSession().createQuery(hql);
	   q.setParameter("pNome",usuario.getNome());
           q.setParameter("pSenha",usuario.getSenha());


        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar o usuário. Descricao " +
                        ex.getMessage());
        }
        
        boolean encontrado = !q.list().isEmpty();

        return encontrado;


    }
    
  
    
}

    

