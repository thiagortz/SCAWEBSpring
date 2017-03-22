/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.report;

import br.sca.model.Curso;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author aluno
 */
public class CursoDataSource implements JRDataSource{
    
    private List data;
    private int index = -1;

    public CursoDataSource(List data) {
        this.data = data;
    }

    public boolean next() throws JRException {
       index++;
       
       return (index < getData().size());
    }

    public Object getFieldValue(JRField field) throws JRException {
       Object value = null;
       
       String fieldName = field.getName();
       
       if("codigo".equals(fieldName))
       {
         value = ((Curso)getData().get(index)).getCodigo();
       }
       else
       {
           if("descricao".equals(fieldName))
           {
             value = ((Curso)getData().get(index)).getDescricao();
           }
           else
           {
               if("cargaHoraria".equals(fieldName))
               {
                  value = ((Curso)getData().get(index)).getCargaHoraria();
               }
           }
       }
       
       return value;
    }

    private List getData() {
       return data;
    }

}
