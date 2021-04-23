package model.dao;

import java.util.List;

import model.entities.Produto;
import model.entities.Departamento; 

public interface ProdutoDao {

	void insert  (Produto obj);
	void udpdate (Produto obj);
	void deleteById (Integer id);
	Produto findByid(Integer id);
	
	List<Produto> findAll();
	List<Produto> findByDepartamento(Departamento departamento);
}
