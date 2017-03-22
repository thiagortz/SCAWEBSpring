/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.dao;

import br.sca.utils.ExcecaoSCA;
import br.sca.model.Curso;
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
public class CursoDAO{

    @Autowired
    private SessionFactory s;

    @Transactional
    public List<Curso> pegarTodos(int ordenacao) throws ExcecaoSCA {

 	Query q;
        String ordem;
        if (ordenacao == 0){
            ordem = "codigo";
        }
        else {
           if (ordenacao == 1){
            ordem = "descricao";
           }
           else
           {
            ordem = "cargaHoraria";
           }
        }



        //SQLQuery sqlQuery;
        try {
           /* String sql = "select * from curso";
	    sqlQuery = session.createSQLQuery(sql);
	    sqlQuery.addEntity(Curso.class); */
	    


           String hql = "from Curso order by " + ordem;
           q = s.getCurrentSession().createQuery(hql);


        } 
        catch(RuntimeException ex)
	{
            throw ex;
        }
        catch(Exception ex)
	{
            throw new ExcecaoSCA("Erro na listagem dos cursos. Descrição " + ex.getMessage());
        }
        //return sqlQuery.list();
        return q.list();
    }
    
    @Transactional
    public void incluir(Curso curso) throws ExcecaoSCA {
        try {

	    s.getCurrentSession().save(curso);
        }
        catch(RuntimeException ex)
	{
            throw ex;
        }
        catch(Exception ex)
	{
            throw new ExcecaoSCA("Erro na inclusão do curso. Descrição " + ex.getMessage() + " " + ex.getLocalizedMessage());
        }
        

    }
    
    @Transactional
    public void alterar(Curso curso) throws ExcecaoSCA {
        try {
           
            Curso c = (Curso) s.getCurrentSession().load(Curso.class,curso.getCodigo());


            s.getCurrentSession().lock(c,LockMode.UPGRADE);

	    s.getCurrentSession().merge(curso);

	    
        }
        catch(RuntimeException ex)
	{
            throw ex;
        }
        catch(Exception ex)
	{
            throw new ExcecaoSCA("Erro na alteração do curso. Descrição " + ex.getMessage());
        }
    }
    
    @Transactional
    public void excluir(Curso curso) throws ExcecaoSCA {
        try {

           Curso c = (Curso) s.getCurrentSession().load(Curso.class,curso.getCodigo());
           System.out.println("DAO1*************");
           s.getCurrentSession().delete(c);
           System.out.println("DAO2*************");
        }
        
        catch(RuntimeException ex)
	{   System.out.println("REDAO*************");
            throw ex;
        }
        catch(Exception ex)
	{
            System.out.println("EDAO*************");
            throw new ExcecaoSCA("Erro na exclusão do curso. Descrição " + ex.getMessage());
        }
        
        
    }

    @Transactional
    public Curso carregar(Curso curso) throws ExcecaoSCA {
        
       	Query q;

        try {

           String hql = "from Curso c where c.codigo = :busca";
	   q = s.getCurrentSession().createQuery(hql);
	   q.setParameter("busca",curso.getCodigo());


        }
        catch(RuntimeException ex)
	{
            throw ex;
        }
        catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar 0 curso. Descrição " +
                        ex.getMessage());
        }
        
        return (Curso)q.list().get(0);


    }
    
  
    
}

    

