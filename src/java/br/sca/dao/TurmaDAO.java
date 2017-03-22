/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.dao;

import br.sca.utils.ExcecaoSCA;
import br.sca.model.*;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Usuario
 */
public class TurmaDAO{
    
    public List<Turma> pegarTodos(Session s, int ordenacao) throws ExcecaoSCA {
       Query q;
       String ordem;

        if (ordenacao == 0){
            ordem = "numero";
        }
        else {
            ordem = "anosemestre";
        }

        try {

           String hql = "from Turma order by " + ordem;
           q = s.createQuery(hql);

        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao listar as turmas. Descricao " +
                        ex.getMessage());
        }
        return q.list();
    }
    
    public List<Turma> pegarTodosPorAnoSemestreCurso(String anoSemestre, Curso curso,Session s) throws ExcecaoSCA {
        Query q;

        try {

           String hql = "from Turma t where t.anoSemestre = :busca1 and t.disciplina.curso.codigo = :busca2";
	   q = s.createQuery(hql);
	   q.setParameter("busca1",anoSemestre);
           q.setParameter("busca2",curso.getCodigo());

        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar a  turma por ano semestre e curso. Descricao " +
                        ex.getMessage());
        }


       return q.list();

    }

    
    public List<Turma> pegarTodosPorAnoSemestreDisciplina(String anoSemestre, Disciplina disciplina, Session s) throws ExcecaoSCA {
                Query q;

        try {

           String hql = "from Turma t where t.anoSemestre = :busca1 and t.disciplina.codigo = :busca2";
	   q = s.createQuery(hql);
	   q.setParameter("busca1",anoSemestre);
           q.setParameter("busca2",disciplina.getCodigo());

        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar a turma por ano semestre e disciplina. Descricao " +
                        ex.getMessage());
        }


       return q.list();
    }
    
    
    public void incluir(Turma turma, Session s) throws ExcecaoSCA {
    try {
            Transaction t = s.beginTransaction();

	    s.save(turma);

	    t.commit();
        } catch (Exception ex) {

                throw new ExcecaoSCA("Erro na inclusão da turma. Descricao " +
                        ex.getMessage());
        }
    }
    
    public void alterar(Turma turma,Session s) throws ExcecaoSCA {
        try {
            Transaction t = s.beginTransaction();

	    s.update(turma);

	    t.commit();
        } catch (Exception ex) {

                throw new ExcecaoSCA("Erro na alteração da turma. Descricao " +
                        ex.getMessage());
        }
    }
    
    public void excluir(Turma turma, Session s) throws ExcecaoSCA {
        try {
            Transaction t = s.beginTransaction();

            Turma tu = (Turma) s.load(Turma.class,turma.getNumero());

	    s.delete(tu);

	    t.commit();
        } catch (Exception ex) {

                throw new ExcecaoSCA("Erro na exclusão da turma. Descricao " +
                        ex.getMessage());
        }
    }

    
    
    public Turma carregar(Turma turma, Session s) throws ExcecaoSCA {
        Query q;

        try {

           String hql = "from Turma t where t.numero = :busca";
	   q = s.createQuery(hql);
	   q.setParameter("busca",turma.getNumero());


        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar a turma. Descricao " +
                        ex.getMessage());
        }


       return (Turma)q.list().get(0);

    }
    
  
    
}

    

