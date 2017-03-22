/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.report;

import br.sca.controller.CtrCadastroCurso;
import br.sca.utils.ExcecaoSCA;
import br.sca.model.Curso;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author aluno
 */
public class UsandoCursoDataSource {
  
    static List<Curso> cursos;
    
    static ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-hibernate.xml");

    static CtrCadastroCurso ctrCadastroCurso = (CtrCadastroCurso)fabrica.getBean("ctrCadastroCurso");

    public static JRDataSource test() throws ExcecaoSCA
    {
        cursos = ctrCadastroCurso.listarTodos(2);
        return new CursoDataSource(cursos);
    }   

}
