package model.dao;

import java.util.List;

import model.entities.Venda;

public interface VendaDao {
	
	
	void insert  (Venda obj);
	void udpdate (Venda obj);
//	void deleteById (Integer id);
	Venda findByid(Integer id);
	
	
	List<Venda> findAll();
//	List<Venda> findByNome(String no_Venda);

	

}
