package br.sca.beans;

import br.sca.controller.CtrCadastroAluno;
import br.sca.controller.CtrCadastroCurso;
import br.sca.model.Aluno;
import br.sca.model.Curso;
import br.sca.utils.ExcecaoSCA;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SessionScoped
@ManagedBean
public class AlunoBean implements Serializable{

	private Aluno aluno = new Aluno();
        private boolean velho = false;

        private List<Aluno> alunos;

        Integer codigoCurso;

        String erro="";

        ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-hibernate.xml");

        CtrCadastroAluno ctrCadastroAluno = (CtrCadastroAluno)fabrica.getBean("ctrCadastroAluno");

        CtrCadastroCurso ctrCadastroCurso = (CtrCadastroCurso)fabrica.getBean("ctrCadastroCurso");
	
	public Aluno getAluno() {
                ResourceBundle prop = ResourceBundle.getBundle("br.sca.utils.anoSemestreAtivo");
                String anoSemestreAtivo = prop.getString("anoSemestre");
                aluno.setAnoSemestreInicio(anoSemestreAtivo);
		return this.aluno;
	}

        public boolean getVelho() {
		return this.velho;
	}

        public String getErro() {
		return this.erro;
	}

        public void setAluno(Aluno aluno) {
                erro = "";
		this.aluno = aluno;
                codigoCurso = aluno.getCurso().getCodigo();
                
	}

        public void setVelho(boolean velho) {
		this.velho = velho;
	}

        public Integer getCodigoCurso() {
		return this.codigoCurso;
	}

        public void setCodigoCurso(Integer codigoCurso) {
		this.codigoCurso = codigoCurso;
	}

	public void gravar(){
            erro="";

            try {

                Curso curso = new Curso();
                curso.setCodigo(codigoCurso);
                curso = ctrCadastroCurso.carregar(curso);
                aluno.setCurso(curso);

                if (!velho)
                {
                    if (aluno.getMatricula() != null)
                    {
                        ctrCadastroAluno.incluir(aluno);
                    }
                    else
                    {
                        erro="Matrícula do Aluno não informada";
                    }
                }
                else
                {
                    ctrCadastroAluno.alterar(aluno);
                    velho = false;
                }
                //System.out.println("Gravou!!!!!!!!!!!!!!!");

                this.aluno = new Aluno();
                alunos = ctrCadastroAluno.listarTodos(0);
                
            } catch (ExcecaoSCA ex) {
               erro = ex.getMsg();
            }

            //return "principal?faces-redirect=true";
	}

        public void limpar(){

                this.aluno = new Aluno();
                velho = false;
                erro="";

	}

        public void excluir(){
            
            try {
                erro = "";
                if (aluno.getMatricula() != null)
                {
                   ctrCadastroAluno.excluir(aluno);
                }
                else
                {
                        erro="Matrícula do Aluno não informada";
                }
                velho = false;
                this.aluno = new Aluno();
                alunos = ctrCadastroAluno.listarTodos(0);
            } catch (ExcecaoSCA ex) {
                erro = ex.getMsg();
            }
	}

        @PostConstruct
        public void carregaAlunos(){
            erro="";
            try {
                alunos = ctrCadastroAluno.listarTodos(0);

            } catch (ExcecaoSCA ex) {
                erro = ex.getMsg();
            }
	}

        public List<Aluno> getAlunos(){
            return alunos;
        }
	
}
