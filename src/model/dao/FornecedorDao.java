package model.dao;

import java.util.List;

import model.entities.Fornecedor;

public interface FornecedorDao {
	
	void insert  (FornecedorDao obj);
	void udpdate (FornecedorDao obj);
	void deleteById (Integer id);
	FornecedorDao findByid(Integer id);
	
	List<FornecedorDao> findAll();

}
