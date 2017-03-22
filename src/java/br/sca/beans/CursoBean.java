package br.sca.beans;

import br.sca.controller.CtrCadastroCurso;


import br.sca.model.Curso;
import br.sca.report.UsandoCursoDataSource;
import br.sca.utils.ExcecaoSCA;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.BatchUpdateException;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.hibernate.exception.ConstraintViolationException;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

@ViewScoped
@ManagedBean
public class CursoBean implements Serializable{

	private Curso curso = new Curso();
        
        private boolean velho = false;

        private List<Curso> cursos;

        String erro="";

        ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-hibernate.xml");

        CtrCadastroCurso ctrCadastroCurso = (CtrCadastroCurso)fabrica.getBean("ctrCadastroCurso");
	
	public Curso getCurso() {
		return this.curso;
	}

        public boolean getVelho() {
		return this.velho;
	}

        public String getErro() {
		return this.erro;
	}

        public void setCurso(Curso curso) {
                erro = "";
		this.curso = curso;
	}

        public void setVelho(boolean velho) {
		this.velho = velho;
	}

	public void gravar(){
            erro="";
            try {
                //System.out.println("Gravando!!!!!!!!!!!!!!!");
                if (!velho)
                {
                    if ((curso.getCodigo() != null) && (curso.getCodigo()!=0))
                    {
                        ctrCadastroCurso.incluir(curso);
                    }
                    else
                    {
                        erro="Código do Curso não informado";
                    }
                }
                else
                {
                    ctrCadastroCurso.alterar(curso);
                    velho = false;
                }
                //System.out.println("Gravou!!!!!!!!!!!!!!!");

                this.curso = new Curso();
                cursos = ctrCadastroCurso.listarTodos(0);
                
            } catch (ExcecaoSCA ex) {
               erro = ex.getMsg();
            }

            //return "principal?faces-redirect=true";
	}

        public void limpar(){

                this.curso = new Curso();
                velho = false;
                erro="";

	}

        public StreamedContent getImprimir(){

                
                System.out.println(System.getProperty("user.dir"));
                velho = false;
                erro="";
                InputStream relatorio = null;

                try {



                   HashMap<String, Integer> parameterMap = null;

		   String arquivo = "C:/Users/Paulo/Documents/Servidor/SCAWEBSpring/build/web/WEB-INF/classes/br/sca/report/RelatorioCursoMVC.jasper";
                                                     
                  // String arquivo = "/home/paulomsf/appservers/apache-tomcat-7x/webapps/SCAWEBSpring/WEB-INF/classes/br/sca/report/RelatorioCursoMVC.jasper";
                                                     
                   
                   ByteArrayOutputStream barray = new ByteArrayOutputStream();

                    //chama fillReport
		   
                   JasperPrint jp = JasperFillManager.fillReport(arquivo,
		 			parameterMap, UsandoCursoDataSource.test());

                    //JasperViewer.viewReport(jp, false);

                    
                    JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();

                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, barray);

                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);

                    exporter.exportReport();

                    
                    relatorio = new ByteArrayInputStream(barray.toByteArray());


                   }catch (ExcecaoSCA ex){
                        erro = ex.getMsg();
                   } catch (JRException e) {
                        erro = "Erro ao gerar relatório. Descrição: " + e.getMessage();
		   }
                 
                return new DefaultStreamedContent(relatorio, "pdf", "cursos.pdf");

	}


        public void excluir(){
            try {
                erro="";

                if ((curso.getCodigo() != null) && (curso.getCodigo()!=0))
                {   System.out.println("Bean1*************");
                    ctrCadastroCurso.excluir(curso);
                    System.out.println("Bean2*************");
                }
                else
                {
                    erro="Código do Curso não informado";
                }

                velho = false;
                cursos = ctrCadastroCurso.listarTodos(0);
                this.curso= new Curso();
            } catch (ExcecaoSCA ex) {
                
                System.out.println("EroBeanSCA*************");
                erro = ex.getMsg();
                
            }
            catch (DataIntegrityViolationException ex) {

                System.out.println("EroBeanlalala*************");
                erro = ex.getMessage();

            }
	}

        @PostConstruct
        public void carregaCursos(){
            erro="";
            try {
                cursos = ctrCadastroCurso.listarTodos(0);

            } catch (ExcecaoSCA ex) {
                erro = ex.getMsg();
            }
	}

        public List<Curso> getCursos(){
            return cursos;
        }
	
}
