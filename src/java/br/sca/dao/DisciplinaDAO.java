/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.dao;

import br.sca.utils.ExcecaoSCA;
import br.sca.model.Curso;
import br.sca.model.Disciplina;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Usuario
 */
public class DisciplinaDAO{
    
    public List<Disciplina> pegarTodos(Session s, int ordenacao) throws ExcecaoSCA {
       Query q;

        String ordem;

        if (ordenacao == 0){
            ordem = "codigo";
        }
        else {
            ordem = "descricao";
        }

        try {

           String hql = "from Disciplina order by " + ordem;
           q = s.createQuery(hql);

        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao listar as disciplinas. Descricao " +
                        ex.getMessage());
        }
        return q.list();
    }
    

    public List<Disciplina> pegarTodosPorCurso(Curso umCurso, Session s, int ordenacao) throws ExcecaoSCA {
       Query q;

        String ordem;

        if (ordenacao == 0){
            ordem = "codigo";
        }
        else {
            ordem = "descricao";
        }

        try {

           String hql = "from Disciplina d where d.curso.codigo = :busca order by " + ordem;
	   q = s.createQuery(hql);
	   q.setParameter("busca",umCurso.getCodigo());


        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar a disciplinas por curso. Descricao " +
                        ex.getMessage());
        }


       return q.list();

    }
    
    
    public List<Disciplina> pegarDisciplinasDoPeriodoPorCurso(int periodo, Curso umCurso, Session s) throws ExcecaoSCA {

        Query q;

        try {

           String hql = "from Disciplina d where d.curso.codigo = :busca1 and d.periodo = :busca2";
	   q = s.createQuery(hql);
	   q.setParameter("busca1",umCurso.getCodigo());
           q.setParameter("busca2",periodo);

        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar a disciplina por curso do período. Descricao " +
                        ex.getMessage());
        }


       return q.list();

    }
    
    
    
    public void incluir(Disciplina disciplina,Session s) throws ExcecaoSCA {
    try {
            Transaction t = s.beginTransaction();

	    s.save(disciplina);

	    t.commit();
        } catch (Exception ex) {

                throw new ExcecaoSCA("Erro na inclusão da disciplina. Descricao " +
                        ex.getMessage());
        }
    }
    
    public void alterar(Disciplina disciplina, Session s) throws ExcecaoSCA {
try {
            Transaction t = s.beginTransaction();

	    s.update(disciplina);

	    t.commit();
        } catch (Exception ex) {

                throw new ExcecaoSCA("Erro na alteração da disciplina. Descricao " +
                        ex.getMessage());
        }
    }
    
    public void excluir(Disciplina disciplina, Session s) throws ExcecaoSCA {
       try {
            Transaction t = s.beginTransaction();

            Disciplina d = (Disciplina) s.load(Disciplina.class,disciplina.getCodigo());
                       
	    s.delete(d);

	    t.commit();
        } catch (Exception ex) {

                throw new ExcecaoSCA("Erro na exclusão da disciplina. Descricao " +
                        ex.getMessage());
        }
    }

    public Disciplina carregar(Disciplina disciplina, Session s) throws ExcecaoSCA {

       Query q;

        try {

           String hql = "from Disciplina d where d.codigo = :busca";
	   q = s.createQuery(hql);
	   q.setParameter("busca",disciplina.getCodigo());


        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar a disciplina. Descricao " +
                        ex.getMessage());
        }
       

       return (Disciplina)q.list().get(0);



    }
    
  
    
}

    

