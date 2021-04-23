package model.dao;

import java.util.List;

import model.entities.Departamento;
import model.entities.Secao;

public interface SecaoDao {
	void insert  (Secao obj);
	void udpdate (Secao obj);
	void deleteById (Integer id);
	Secao findByid(Integer id);
	
	List<Secao> findAll();
}
