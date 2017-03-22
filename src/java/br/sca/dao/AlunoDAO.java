/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.dao;


import br.sca.utils.ExcecaoSCA;
import br.sca.model.*;
import java.io.Serializable;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Usuario
 */
public class AlunoDAO{

    @Autowired
    private SessionFactory s;

    @Transactional
    public List<Aluno> pegarTodos(int ordenacao) throws ExcecaoSCA {

        Query q;
        String ordem;

        if (ordenacao == 0){
            ordem = "matricula";
        }
        else {
            ordem = "nome";
        }

        try {

           String hql = "from Aluno order by " + ordem;
           q = s.getCurrentSession().createQuery(hql);
        }
        catch(RuntimeException ex)
	{
            throw ex;
        }
        catch(Exception ex)
	{
            throw new ExcecaoSCA("Erro na listagem dos alunos. Descrição " + ex.getMessage());
        }
        return q.list();
    }
 
    @Transactional
    public List<Aluno> pegarTodosPorCurso(Curso umCurso, int ordenacao) throws ExcecaoSCA {
       Query q;

        String ordem;

        if (ordenacao == 0){
            ordem = "matricula";
        }
        else {
            ordem = "nome";
        }

        try {
	String hql = "from Aluno a where a.curso.codigo = :busca order by " + ordem;
	q = s.getCurrentSession().createQuery(hql);
	q.setParameter("busca",umCurso.getCodigo());

        }
        
        catch(RuntimeException ex)
	{
            throw ex;
        }
        catch(Exception ex)
	{
            throw new ExcecaoSCA("Erro na listagem dos alunos. Descrição " + ex.getMessage());
        }

        return q.list();
    }
    
    
  
    
    
    @SuppressWarnings("empty-statement")
    @Transactional
    public void incluir(Aluno aluno) throws ExcecaoSCA {
        
        try {        
            

	    s.getCurrentSession().save(aluno);

	   
        }
        
        catch(RuntimeException ex)
	{
            throw ex;
        }
        catch(Exception ex)
	{
            throw new ExcecaoSCA("Erro na inclusão do aluno. Descrição " + ex.getMessage() + " " + ex.getLocalizedMessage());
        }

    }
    
    @Transactional
    public void alterar(Aluno aluno) throws ExcecaoSCA {
        Query q;

        try {

            String hql = "from Aluno a where a.matricula = :busca";
	   q = s.getCurrentSession().createQuery(hql);
	   q.setParameter("busca",aluno.getMatricula());

           Aluno a = (Aluno)q.list().get(0);


            s.getCurrentSession().lock(a,LockMode.UPGRADE);

	    s.getCurrentSession().merge(aluno);


        }
        catch(RuntimeException ex)
	{
            throw ex;
        }
        catch(Exception ex)
	{
            throw new ExcecaoSCA("Erro na alteração do aluno. Descrição " + ex.getMessage());
        }
    }
    
    @Transactional
    public void excluir(Aluno aluno) throws ExcecaoSCA {

        Query q;

        try {

           String hql = "from Aluno a where a.matricula = :busca";
	   q = s.getCurrentSession().createQuery(hql);
	   q.setParameter("busca",aluno.getMatricula());

           Aluno a = (Aluno)q.list().get(0);

           s.getCurrentSession().delete(a);
        }
        catch(RuntimeException ex)
	{
            throw ex;
        }
        catch (Exception ex) {

            throw new ExcecaoSCA("Erro na exclusão do aluno. Descrição " + ex.getMessage() + " " + ex.getLocalizedMessage());

        }
    }

    @Transactional
    public Aluno carregar(Aluno umAluno) throws ExcecaoSCA {

       Query q;

        try {

           String hql = "from Aluno a where a.matricula = :busca";
	   q = s.getCurrentSession().createQuery(hql);
	   q.setParameter("busca",umAluno.getMatricula());


        }
        
        catch(RuntimeException ex)
	{
            throw ex;
        }
        catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar 0 curso. Descrição " +
                        ex.getMessage());
        }
       

       return (Aluno)q.list().get(0);
        
       

    }
    
  
    
}

    

