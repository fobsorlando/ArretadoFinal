package model.entities;

import java.io.Serializable;
import java.sql.Date;

public class Sexo implements Serializable  {
	
	/**
	 * Orlando Sabóia - FOBS
	 * Maio/2021
	 */
	
	private static final long serialVersionUID = 1L;
	private String sg_sexo;
	private String no_sexo;
	
	public Sexo () {
		
	}

	public Sexo(String sg_sexo, String no_sexo) {
		super();
		this.sg_sexo = sg_sexo;
		this.no_sexo = no_sexo;
	}

	public String getSg_sexo() {
		return sg_sexo;
	}

	public void setSg_sexo(String sg_sexo) {
		this.sg_sexo = sg_sexo;
	}

	public String getNo_sexo() {
		return no_sexo;
	}

	public void setNo_sexo(String no_sexo) {
		this.no_sexo = no_sexo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sg_sexo == null) ? 0 : sg_sexo.hashCode());
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
		Sexo other = (Sexo) obj;
		if (sg_sexo == null) {
			if (other.sg_sexo != null)
				return false;
		} else if (!sg_sexo.equals(other.sg_sexo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "sexo [sg_sexo=" + sg_sexo + ", no_sexo=" + no_sexo + "]";
	}



}
