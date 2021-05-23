package model.dao;

import java.util.List;

import model.entities.Cliente;
import model.entities.Fornecedor;

public interface FornecedorDao {
	
	void insert  (Fornecedor obj);
	void udpdate (Fornecedor obj);
	void deleteById (Integer id);
	Fornecedor findByid(Integer id);
	
	List<Fornecedor> findAll();
	List<Fornecedor> findByNome(String no_fornecedor);

}
