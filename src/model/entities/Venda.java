package model.entities;

import java.io.Serializable;
import java.sql.Date;

public class Venda implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id_venda;
	private Date dt_venda;

	private Double vl_venda;
	private Double vl_produto;
	private Double vl_desconto;
	private Double vl_acrescimo;
	private String fl_situacao;
	
	private Usuario usuario;
	private Cliente cliente;
	
	public Venda() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Venda(Integer id_venda, Date dt_venda, Double vl_venda, Double vl_produto, Double vl_desconto,
			Double vl_acrescimo, String fl_situacao, Usuario usuario, Cliente cliente) {
		super();
		this.id_venda = id_venda;
		this.dt_venda = dt_venda;
		this.vl_venda = vl_venda;
		this.vl_produto = vl_produto;
		this.vl_desconto = vl_desconto;
		this.vl_acrescimo = vl_acrescimo;
		this.fl_situacao = fl_situacao;
		this.usuario = usuario;
		this.cliente = cliente;
	}

	public Integer getId_venda() {
		return id_venda;
	}

	public void setId_venda(Integer id_venda) {
		this.id_venda = id_venda;
	}

	public Date getDt_venda() {
		return dt_venda;
	}

	public void setDt_venda(Date dt_venda) {
		this.dt_venda = dt_venda;
	}

	public Double getVl_venda() {
		return vl_venda;
	}

	public void setVl_venda(Double vl_venda) {
		this.vl_venda = vl_venda;
	}

	public Double getVl_produto() {
		return vl_produto;
	}

	public void setVl_produto(Double vl_produto) {
		this.vl_produto = vl_produto;
	}

	public Double getVl_desconto() {
		return vl_desconto;
	}

	public void setVl_desconto(Double vl_desconto) {
		this.vl_desconto = vl_desconto;
	}

	public Double getVl_acrescimo() {
		return vl_acrescimo;
	}

	public void setVl_acrescimo(Double vl_acrescimo) {
		this.vl_acrescimo = vl_acrescimo;
	}

	public String getFl_situacao() {
		return fl_situacao;
	}

	public void setFl_situacao(String fl_situacao) {
		this.fl_situacao = fl_situacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Venda [id_venda=" + id_venda + ", dt_venda=" + dt_venda + ", vl_venda=" + vl_venda + ", vl_produto="
				+ vl_produto + ", vl_desconto=" + vl_desconto + ", vl_acrescimo=" + vl_acrescimo + ", fl_situacao="
				+ fl_situacao + ", usuario=" + usuario + ", cliente=" + cliente + "]";
	}
	
	
	
}
