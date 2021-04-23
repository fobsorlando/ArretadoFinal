package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Departamento;

public class DepartamentoService {
	
	public List<Departamento> findAll() {
		
		List<Departamento> list = new ArrayList<>();
		list.add (new Departamento(1,"Bolo"));
		list.add (new Departamento(2,"Salgados"));
		list.add (new Departamento(3,"Limpeza"));
		list.add (new Departamento(4,"Acesórios"));
		list.add (new Departamento(5,"Não sei"));
		
		return list;
		
	}

}
