package model.entities;

import java.io.Serializable;
import java.sql.Date;

public class Secao implements Serializable  {
	
	/**
	 * Orlando Sabóia - FOBS
	 * Abril/2021
	 */
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String no_secao;
	private Date dth_criacao;
	private Date dth_alteracao;
	
	
	public Secao () {
		
	}

	public Secao(Integer id, String no_departamento) {
		super();
		this.id = id;
		this.no_secao = no_departamento;
	}


	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Secao [id=" + id + ", no_secao=" + no_secao + "]";
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
		Secao other = (Secao) obj;
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

	public String getNo_secao() {
		return no_secao;
	}

	public void setNo_secao(String no_secao) {
		this.no_secao = no_secao;
	}

	public Date getDth_criacao() {
		return dth_criacao;
	}

	public Date getDth_alteracao() {
		return dth_alteracao;
	}
	
}
