package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SubGrupoDao;
import model.entities.SubGrupo;

public class SubGrupoService {
	
	private SubGrupoDao dao = DaoFactory.createSubGrupoDao();

	public List<SubGrupo> findAll() {
		
		return dao.findAll();
		
	}
	
	public void saveOrUpdate(SubGrupo obj) {
		if (obj.getId() == null ) {
			dao.insert(obj);
		}
		else {
			dao.udpdate(obj);
		}
	}

	public void remove(SubGrupo obj) {
		dao.deleteById(obj.getId());
	}
}
