/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.controller;

import br.sca.utils.ExcecaoSCA;
import br.sca.dao.*;
import br.sca.model.*;
//import br.sca.utils.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.hibernate.classic.Session;

/**
 *
 * @author Usuario
 */
public class CtrCadastroMatricula {
    private static CtrCadastroMatricula umCtrCadastroMatricula;
    AvaliacaoDAO avaliacaoDAO;
    TurmaDAO turmaDAO;
    DisciplinaDAO disciplinaDAO;
    String anoSemestreAtivo;
    Session session;
    
    private CtrCadastroMatricula(){
       DAOFactory daoFactory = new DAOFactory();
       avaliacaoDAO = daoFactory.getAvaliacaoDAO();
       turmaDAO = daoFactory.getTurmaDAO();
       disciplinaDAO = daoFactory.getDisciplinaDAO();
       ResourceBundle prop = ResourceBundle.getBundle("br.sca.utils.anoSemestreAtivo");
       anoSemestreAtivo = prop.getString("anoSemestre");
  //     session = HibernateUtil.getSession();
       
    
    }
    
    public static CtrCadastroMatricula getInstance(){
        if (umCtrCadastroMatricula == null){
            umCtrCadastroMatricula = new CtrCadastroMatricula();
        }
        return umCtrCadastroMatricula;
    }

    public List <Avaliacao> listarTodos(int ordenacao) throws ExcecaoSCA{
        List <Avaliacao>lista = new ArrayList <Avaliacao>();
        lista = avaliacaoDAO.pegarTodos(session,ordenacao);
        return lista;
    }
    
    public List <Avaliacao> listarTodosPorAnoSemestre(String anoSemestre, int ordenacao) throws ExcecaoSCA{
        List <Avaliacao>lista = new ArrayList <Avaliacao>();
        lista = avaliacaoDAO.pegarTodosPorAnoSemestre(anoSemestre,session,ordenacao);
        return lista;
    }
    public List <Avaliacao> listarTodosPorAluno(Aluno umAluno, int operacao) throws ExcecaoSCA{
        List <Avaliacao>lista = new ArrayList <Avaliacao>();
        lista = avaliacaoDAO.pegarTodosPorAluno(umAluno,session,operacao);
        return lista;
    }
    
    public List <Avaliacao> listarTodosPorTurma(Turma umaTurma, int ordenacao) throws ExcecaoSCA{
        List <Avaliacao>lista = new ArrayList <Avaliacao>();
        lista = avaliacaoDAO.pegarTodosPorTurma(umaTurma,session,ordenacao);
        return lista;
    }
    
    public List <Turma> listarTurmasPorAluno(String anoSemestre,Aluno umAluno) throws ExcecaoSCA{
       List <Turma>listaTurma = new ArrayList <Turma>();
       List <Turma>listaTurmaFinal = new ArrayList <Turma>();
       List <Avaliacao>listaAvaliacao = new ArrayList <Avaliacao>();
       List <Disciplina>listaDisciplinaSemResultado = new ArrayList <Disciplina>();
              
       listaAvaliacao = avaliacaoDAO.pegarTodosPorAluno(umAluno,session,0);
       listaTurma = turmaDAO.pegarTodosPorAnoSemestreCurso(anoSemestre,umAluno.getCurso(),session);

       if (listaTurma.isEmpty())
       {
          throw new ExcecaoSCA("Não existem turmas para este curso neste ano semestre.") ; 
       }
       
       try {
           
        if (listaAvaliacao.isEmpty())
        {
          
          obterTurmasDeDisciplinasDoPeriodo(umAluno,listaTurma,1,listaTurmaFinal);  
        }
        else
        {
          listaDisciplinaSemResultado = avaliacaoDAO.pegarTodasSemResultadoPorAluno(umAluno,session);
          
          if (!listaDisciplinaSemResultado.isEmpty())
          {
               throw new ExcecaoSCA("Este aluno possui avaliações em aberto.") ;
          }
          
          listaTurmaFinal = obterTurmasDeDisciplinasComReprovacao(umAluno,listaTurma);  

          
          if (listaTurmaFinal.size() <= 2)
          {
             int ultimoPeriodo = avaliacaoDAO.pegarUltimoPeriodoDoAluno(umAluno,session);
             int periodo = ++ultimoPeriodo;
             obterTurmasDeDisciplinasDoPeriodo(umAluno,listaTurma,periodo,listaTurmaFinal);    
          }    
                     
        }
       }catch (ExcecaoSCA ex) {
            throw ex;
       } 
       
        return listaTurmaFinal;
    }
       
    
    private List<Turma> obterTurmasDeDisciplinasComReprovacao(Aluno umAluno,List<Turma> listaTurma) throws ExcecaoSCA
    {
        boolean achou;
        List <Disciplina>listaDisciplinaComReprovacao = new ArrayList <Disciplina>();
        List <Turma>listaTurmaFinal = new ArrayList <Turma>();
        
        try {
            listaDisciplinaComReprovacao = avaliacaoDAO.pegarTodasComReprovacaoPorAluno(umAluno,session);
       
            if (!listaDisciplinaComReprovacao.isEmpty()) {
                
                for (Disciplina disciplina : listaDisciplinaComReprovacao) {
                    achou = false;

                    for (Turma turma : listaTurma) {
                        if (disciplina.getCodigo() == turma.getDisciplina().getCodigo()) {
                            listaTurmaFinal.add(turma);
                            achou = true;
                        }
                    }
                    if (!achou)
                    {
                      throw new ExcecaoSCA("Aluno com dependência em disciplina sem turma no semestre ano ativo") ;
                    }
                   
                }
            }
  
         } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
         }

        return listaTurmaFinal;
    }                                        
    
    private void obterTurmasDeDisciplinasDoPeriodo(Aluno umAluno,List<Turma> listaTurma,int periodo,List<Turma> listaTurmaFinal) throws ExcecaoSCA
    {
         
         
        try {
            
            List<Disciplina> listaDisciplinaPeriodo = new ArrayList <Disciplina>();
            
            listaDisciplinaPeriodo = disciplinaDAO.pegarDisciplinasDoPeriodoPorCurso(periodo, umAluno.getCurso(),session);
            
            if (!listaDisciplinaPeriodo.isEmpty()) {
               
                for (Disciplina disciplina : listaDisciplinaPeriodo) {
                  
                    for (Turma turma : listaTurma) {
                        if (disciplina.getCodigo() == turma.getDisciplina().getCodigo()) {
                            listaTurmaFinal.add(turma);
                            
                        }
                    }
                   
                }
            }
        } catch (ExcecaoSCA ex) {
              throw new ExcecaoSCA("Erro ao pegar disciplinas do período do aluno.") ;
        }
    }                                        
    
    
    private boolean validar(Avaliacao avaliacao) throws ExcecaoSCA {        
       
            
            if (avaliacao.getAluno() == null) {
                throw new ExcecaoSCA("O Aluno da matrícula é obrigatório.") ;
            }
            if (avaliacao.getTurma() == null) {
                throw new ExcecaoSCA("A Turma da matrícula é obrigatória.") ;
            }
            if (!avaliacao.getTurma().getAnoSemestre().equals(anoSemestreAtivo))
            {
                 throw new ExcecaoSCA("A turma não é do Ano semestre ativo.") ;  
            }
                
            
       return true ;
    }

    public void incluir (Avaliacao avaliacao) throws ExcecaoSCA {
        try {
            if (validar(avaliacao))
              avaliacaoDAO.incluir(avaliacao,session);
        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }
        
    }

    public void excluir (Avaliacao avaliacao) throws ExcecaoSCA {
        try {

            avaliacao = avaliacaoDAO.carregar(avaliacao,session);

            if (avaliacao.getResultado() != null) {
                throw new ExcecaoSCA("O Aluno já possui resultado nesta turma.") ;
            }
            else
            {
              if (!avaliacao.getTurma().getAnoSemestre().equals(anoSemestreAtivo))
              {
                 throw new ExcecaoSCA("A turma não é do Ano semestre ativo.") ;  
              }
              else
              {
                 avaliacaoDAO.excluir(avaliacao,session);
              }
            }    
            
        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }
        
    }

    public Avaliacao carregar(Avaliacao avaliacao) throws ExcecaoSCA {
        try {
            avaliacao = avaliacaoDAO.carregar(avaliacao,session);
        } catch (ExcecaoSCA ex)  {
            throw new ExcecaoSCA(ex.getMsg());
        }

        return avaliacao;
    }

   
    
    

}