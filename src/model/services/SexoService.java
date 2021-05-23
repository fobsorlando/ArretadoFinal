package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SexoDao;
import model.entities.Sexo;

public class SexoService {
	
	private SexoDao dao = DaoFactory.createSexoDao();

	public List<Sexo> findAll() {
		
		return dao.findAll();
		
	}

}
