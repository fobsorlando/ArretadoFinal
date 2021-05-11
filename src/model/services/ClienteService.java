package model.services;

import java.util.List;

import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.entities.Cliente;

public class ClienteService {
	
	private ClienteDao dao = DaoFactory.createClienteDao();

	public List<Cliente> findAll() {
		
		return dao.findAll();
		
	}
	
	public List<Cliente> findByNome(String no_cliente) {
		
		return dao.findByNome(no_cliente);
		
	}
	
	public void saveOrUpdate(Cliente obj) {
		if (obj.getId() == null ) {
			dao.insert(obj);
		}
		else {
			dao.udpdate(obj);
		}
	}

	public void remove(Cliente obj) {
		dao.deleteById(obj.getId());
	}
}
