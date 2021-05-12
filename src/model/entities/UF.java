package model.entities;

import java.io.Serializable;
import java.sql.Date;

public class UF implements Serializable  {
	
	/**
	 * Orlando Sabóia - FOBS
	 * Maio/2021
	 */
	
	private static final long serialVersionUID = 1L;
	private String sg_uf;
	private String no_unidade;
	
	
	public UF () {
		
	}

	public UF(String sg_uf, String no_udade) {
		super();
		this.sg_uf = sg_uf;
		this.no_unidade = no_unidade;
	}

	public String getSg_uf() {
		return sg_uf;
	}

	public void setSg_uf(String sg_uf) {
		this.sg_uf = sg_uf;
	}

	public String getNo_unidade() {
		return no_unidade;
	}

	public void setNo_unidade(String no_unidade) {
		this.no_unidade = no_unidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((no_unidade == null) ? 0 : no_unidade.hashCode());
		result = prime * result + ((sg_uf == null) ? 0 : sg_uf.hashCode());
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
		UF other = (UF) obj;
		if (no_unidade == null) {
			if (other.no_unidade != null)
				return false;
		} else if (!no_unidade.equals(other.no_unidade))
			return false;
		if (sg_uf == null) {
			if (other.sg_uf != null)
				return false;
		} else if (!sg_uf.equals(other.sg_uf))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UF [sg_uf=" + sg_uf + ", no_unidade=" + no_unidade + "]";
	}

}
