package model.entities;

import java.io.Serializable;
import java.sql.Date;

public class Cliente implements Serializable  {

	/**
	 * Orlando Maio/2021.
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String no_cliente;
	private String no_apelido;
	private Date dt_nascimento;
	private String fl_sexo;
	private String no_email1;
	private String no_email2;
	private String nr_telefone1;
	private String nr_telefone2;
	private String nr_cep;
	private String no_endereco;
	private Integer nr_numero;
	private String no_complemento;
	private String no_cidade;
	private String no_bairro;
	private String sg_uf;
	private String no_observacao;
	private Date dth_criacao;
	private Date dth_aleracao;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNo_cliente() {
		return no_cliente;
	}
	public void setNo_cliente(String no_cliente) {
		this.no_cliente = no_cliente;
	}
	public String getNo_apelido() {
		return no_apelido;
	}
	public void setNo_apelido(String no_apelido) {
		this.no_apelido = no_apelido;
	}
	public Date getDt_nascimento() {
		return dt_nascimento;
	}
	public void setDt_nascimento(Date dt_nascimento) {
		this.dt_nascimento = dt_nascimento;
	}
	public String getFl_sexo() {
		return fl_sexo;
	}
	public void setFl_sexo(String fl_sexo) {
		this.fl_sexo = fl_sexo;
	}
	public String getNo_email1() {
		return no_email1;
	}
	public void setNo_email1(String no_email1) {
		this.no_email1 = no_email1;
	}
	public String getNo_email2() {
		return no_email2;
	}
	public void setNo_email2(String no_email2) {
		this.no_email2 = no_email2;
	}
	public String getNr_telefone1() {
		return nr_telefone1;
	}
	public void setNr_telefone1(String nr_telefone1) {
		this.nr_telefone1 = nr_telefone1;
	}
	public String getNr_telefone2() {
		return nr_telefone2;
	}
	public void setNr_telefone2(String nr_telefone2) {
		this.nr_telefone2 = nr_telefone2;
	}
	public String getNr_cep() {
		return nr_cep;
	}
	public void setNr_cep(String nr_cep) {
		this.nr_cep = nr_cep;
	}
	public String getNo_endereco() {
		return no_endereco;
	}
	public void setNo_endereco(String no_endereco) {
		this.no_endereco = no_endereco;
	}
	public Integer getNr_numero() {
		return nr_numero;
	}
	public void setNr_numero(Integer nr_numero) {
		this.nr_numero = nr_numero;
	}
	public String getNo_complemento() {
		return no_complemento;
	}
	public void setNo_complemento(String no_complemento) {
		this.no_complemento = no_complemento;
	}
	public String getNo_cidade() {
		return no_cidade;
	}
	public void setNo_cidade(String no_cidade) {
		this.no_cidade = no_cidade;
	}
	public String getNo_bairro() {
		return no_bairro;
	}
	public void setNo_bairro(String no_bairro) {
		this.no_bairro = no_bairro;
	}
	public String getSg_uf() {
		return sg_uf;
	}
	public void setSg_uf(String sg_uf) {
		this.sg_uf = sg_uf;
	}
	public String getNo_observacao() {
		return no_observacao;
	}
	public void setNo_observacao(String no_observacao) {
		this.no_observacao = no_observacao;
	}
	public Date getDth_criacao() {
		return dth_criacao;
	}
	public void setDth_criacao(Date dth_criacao) {
		this.dth_criacao = dth_criacao;
	}
	public Date getDth_aleracao() {
		return dth_aleracao;
	}
	public void setDth_aleracao(Date dth_aleracao) {
		this.dth_aleracao = dth_aleracao;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", no_cliente=" + no_cliente + ", no_apelido=" + no_apelido + ", dt_nascimento="
				+ dt_nascimento + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((no_cliente == null) ? 0 : no_cliente.hashCode());
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
		Cliente other = (Cliente) obj;
		if (no_cliente == null) {
			if (other.no_cliente != null)
				return false;
		} else if (!no_cliente.equals(other.no_cliente))
			return false;
		return true;
	}
	
	
	
	
	

}
