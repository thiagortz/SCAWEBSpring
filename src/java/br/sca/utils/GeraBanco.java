package br.sca.utils;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class GeraBanco {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AnnotationConfiguration configuration = new AnnotationConfiguration();
		
		configuration.configure();
		
		SchemaExport se = new SchemaExport(configuration);
		
		se.create(true,true);
		
		
		

	}

}
