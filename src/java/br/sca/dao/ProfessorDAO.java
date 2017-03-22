/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.dao;

import br.sca.utils.ExcecaoSCA;
import br.sca.model.Professor;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Usuario
 */
public class ProfessorDAO{

       
    public List<Professor> pegarTodos(Session s, int ordenacao) throws ExcecaoSCA {
        Query q;
        String ordem;

        if (ordenacao == 0){
            ordem = "matricula";
        }
        else {
            ordem = "nome";
        }

        try {

           String hql = "from Professor order by " + ordem;
           q = s.createQuery(hql);

        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao listar os professores. Descricao " +
                        ex.getMessage());
        }
        return q.list();
    }
    

    
    
    public void incluir(Professor professor, Session s) throws ExcecaoSCA {
        try {
            Transaction t = s.beginTransaction();

	    s.save(professor);

	    t.commit();
        } catch (Exception ex) {

                throw new ExcecaoSCA("Erro na inclusão do professor. Descricao " +
                        ex.getMessage());
        }

    }
    
    public void alterar(Professor professor, Session s) throws ExcecaoSCA {
            try {
            Transaction t = s.beginTransaction();

	    s.update(professor);

	    t.commit();
        } catch (Exception ex) {

                throw new ExcecaoSCA("Erro na alteração do professor. Descricao " +
                        ex.getMessage());
        }
    }
    
    public void excluir(Professor professor, Session s) throws ExcecaoSCA {
            try {
                Transaction t = s.beginTransaction();

                Query q;

                String hql = "from Professor a where a.matricula = :busca";
                q = s.createQuery(hql);
                q.setParameter("busca",professor.getMatricula());
                professor = (Professor) q.list().get(0);

                s.delete(professor);

                t.commit();
            } catch (Exception ex) {

                throw new ExcecaoSCA("Erro na exclusão do professor. Descricao " +
                        ex.getMessage());
            }
    }

    public Professor carregar(Professor umProfessor, Session s) throws ExcecaoSCA {

       Query q;

        try {

           String hql = "from Professor p where p.matricula = :busca";
	   q = s.createQuery(hql);
	   q.setParameter("busca",umProfessor.getMatricula());


        } catch (Exception ex) {
            throw new ExcecaoSCA("Erro ao carregar o professor. Descricao " +
                        ex.getMessage());
        }
      

       return (Professor)q.list().get(0);



    }
    
  
    
}

    

