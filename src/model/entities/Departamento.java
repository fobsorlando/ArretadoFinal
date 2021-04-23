package model.entities;

import java.io.Serializable;
import java.sql.Date;

public class Departamento implements Serializable  {
	
	/**
	 * Orlando Sabóia - FOBS
	 * Abril/2021
	 */
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String no_departamento;
	private Date dth_criacao;
	private Date dth_alteracao;
	
	
	public Departamento () {
		
	}

	public Departamento(Integer id, String no_departamento) {
		super();
		this.id = id;
		this.no_departamento = no_departamento;
	}


	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Departamento [id=" + id + ", no_departamento=" + no_departamento + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departamento other = (Departamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

 
	public void setId(Integer id) {
		this.id = id;
	}

	public String getNo_departamento() {
		return no_departamento;
	}

	public void setNo_departamento(String no_departamento) {
		this.no_departamento = no_departamento;
	}

	public Date getDth_criacao() {
		return dth_criacao;
	}

	public Date getDth_alteracao() {
		return dth_alteracao;
	}
	
}
