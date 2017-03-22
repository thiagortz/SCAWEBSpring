/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.controller;

import br.sca.utils.ExcecaoSCA;
import br.sca.dao.*;
import br.sca.model.*;
//import br.sca.utils.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.classic.Session;

/**
 *
 * @author Usuario
 */
public class CtrCadastroDisciplina {
    private static CtrCadastroDisciplina umCtrCadastroDisciplina;
    DisciplinaDAO disciplinaDAO;
    Session session;
    
    private CtrCadastroDisciplina(){
       DAOFactory daoFactory = new DAOFactory();
       disciplinaDAO = daoFactory.getDisciplinaDAO();
  //     session = HibernateUtil.getSession();
    
    }
    
    public static CtrCadastroDisciplina getInstance(){
        if (umCtrCadastroDisciplina == null){
            umCtrCadastroDisciplina = new CtrCadastroDisciplina();
        }
        return umCtrCadastroDisciplina;
    }

    public List <Disciplina> listarTodos(int ordenacao) throws ExcecaoSCA{
        List <Disciplina>lista = new ArrayList <Disciplina>();
        lista = disciplinaDAO.pegarTodos(session,ordenacao);
        return lista;
    }
    
    
    public List <Disciplina> listarTodosPorCurso(Curso umCurso, int ordenacao) throws ExcecaoSCA{
        List <Disciplina>lista = new ArrayList <Disciplina>();
        lista = disciplinaDAO.pegarTodosPorCurso(umCurso,session,ordenacao);
        return lista;
    }
    
    
    private boolean validar(Disciplina disciplina) throws ExcecaoSCA {        
                 
            if (disciplina.getDescricao() == null || disciplina.getDescricao().trim().equals("")) {
                throw new ExcecaoSCA("A descrição da disciplina é obrigatória.") ;
            }
            if (disciplina.getPeriodo()== null) {
                throw new ExcecaoSCA("O período da disciplina é obrigatório.") ;
            }
            if (disciplina.getCurso() == null) {
                throw new ExcecaoSCA("O Curso da disciplina é obrigatório.") ;
            }

            
            
            return true ;
    }

    public void incluir (Disciplina disciplina) throws ExcecaoSCA {
        try {
            if (validar(disciplina))
              disciplinaDAO.incluir(disciplina,session);
        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }
        
    }
    
    public void alterar (Disciplina disciplina) throws ExcecaoSCA {
        try {
            if (validar(disciplina))
                disciplinaDAO.alterar(disciplina,session);
        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }
        
    }

        public void excluir (Disciplina disciplina) throws ExcecaoSCA {
        try {
            disciplinaDAO.excluir(disciplina,session);
        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }
        
    }

    public Disciplina carregar(Disciplina disciplina) throws ExcecaoSCA {
        try {
            disciplina = disciplinaDAO.carregar(disciplina,session);
        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }

        return disciplina;
    }
    
    

}