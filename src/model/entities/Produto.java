package model.entities;

import java.io.Serializable;
import java.sql.Date;

public class Produto implements Serializable {

	/**
	 * Orlando Saboia
	 * Abril / 2021
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String no_produto;
	private String no_produto_forn;
	private Long cd_ean13;
	private Double vl_venda;
	private Double vl_custo;
	
	private Date dth_criacao;
	private Date dth_alteracao;
	
	private Departamento departamento;
	private Secao secao;
	private Grupo grupo;
	private SubGrupo subGrupo;
	
	private Fornecedor fornecedor;
	
	public Produto() {
		
	}
	
	public Produto(String no_produto, String no_produto_forn, Long cd_ean13, Double vl_venda, Double vl_custo,
			Departamento departamento, Secao secao, Grupo grupo,
			SubGrupo subGrupo, Fornecedor fornecedor) {
		super();
		this.no_produto = no_produto;
		this.no_produto_forn = no_produto_forn;
		this.cd_ean13 = cd_ean13;
		this.vl_venda = vl_venda;
		this.vl_custo = vl_custo;
		this.dth_criacao = dth_criacao;
		this.dth_alteracao = dth_alteracao;
		this.departamento = departamento;
		this.secao = secao;
		this.grupo = grupo;
		this.subGrupo = subGrupo;
		this.fornecedor = fornecedor;
	}

	public String getNo_produto() {
		return no_produto;
	}

	public void setNo_produto(String no_produto) {
		this.no_produto = no_produto;
	}

	public String getNo_produto_forn() {
		return no_produto_forn;
	}

	public void setNo_produto_forn(String no_produto_forn) {
		this.no_produto_forn = no_produto_forn;
	}

	public Long getCd_ean13() {
		return cd_ean13;
	}

	public void setCd_ean13(Long cd_ean13) {
		this.cd_ean13 = cd_ean13;
	}

	public Double getVl_venda() {
		return vl_venda;
	}

	public void setVl_venda(Double vl_venda) {
		this.vl_venda = vl_venda;
	}

	public Double getVl_custo() {
		return vl_custo;
	}

	public void setVl_custo(Double vl_custo) {
		this.vl_custo = vl_custo;
	}

	public Date getDth_criacao() {
		return dth_criacao;
	}

	public void setDth_criacao(Date dth_criacao) {
		this.dth_criacao = dth_criacao;
	}

	public Date getDth_alteracao() {
		return dth_alteracao;
	}

	public void setDth_alteracao(Date dth_alteracao) {
		this.dth_alteracao = dth_alteracao;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Secao getSecao() {
		return secao;
	}

	public void setSecao(Secao secao) {
		this.secao = secao;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public SubGrupo getSubGrupo() {
		return subGrupo;
	}

	public void setSubGrupo(SubGrupo subGrupo) {
		this.subGrupo = subGrupo;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((no_produto == null) ? 0 : no_produto.hashCode());
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
		Produto other = (Produto) obj;
		if (no_produto == null) {
			if (other.no_produto != null)
				return false;
		} else if (!no_produto.equals(other.no_produto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", no_produto=" + no_produto + ", no_produto_forn=" + no_produto_forn
				+ ", cd_ean13=" + cd_ean13 + ", vl_venda=" + vl_venda + ", vl_custo=" + vl_custo + ", dth_criacao="
				+ dth_criacao + ", dth_alteracao=" + dth_alteracao + ", departamento=" + departamento + ", secao="
				+ secao + ", grupo=" + grupo + ", subGrupo=" + subGrupo + ", fornecedor=" + fornecedor + "]";
	}
	
	
}
