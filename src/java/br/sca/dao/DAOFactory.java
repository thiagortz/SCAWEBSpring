package br.sca.dao;



public class DAOFactory
{	

	
	// Aqui entram as implementa��es dos m�todos abstratos 
	// especificados em DAOFactory.
	
        public static UsuarioDAO getUsuarioDAO()
	{   
            return new UsuarioDAO();
	}

        public static CursoDAO getCursoDAO()
	{	return new CursoDAO();
	}

        public static DisciplinaDAO getDisciplinaDAO()
	{	return new DisciplinaDAO();
	}
	
        public static AlunoDAO getAlunoDAO()
	{	return new AlunoDAO();
	}
	
        public static ProfessorDAO getProfessorDAO()
	{	return new ProfessorDAO();
	}
        
        public static TurmaDAO getTurmaDAO()
	{	return new TurmaDAO();
	}
        
        public static AvaliacaoDAO getAvaliacaoDAO()
	{	return new AvaliacaoDAO();
	}
        
}
