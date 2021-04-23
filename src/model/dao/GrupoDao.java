package model.dao;

import java.util.List;

import model.entities.Grupo;

public interface GrupoDao {
	void insert  (Grupo obj);
	void udpdate (Grupo obj);
	void deleteById (Integer id);
	Grupo findByid(Integer id);
	
	List<Grupo> findAll();
}
