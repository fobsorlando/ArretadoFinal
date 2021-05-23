package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.UnidadeDao;
import model.entities.Secao;
import model.entities.Unidade;

public class UnidadeService {
	
	private UnidadeDao dao = DaoFactory.createUnidadeDao();
	
	public Unidade findByid(String id) {
		return dao.findByid(id);
	}
	
	public List<Unidade> findAll() {
		
		return dao.findAll();
		
	}
	
	public void saveOrUpdate(Unidade obj) {

		Unidade id = dao.findByid(obj.getSg_unidade());
		if (id == null ) {
			dao.insert(obj);
		}
		else {
			dao.udpdate(obj);
		}
	}
	public void save(Unidade obj) {
		dao.insert(obj);
	}
	
	public void update(Unidade obj) {
		dao.udpdate(obj);
	}
	
	public void remove(Unidade obj) {
		dao.deleteById(obj.getSg_unidade());
	}

}
