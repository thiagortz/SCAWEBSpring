/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.controller;

import br.sca.utils.*;
import br.sca.dao.*;
import br.sca.model.Curso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Usuario
 */
public class CtrCadastroCurso{
    
    private CursoDAO cursoDAO = null;


    public void setCursoDAO(CursoDAO cursoDAO)
    {	
        this.cursoDAO = cursoDAO;
    }
    

    public List <Curso> listarTodos(int ordenacao) throws ExcecaoSCA{
        List <Curso>lista = null;
        try {

            lista = new ArrayList <Curso>();
        
            lista = cursoDAO.pegarTodos(ordenacao);
        }
        catch(ExcecaoSCA e)
	{
            throw e;
        }
        catch(RuntimeException e)
	{
            throw new ExcecaoSCA("Erro na listagem dos cursos. Descricao " + e.getMessage());
        }
	
        
        return lista;
    }
    
    
    private boolean validar(Curso curso) throws ExcecaoSCA {        
                 
                    
            if (curso.getCargaHoraria().intValue() <= 0) {
                throw new ExcecaoSCA("A Carga Horária dave ser maior que 0.") ;
            }            
            if (curso.getDescricao() == null || curso.getDescricao().trim().equals("")) {
                throw new ExcecaoSCA("A descrição do curso é obrigatória.") ;
            }
            if (curso.getNumPeriodos().intValue() <= 0) {
                throw new ExcecaoSCA("O número de períodos deve ser maior que 0.") ;
            }
            
            return true ;
    }

    @Transactional
    public void incluir (Curso curso) throws ExcecaoSCA {
        try {
            
            if (validar(curso))
              
              
              cursoDAO.incluir(curso);
              
        }
        
        catch(ExcecaoSCA e)
	{
            throw e;
        }
        catch(RuntimeException e)
	{   
            if (e instanceof ConstraintViolationException){
                throw new ExcecaoSCA("Curso duplicado. Descrição " + ((ConstraintViolationException)e).getSQLException().getNextException().getSQLState());
            }
            else{
                throw new ExcecaoSCA("Erro na inclusão do curso. Descrição " + e.getMessage() + "/" + e.getCause());
            }



        }
        
	
        
    }
    
    @Transactional
    public void alterar (Curso curso) throws ExcecaoSCA {
        try {
            
            if (validar(curso)){
              
              
              cursoDAO.alterar(curso);
              
            }
        }
        catch(ExcecaoSCA e)
	{
            throw e;
        }
        catch(ObjectNotFoundException e)
	{ 
	  throw new ExcecaoSCA("Erro na alteração do curso. Descricao " + "Curso não encontrado.");
	}
	catch(RuntimeException e)
	{
            throw new ExcecaoSCA("Erro na alteração do curso. Descricao " + e.getMessage());
        }
	        
    }

    //@Transactional
    public void excluir (Curso curso) throws ExcecaoSCA {
        try {
            System.out.println("CTR1*************");
            cursoDAO.excluir(curso);
            System.out.println("CTR2*************");
	}
        catch(ExcecaoSCA e)
	{   System.out.println("SCACont*************");
            throw e;
        }
	catch(ObjectNotFoundException e)
	{ System.out.println("ObjectCont*************");
	  throw new ExcecaoSCA("Erro na exclusão do curso. Descricao " + "Curso não encontrado.");
	}
	catch(RuntimeException e)
	{   System.out.println("RTCont*************");
            if (e instanceof ConstraintViolationException){
                throw new ExcecaoSCA("Curso possuído por alguma disciplina ou aluno. Descrição " + ((ConstraintViolationException)e).getSQLException().getNextException().getSQLState());
            }
            else{
                throw new ExcecaoSCA("Erro na exclusão do curso. Descrição " + e.getMessage() + "/" + e.getCause());
            }
            
        }
        catch(Exception ex)
	{
            System.out.println("ECtr*************");
            throw new ExcecaoSCA("Erro na exclusão do curso. Descrição " + ex.getMessage());
        }
	
        
    }

    @Transactional
    public Curso carregar(Curso curso) throws ExcecaoSCA {
        try {
            
            curso = cursoDAO.carregar(curso);
        }
        catch(ExcecaoSCA e)
	{
            throw e;
        }
	catch(ObjectNotFoundException e)
	{
	  throw new ExcecaoSCA("Erro ao carregar o curso. Descricao " + "Curso não encontrado.");
	}
	catch(RuntimeException e)
	{
           throw new ExcecaoSCA("Erro ao carregar o curso. Descrição " + e.getMessage() + "/" + e.getCause());

        }

        return curso;
    }
    
   

        
        
    }
        
        
    
   