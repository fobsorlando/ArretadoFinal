package model.dao;

import java.util.List;

import model.entities.Unidade;

public interface UnidadeDao {

	void insert  (Unidade obj);
	void udpdate (Unidade obj);
	void deleteById (String id);
	Unidade findByid(String id);
	
	List<Unidade> findAll();
}
