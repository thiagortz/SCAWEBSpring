/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.controller;

import br.sca.utils.ExcecaoSCA;
import br.sca.dao.*;
import br.sca.model.*;
//import br.sca.utils.HibernateUtil;
import br.sca.utils.TrataData;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.hibernate.classic.Session;

/**
 *
 * @author Usuario
 */
public class CtrCadastroTurma {
    private static CtrCadastroTurma umCtrCadastroTurma;
    TurmaDAO turmaDAO;
    String anoSemestreAtivo;
    Session session;
    
    private CtrCadastroTurma(){
       DAOFactory daoFactory = new DAOFactory();
       turmaDAO = daoFactory.getTurmaDAO();
       ResourceBundle prop = ResourceBundle.getBundle("br.sca.utils.anoSemestreAtivo");
       anoSemestreAtivo = prop.getString("anoSemestre");
     //  session = HibernateUtil.getSession();
    
    }
    
    public static CtrCadastroTurma getInstance(){
        if (umCtrCadastroTurma == null){
            umCtrCadastroTurma = new CtrCadastroTurma();
        }
        return umCtrCadastroTurma;
    }

    public List <Turma> listarTodos(int ordenacao) throws ExcecaoSCA{
        List <Turma>lista = new ArrayList <Turma>();
        lista = turmaDAO.pegarTodos(session,ordenacao);
        return lista;
    }
    
    private boolean validar(Turma turma) throws ExcecaoSCA {
                       
            if (turma.getAnoSemestre()== null || turma.getAnoSemestre().trim().equals("")) {
                throw new ExcecaoSCA("O ano e o semestre da turma são obrigatórios.") ;
            }
            if (TrataData.AnoSemestreInvalido(turma.getAnoSemestre())){
                throw new ExcecaoSCA("O ano e o semestre da turma são inválidos");
            }
            if (!turma.getAnoSemestre().equals(anoSemestreAtivo))
            {
                 throw new ExcecaoSCA("A turma não é do Ano semestre ativo.") ;  
            }
            if (turma.getDisciplina().getCodigo() < 0) {
                throw new ExcecaoSCA("Disciplina da turma inválida.") ;
            }
            if (turma.getProfessor().getMatricula() < 0) {
                throw new ExcecaoSCA("Professor da turma inválido.") ;
            }

            
            
            return true ;
    }

    public void incluir (Turma turma) throws ExcecaoSCA {
       List <Turma>listaTurma = new ArrayList <Turma>();
        try {
            if (validar(turma)){
                listaTurma = turmaDAO.pegarTodosPorAnoSemestreDisciplina(turma.getAnoSemestre(),turma.getDisciplina(),session);
                if (!listaTurma.isEmpty()){
                    throw new ExcecaoSCA("Já existe turma para esta disciplina neste ano semestre.") ; 
                }
                turmaDAO.incluir(turma,session);
            }

              
        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }
        
    }
    
    public void alterar (Turma turma) throws ExcecaoSCA {
        try {
            if (validar(turma))
                turmaDAO.alterar(turma,session);
        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }
        
    }

        public void excluir (Turma turma) throws ExcecaoSCA {
        try {
            turmaDAO.excluir(turma,session);
        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }
        
    }

    public Turma carregar(Turma turma) throws ExcecaoSCA {
        try {
            turma = turmaDAO.carregar(turma,session);
        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }
        return turma;
    }

    

}