package model.dao;

import java.util.List;

import model.entities.Departamento;

public interface DepartamentoDao {
	
	
	void insert  (Departamento obj);
	void udpdate (Departamento obj);
	void deleteById (Integer id);
	Departamento findByid(Integer id);
	
	List<Departamento> findAll();
	

}
