package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.UFDao;
import model.entities.UF;

public class UFService {
	
	private UFDao dao = DaoFactory.createUFDao();

	public List<UF> findAll() {
		
		return dao.findAll();
		
	}

}
