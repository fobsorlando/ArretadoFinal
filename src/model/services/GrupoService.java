package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.GrupoDao;
import model.entities.Grupo;

public class GrupoService {
	
	private GrupoDao dao = DaoFactory.createGrupoDao();

	public List<Grupo> findAll() {
		
		return dao.findAll();
		
	}
	
	public void saveOrUpdate(Grupo obj) {
		if (obj.getId() == null ) {
			dao.insert(obj);
		}
		else {
			dao.udpdate(obj);
		}
	}

	public void remove(Grupo obj) {
		dao.deleteById(obj.getId());
	}
}
