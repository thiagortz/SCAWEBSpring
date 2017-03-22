/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.controller;

import br.sca.dao.*;
import br.sca.model.Professor;
import br.sca.utils.*;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.classic.Session;

/**
 *
 * @author Usuario
 */
public class CtrCadastroProfessor {
    private static CtrCadastroProfessor umCtrCadastroProfessor;
    ProfessorDAO professorDAO;
    Session session;
    
    private CtrCadastroProfessor(){
           
        
     DAOFactory daoFactory = new DAOFactory();
     professorDAO = daoFactory.getProfessorDAO();
   //  session = HibernateUtil.getSession();
    
    }
    
    public static CtrCadastroProfessor getInstance(){
        if (umCtrCadastroProfessor == null){
            umCtrCadastroProfessor = new CtrCadastroProfessor();
        }
        return umCtrCadastroProfessor;
    }    
    
    
    public List <Professor> listarTodos(int ordenacao) throws ExcecaoSCA{
        List <Professor>lista = new ArrayList <Professor>();
        lista = professorDAO.pegarTodos(session,ordenacao);
        return lista;
    }
    
    private boolean validar(Professor professor) throws ExcecaoSCA {        
                 
        if(professor.getNome() == null || professor.getNome().trim().equals("")){
            throw new ExcecaoSCA("Nome deve ser preenchido");
        }
        if(professor.getCpf() == null || professor.getCpf().trim().equals("")){
            throw new ExcecaoSCA("CPF deve ser preenchido");
        }        
        if(professor.getSexo() == null || professor.getSexo().trim().equals("")){
            throw new ExcecaoSCA("Sexo deve ser preenchido");
        }        
        if (professor.getLogradouro() == null || professor.getLogradouro().equals("")){
            throw new ExcecaoSCA("Logradouro deve ser preenchido");
        }
        if (professor.getNumero() == null || professor.getNumero().equals("")){
            throw new ExcecaoSCA("Número deve ser preenchido");
        }
        if (professor.getBairro() == null || professor.getBairro().equals("")){
            throw new ExcecaoSCA("Bairro deve ser preenchido");
        }
        if (professor.getCidade() == null || professor.getCidade().equals("")){
            throw new ExcecaoSCA("Cidade deve ser preenchida");
        }
        if (professor.getCep()== null || professor.getCep().trim().equals("")){
            throw new ExcecaoSCA("Cep deve ser preenchido");
        }
        if (professor.getUf() == null || professor.getUf().equals("")){
            throw new ExcecaoSCA("UF deve ser preenchido");
        }        
        if(professor.getDataNascimento() == null){
            throw new ExcecaoSCA("Data de nascimento deve ser preenchida");
        }
        
     /*  if (TrataData.dataInvalida(professor.getDataNascimento())){
            throw new ExcecaoSCA("Data de nascimento inválida");
        } */
                         
        if (professor.getMatricula().intValue() < 0) {
                throw new ExcecaoSCA("A Matrícula não dave ser negativa.") ;
        }
        
        if (professor.getTitulacaoMaxima()== null || professor.getTitulacaoMaxima().trim().equals("")) {
            throw new ExcecaoSCA("A titulação é obrigatória.") ;
        }
   
       
            
        return true ;
    }

    public void incluir (Professor professor) throws ExcecaoSCA {
        try {
            if (validar(professor))
              professorDAO.incluir(professor,session);
        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }
        
    }
    
    public void alterar (Professor professor) throws ExcecaoSCA {
        try {
            if (validar(professor))
                professorDAO.alterar(professor,session);
        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }
        
    }

        public void excluir (Professor professor) throws ExcecaoSCA {
        try {
            professorDAO.excluir(professor,session);
        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }
        
    }

    public Professor carregar(Professor professor) throws ExcecaoSCA {
        try {
            professor = professorDAO.carregar(professor,session);
        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }
        return professor;
    }
    
 

        
        
    }
        
        
    
   