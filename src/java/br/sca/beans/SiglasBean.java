package br.sca.beans;


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean
public class SiglasBean implements Serializable{

	private List<String> siglasEstado;
        private List<String> siglasSexo;

        @PostConstruct
        public void carregaSiglas(){

             String[] estados = {"AC","AL","AM","AP","BA","CE","DF","ES","GO","MA","MG","MS","MT","PA","PB","PE","PI","PR","RJ","RN","RO","RR","RS","SC","SE","SP","TO"};
             siglasEstado = Arrays.asList(estados);

             String[] sexos = {"M","F"};
             siglasSexo = Arrays.asList(sexos);

	}

        public List<String> getSiglasEstado(){
            return siglasEstado;
        }

        public List<String> getSiglasSexo(){
            return siglasSexo;
        }
}
