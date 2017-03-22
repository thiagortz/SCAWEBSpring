/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.controller;

import br.sca.utils.ExcecaoSCA;
import br.sca.dao.*;
import br.sca.model.*;
import br.sca.utils.TrataData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Usuario
 */
public class CtrCadastroAluno{
   
    private AlunoDAO alunoDAO=null;

    ResourceBundle prop = ResourceBundle.getBundle("br.sca.utils.anoSemestreAtivo");
    String anoSemestreAtivo = prop.getString("anoSemestre");


    public void setAlunoDAO(AlunoDAO alunoDAO)
    {	this.alunoDAO = alunoDAO;
    }
    
    
    public List <Aluno> listarTodos(int ordenacao) throws ExcecaoSCA{
        
        List <Aluno>lista = new ArrayList <Aluno>();

        try {
        lista = alunoDAO.pegarTodos(ordenacao);
        

        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }
        return lista;
    }
    
    public List <Aluno> listarTodosPorCurso(Curso umCurso, int ordenacao) throws ExcecaoSCA{
        List <Aluno>lista = new ArrayList <Aluno>();
      try {
        lista = alunoDAO.pegarTodosPorCurso(umCurso,ordenacao);
        
      } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
      }

        return lista;
    }
    
    
    private boolean validar(Aluno aluno) throws ExcecaoSCA {        
                 
        if(aluno.getNome() == null || aluno.getNome().trim().equals("")){
            throw new ExcecaoSCA("Nome deve ser preenchido");
        }
        if(aluno.getCpf() == null || aluno.getCpf().trim().equals("")){
            throw new ExcecaoSCA("CPF deve ser preenchido");
        }        
        if(aluno.getSexo() == null || aluno.getSexo().trim().equals("")){
            throw new ExcecaoSCA("Sexo deve ser preenchido");
        }        
        if (aluno.getLogradouro() == null || aluno.getLogradouro().equals("")){
            throw new ExcecaoSCA("Logradouro deve ser preenchido");
        }
        if (aluno.getNumero() == null || aluno.getNumero().equals("")){
            throw new ExcecaoSCA("Número deve ser preenchido");
        }
        if (aluno.getBairro() == null || aluno.getBairro().equals("")){
            throw new ExcecaoSCA("Bairro deve ser preenchido");
        }
        if (aluno.getCidade() == null || aluno.getCidade().equals("")){
            throw new ExcecaoSCA("Cidade deve ser preenchida");
        }
        if (aluno.getCep() == null || aluno.getCep().trim().equals("")){
            throw new ExcecaoSCA("Cep deve ser preenchido");
        }
        if (aluno.getUf() == null || aluno.getUf().equals("")){
            throw new ExcecaoSCA("UF deve ser preenchido");
        }        
    /*    if(aluno.getDataNascimento() == null){
            throw new ExcecaoSCA("Data de nascimento deve ser preenchida");
        }
        
       if (TrataData.dataInvalida(aluno.getDataNascimento().toString())){
            throw new ExcecaoSCA("Data de nascimento inválida");
        }*/
                         
        if (aluno.getMatricula().intValue() < 0) {
                throw new ExcecaoSCA("A Matrícula não dave ser negativa.") ;
        }
        
        if (aluno.getAnoSemestreInicio()== null || aluno.getAnoSemestreInicio().trim().equals("")) {
                throw new ExcecaoSCA("O ano e o semestre do aluno são obrigatórios.") ;
        }
        if (TrataData.AnoSemestreInvalido(aluno.getAnoSemestreInicio())){
                throw new ExcecaoSCA("O ano e o semestre do aluno são inválidos");
        }
        
        if (aluno.getCurso() == null) {
                throw new ExcecaoSCA("O Curso do aluno é obrigatório.") ;
        }
        
        return true ;
    }

    @Transactional
    public void incluir (Aluno aluno) throws ExcecaoSCA {
        try {
            if (validar(aluno))
            {
              if (!aluno.getAnoSemestreInicio().equals(anoSemestreAtivo))
              {
                 throw new ExcecaoSCA("O aluno não é do Ano semestre ativo.") ;  
              }
              
              alunoDAO.incluir(aluno);
            }
        }
        catch(ExcecaoSCA e)
	{
            throw e;
        }
        catch(RuntimeException e)
	{
            if (e instanceof ConstraintViolationException){
                throw new ExcecaoSCA("Aluno duplicado. Descrição " + ((ConstraintViolationException)e).getSQLException().getNextException().getSQLState());
            }
            else{
                throw new ExcecaoSCA("Erro na inclusão do aluno. Descrição " + e.getMessage() + "/" + e.getCause());
            }



        }
        
    }
    
    @Transactional
    public void alterar (Aluno aluno) throws ExcecaoSCA {
        try {
            if (validar(aluno))
            {
              alunoDAO.alterar(aluno);
            }    
        }
        catch(ExcecaoSCA e)
	{
            throw e;
        }
        catch(ObjectNotFoundException e)
	{
	  throw new ExcecaoSCA("Erro na alteração do aluno. Descricao " + "Aluno não encontrado.");
	}
	catch(RuntimeException e)
	{
            throw new ExcecaoSCA("Erro na alteração do aluno. Descricao " + e.getMessage());
        }
        
    }

    @Transactional
    public void excluir (Aluno aluno) throws ExcecaoSCA {
        try {
            alunoDAO.excluir(aluno);
        }
        catch(ExcecaoSCA e)
	{
            throw e;
        }
	catch(ObjectNotFoundException e)
	{
	  throw new ExcecaoSCA("Erro na exclusão do aluno. Descricao " + "Aluno não encontrado.");
	}
	catch(RuntimeException e)
	{
            if (e instanceof ConstraintViolationException){
                throw new ExcecaoSCA("Aluno matriculado em alguma turma. Descrição " + ((ConstraintViolationException)e).getSQLException().getNextException().getSQLState());
            }
            else{
                throw new ExcecaoSCA("Erro na exclusão do aluno. Descrição " + e.getMessage() + "/" + e.getCause());
            }

        }
        
    }

    @Transactional
    public Aluno carregar(Aluno aluno) throws ExcecaoSCA {
        try {
            aluno = alunoDAO.carregar(aluno);
        }
        catch(ExcecaoSCA e)
	{
            throw e;
        }
	catch(ObjectNotFoundException e)
	{
	  throw new ExcecaoSCA("Erro ao carregar o aluno. Descricao " + "Aluno não encontrado.");
	}
	catch(RuntimeException e)
	{
           throw new ExcecaoSCA("Erro ao carregar o aluno. Descrição " + e.getMessage() + "/" + e.getCause());

        }

        return aluno;
    }
    


        
 }
        
        
    
   