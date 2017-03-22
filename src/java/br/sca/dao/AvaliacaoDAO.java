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
public class AvaliacaoDAO{
    
    public List<Avaliacao> pegarTodos(Session s, int ordenacao) throws ExcecaoSCA {
        Query q;

        String ordem;

        if (ordenacao == 0) {
          ordem = "turma_numero";
        } else {
          ordem = "aluno_codigo";
        }

        try {

           String hql = "from Avaliacao order by " + ordem;
           q = s.createQuery(hql);

        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao listar as avaliações. Descricao " +
                        ex.getMessage());
        }
        return q.list();
    }
    
    public List<Avaliacao> pegarTodosPorAnoSemestre(String anoSemestre, Session s, int ordenacao) throws ExcecaoSCA {
        Query q;

        String ordem;

        if (ordenacao == 0) {
          ordem = "turma_numero";
        } else {
          ordem = "aluno_codigo";
        }

        try {

           String hql = "from Avaliacao a where a.turma.anoSemestre = :busca order by " + ordem;
	   q = s.createQuery(hql);
	   q.setParameter("busca",anoSemestre);

        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar a avaliação por ano semestre. Descricao " +
                        ex.getMessage());
        }


       return q.list();
         
    }

    
    
    public List<Avaliacao> pegarTodosPorAluno(Aluno umAluno, Session s, int ordenacao) throws ExcecaoSCA {
        Query q;
        
        String ordem;

        if (ordenacao == 0) {
          ordem = "turma_numero";
        } else {
          ordem = "aluno_codigo";
        }
       
        

        try {

           String hql = "from Avaliacao a where a.aluno.matricula = :busca order by " + ordem;
	   q = s.createQuery(hql);
	   q.setParameter("busca",umAluno.getMatricula());

        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar a avaliação por aluno. Descricao " +
                        ex.getMessage());
        }


       return q.list();
    }
    

    public List<Disciplina> pegarTodasSemResultadoPorAluno(Aluno umAluno, Session s) throws ExcecaoSCA {
        Query q;

        try {

           String hql = "from Avaliacao a where a.aluno.matricula = :busca and a.resultado is null";
	   q = s.createQuery(hql);
	   q.setParameter("busca",umAluno.getMatricula());

        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar a avaliação sem resultado. Descricao " +
                        ex.getMessage());
        }


       return q.list();
              
    }
    
    
    public List<Disciplina> pegarTodasComReprovacaoPorAluno(Aluno umAluno, Session s) throws ExcecaoSCA {
        
        Query q;

        try {

           String hql = "select distinct a1.turma.disciplina from Avaliacao a1 where a1.aluno.matricula = :busca and a1.resultado = 'r'"
              + " and a1.aluno.matricula not in (select a2.aluno.matricula from Avaliacao a2 where a2.resultado = 'a' and"
              + " a2.turma.disciplina.codigo = a1.turma.disciplina.codigo)";
	   q = s.createQuery(hql);
	   q.setParameter("busca",umAluno.getMatricula());

        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar a avaliação com reprovação por aluno. Descricao " +
                        ex.getMessage());
        }


       return q.list();
     
    }


    
    
    public int pegarUltimoPeriodoDoAluno(Aluno umAluno, Session s) throws ExcecaoSCA {

        Query q;

        try {

           String hql = "select max(a.turma.disciplina.periodo) from Avaliacao a where a.aluno.matricula = :busca and a.resultado is not null";
	   q = s.createQuery(hql);
           q.setParameter("busca",umAluno.getMatricula());


        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar o último período do aluno(avaliação). Descricao " +
                        ex.getMessage());
        }


       return ((Integer)q.list().get(0)).intValue();

       
    }
    
    public List<Avaliacao> pegarTodosPorTurma(Turma umaTurma, Session s,int ordenacao) throws ExcecaoSCA {
        Query q;
        String ordem;

        if (ordenacao == 0) {
          ordem = "turma_numero";
        } else {
          ordem = "aluno_codigo";
        }

        try {

           String hql = "from Avaliacao a where a.turma.numero = :busca order by " + ordem;
	   q = s.createQuery(hql);
	   q.setParameter("busca",umaTurma.getNumero());

        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar a avaliação por turma. Descricao " +
                        ex.getMessage());
        }


       return q.list();
              
    }
    
    public void incluir(Avaliacao avaliacao, Session s) throws ExcecaoSCA {
      try {
            Transaction t = s.beginTransaction();

	    s.save(avaliacao);

	    t.commit();
        } catch (Exception ex) {

                throw new ExcecaoSCA("Erro na inclusão da avaliação. Descricao " +
                        ex.getMessage());
        }

    }
    
    public void alterar(Avaliacao avaliacao, Session s) throws ExcecaoSCA {
       try {
            Transaction t = s.beginTransaction();

	    s.update(avaliacao);

	    t.commit();
        } catch (Exception ex) {

                throw new ExcecaoSCA("Erro na alteração da avaliação. Descricao " +
                        ex.getMessage());
        }
    }
    
    public void excluir(Avaliacao avaliacao, Session s) throws ExcecaoSCA {
        Query q;
        try {
           Transaction t = s.beginTransaction();
           String hql = "from Avaliacao a where a.aluno.matricula = :busca1 and a.turma.numero = :busca2";
	   q = s.createQuery(hql);
	   q.setParameter("busca1",avaliacao.getAluno().getMatricula());
           q.setParameter("busca2",avaliacao.getTurma().getNumero());

           avaliacao = (Avaliacao)q.list().get(0);

	   s.delete(avaliacao);

	   t.commit();
        } catch (Exception ex) {

                throw new ExcecaoSCA("Erro na exclusão da avaliação. Descricao " +
                        ex.getMessage());
        }
    }

    public Avaliacao carregar(Avaliacao avaliacao, Session s) throws ExcecaoSCA {

        
       Query q;

        try {

           String hql = "from Avaliacao a where a.aluno.matricula = :busca1 and a.turma.numero = :busca2";
	   q = s.createQuery(hql);
	   q.setParameter("busca1",avaliacao.getAluno().getMatricula());
           q.setParameter("busca2",avaliacao.getTurma().getNumero());
           
        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar a avaliação. Descricao " +
                        ex.getMessage());
        }


       return (Avaliacao)q.list().get(0);


    }
    
  
    
}

    

