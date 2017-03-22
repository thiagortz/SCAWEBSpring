package br.sca.beans;

import br.sca.controller.CtrCadastroUsuario;
import br.sca.model.Usuario;
import br.sca.utils.ExcecaoSCA;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@SessionScoped
@ManagedBean
public class LoginBean implements Serializable{

       private Usuario usuario = new Usuario();

       private boolean usuarioLogado=false;

       ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-hibernate.xml");

       CtrCadastroUsuario ctrCadastroUsuario = (CtrCadastroUsuario)fabrica.getBean("ctrCadastroUsuario");

	
	public Usuario getUsuario() {
		return this.usuario;
	}

        public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

       
        public String efetuaLogin(){
            boolean loginValido=false;
            
            try {
                loginValido = ctrCadastroUsuario.existe(this.usuario);


            } catch (ExcecaoSCA ex) {
                //Logger.getLogger(CursoBean.class.getName()).log(Level.SEVERE, null, ex);
            }

             if (loginValido){
                usuarioLogado = true;
                return "principal?faces-redirect=true";
             }
             else{
                usuarioLogado = false;
                return "index?faces-redirect=true";
             }
            
	}

        public boolean isUsuarioLogado(){
            return usuarioLogado;
        }

        
	
}
