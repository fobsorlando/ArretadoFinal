package model.services;

import java.util.List;

import model.dao.VendaDao;
import model.dao.DaoFactory;
import model.entities.Venda;

public class VendaService {
	
	private VendaDao dao = DaoFactory.createVendaDao();

	public List<Venda> findAll() {
		
		return dao.findAll();
		
	}
	/*
	public List<Venda> findByNome(String no_Venda) {
		
		return dao.findByNome(no_Venda);
		
	}
	*/
	
	public void saveOrUpdate(Venda obj) {
		if (obj.getId_venda() == null ) {
			dao.insert(obj);
		}
		else {
			dao.udpdate(obj);
		}
	}

	/*
	public void remove(Venda obj) {
		dao.deleteById(obj.getId());
	}
	*/
}
