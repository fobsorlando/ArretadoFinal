package model.dao;

import java.util.List;

import model.entities.SubGrupo;

public interface SubGrupoDao {
	void insert  (SubGrupo obj);
	void udpdate (SubGrupo obj);
	void deleteById (Integer id);
	SubGrupo findByid(Integer id);
	
	List<SubGrupo> findAll();

}
