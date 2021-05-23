package model.entities;

import java.io.Serializable;
import java.sql.Date;

public class Unidade implements Serializable  {
	
	/**
	 * Orlando Sabóia - FOBS
	 * Maio/2021
	 */
	
	private static final long serialVersionUID = 1L;
	private String sg_unidade;
	private String no_unidade;
	
	public Unidade () {
		
	}

	public Unidade(String sg_unidade, String no_unidade) {
		super();
		this.sg_unidade = sg_unidade;
		this.no_unidade = no_unidade;
	}

	public String getSg_unidade() {
		return sg_unidade;
	}

	public void setSg_unidade(String sg_unidade) {
		this.sg_unidade = sg_unidade;
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
		result = prime * result + ((sg_unidade == null) ? 0 : sg_unidade.hashCode());
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
		Unidade other = (Unidade) obj;
		if (sg_unidade == null) {
			if (other.sg_unidade != null)
				return false;
		} else if (!sg_unidade.equals(other.sg_unidade))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "unidade [sg_unidade=" + sg_unidade + ", no_unidade=" + no_unidade + "]";
	}



}
