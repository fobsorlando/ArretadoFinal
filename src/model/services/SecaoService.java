package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SecaoDao;
import model.entities.Secao;

public class SecaoService {
	
	private SecaoDao dao = DaoFactory.createSecaoDao();

	public List<Secao> findAll() {
		
		return dao.findAll();
		
	}
	
	public void saveOrUpdate(Secao obj) {
		if (obj.getId() == null ) {
			dao.insert(obj);
		}
		else {
			dao.udpdate(obj);
		}
	}

	public void remove(Secao obj) {
		dao.deleteById(obj.getId());
	}
}
