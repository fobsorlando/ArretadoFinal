package model.dao;

import java.util.List;

import model.entities.Cliente;

public interface ClienteDao {
	
	
	void insert  (Cliente obj);
	void udpdate (Cliente obj);
	void deleteById (Integer id);
	Cliente findByid(Integer id);
	
	List<Cliente> findAll();
	

}
