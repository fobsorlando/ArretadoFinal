package model.entities;

import java.io.Serializable;
import java.sql.Date;

public class Grupo implements Serializable  {
	
	/**
	 * Orlando Sabóia - FOBS
	 * Abril/2021
	 */
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String no_grupo;
	private Date dth_criacao;
	private Date dth_alteracao;
	
	
	public Grupo () {
		
	}

	public Grupo(Integer id, String no_grupo) {
		super();
		this.id = id;
		this.no_grupo = no_grupo;
	}


	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Grupo [id=" + id + ", no_grupo=" + no_grupo + "]";
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
		Grupo other = (Grupo) obj;
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

	public String getNo_grupo() {
		return no_grupo;
	}

	public void setNo_grupo(String no_grupo) {
		this.no_grupo = no_grupo;
	}

	public Date getDth_criacao() {
		return dth_criacao;
	}

	public Date getDth_alteracao() {
		return dth_alteracao;
	}
	
}
