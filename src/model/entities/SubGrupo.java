package model.entities;

import java.io.Serializable;
import java.sql.Date;

public class SubGrupo implements Serializable  {
	
	/**
	 * Orlando Sabóia - FOBS
	 * Abril/2021
	 */
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String no_subGrupo;
	private Date dth_criacao;
	private Date dth_alteracao;
	
	
	public SubGrupo () {
		
	}

	public SubGrupo(Integer id, String no_subGrupo) {
		super();
		this.id = id;
		this.no_subGrupo = no_subGrupo;
	}


	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "SubGrupo [id=" + id + ", no_subGrupo=" + no_subGrupo + "]";
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
		SubGrupo other = (SubGrupo) obj;
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

	public String getNo_subGrupo() {
		return no_subGrupo;
	}

	public void setNo_subGrupo(String no_subGrupo) {
		this.no_subGrupo = no_subGrupo;
	}

	public Date getDth_criacao() {
		return dth_criacao;
	}

	public Date getDth_alteracao() {
		return dth_alteracao;
	}
	
}
