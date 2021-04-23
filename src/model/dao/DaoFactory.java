package model.dao;

import db.DB;
import model.dao.impl.DepartamentoDaoJDBC;
import model.dao.impl.GrupoDaoJDBC;
import model.dao.impl.ProdutoDaoJDBC;
import model.dao.impl.SecaoDaoJDBC;
import model.dao.impl.SubGrupoDaoJDBC;

public class DaoFactory {
	
	public static ProdutoDao createProdutoDao() {
		
		return new ProdutoDaoJDBC(DB.getConnection());
	}
	
	public static DepartamentoDao createDepartamentoDao() {
		
		return new DepartamentoDaoJDBC(DB.getConnection());
	}
	
	public static GrupoDao createGrupoDao() {
		
		return new GrupoDaoJDBC(DB.getConnection());
	}
	public static SubGrupoDao createSubGrupoDao() {
		
		return new SubGrupoDaoJDBC(DB.getConnection());
	}
	
	public static SecaoDao createSecaoDao() {
		
		return new SecaoDaoJDBC(DB.getConnection());
	}

}
