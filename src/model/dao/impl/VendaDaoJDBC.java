package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.VendaDao;
import model.entities.Cliente;
import model.entities.Sexo;
import model.entities.UF;
import model.entities.Usuario;
import model.entities.Venda;

public class VendaDaoJDBC implements VendaDao {
	


    private Connection conn;

    public VendaDaoJDBC(Connection conn) {
                this.conn=conn;
    }
	private Venda instantiateVenda(ResultSet rs, Usuario usuario, Cliente cliente) 
			  throws SQLException {
		Venda obj = new Venda();
		obj.setId_venda(rs.getInt("id_venda"));
		obj.setDt_venda(rs.getDate("dt_venda"));
		obj.setVl_venda(rs.getDouble("vl_venda"));
		obj.setVl_produto(rs.getDouble("vl_produto"));
		obj.setVl_desconto(rs.getDouble("vl_desconto"));
		obj.setVl_acrescimo(rs.getDouble("vl_acrescimo"));
		obj.setFl_situacao(rs.getString("fl_situacao"));
		
		obj.setCliente(cliente);
		obj.setUsuario(usuario);
		
		return obj;
	}
    
	private Usuario instantiateUsuario(ResultSet rs) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setId(rs.getInt("id"));
		usuario.setNo_usuario(rs.getString("no_usuario"));
		return usuario;
	}
	
	private Cliente instantiateCliente(ResultSet rs) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setId(rs.getInt("id_cliente"));
		cliente.setNo_cliente(rs.getString("no_cliente"));
		return cliente;
	}
	@Override
	public void insert(Venda obj) {
		PreparedStatement st = null;
		try {
				st = conn.prepareStatement("insert into Venda "
							+ " (id_venda, dt_venda, id_usuario, id_cliente, "
							+ " vl_venda, vl_produto, vl_desconto, vl_acrescimo, "
							+ " fl_situacao) "
							+ " values  "
							+  " (?, ?, ?, ?, ?, ?, ?, ?, ?) ",
								Statement.RETURN_GENERATED_KEYS
								);

				st.setInt(1, obj.getId_venda());
				st.setDate(2,obj.getDt_venda());
				st.setInt(3, obj.getUsuario().getId());
				st.setInt(4, obj.getCliente().getId());
				st.setDouble(5, obj.getVl_venda());
				st.setDouble(6, obj.getVl_produto());
				st.setDouble(7, obj.getVl_desconto());
				st.setDouble(8, obj.getVl_acrescimo());
				
				st.setString(9, obj.getFl_situacao());
				
				
				
				int rowsAffected = st.executeUpdate();

				if (rowsAffected > 0) {
							ResultSet rs = st.getGeneratedKeys();
							if  (rs.next()) {
									int id = rs.getInt(1);
									obj.setId_venda(id);
							}
							DB.closeResultSet(rs);
				}
				else {
						throw new DbException ("Erro Inexperado: N├úo  houve Inclus├úo!");
				}

		}
		catch (SQLException e) {
				throw new DbException (e.getMessage());
		}
		finally {
				DB.closeStatement(st);
		}

		
	}

	@Override
	public void udpdate(Venda obj) {
				PreparedStatement st = null;
		try {
				st = conn.prepareStatement("update  Venda set "
							+ " fl_situacao = ?, "
							+ "where id_venda = ? "
								);
				st.setString(1, obj.getFl_situacao());
			
				
				st.setInt(2,obj.getId_venda());
						


				int rowsAffected = st.executeUpdate();

				if (rowsAffected > 0) {
					// nada por enquanto
				}
				else {
						throw new DbException ("Venda não Existe para Alteração!");
				}

		}
		catch (SQLException e) {
				throw new DbException (e.getMessage());
		}
		finally {
				DB.closeStatement(st);
		}
		
	}

	/*
	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
				st = conn.prepareStatement("delete from Venda  "
								+ "where id = ?"
								);

				st.setInt(1,id);

				int rowsAffected = st.executeUpdate();

				if (rowsAffected == 0 ) {
						throw  new DbException("ID N├âO EXITE PARA EXCLUIR!");
				}

		}
		catch (SQLException e) {
				throw new DbException (e.getMessage());
		}
		finally {
				DB.closeStatement(st);
		}

		
	}
	 */
	
	@Override
	public Venda findByid(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
								"select * from Venda  "
								+ "left join usuario "
								+ "on  usuario.id = Venda.id_usuario "
								+ "left join cliente "
								+ " on cliente.id = Venda.id_cliente"
								+ "where id_venda = ?"
								);
				st.setInt(1, id);
				rs = st.executeQuery();
				if (rs.next()) {
					//Venda obj = instantiateVenda(rs);
					Usuario usuario = instantiateUsuario(rs);
					Cliente cliente = instantiateCliente(rs);
					
					Venda obj = instantiateVenda(rs,usuario,cliente);
					return obj;
				}
				return null;
		}
		catch (SQLException e){
				throw new  DbException(e.getMessage());
		}
		finally {
				DB.closeStatement(st);
				DB.closeResultSet(rs);
		}


	}

	@Override
	public List<Venda> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
								"select * from Venda "
								+ "left join usuario "
								+ "on  usuario.id = Venda.id_usuario "
								+ "left join cliente "
								+ " on cliente.id = Venda.id_cliente"
								);
				
				rs = st.executeQuery();
				List <Venda> list = new ArrayList<>();

				while (rs.next()) {
				//	Venda obj = instantiateVenda(rs);
					Usuario usuario = instantiateUsuario(rs);
					Cliente cliente = instantiateCliente(rs);
					
					Venda obj = instantiateVenda(rs,usuario,cliente);
					list.add(obj);
				}
				return list;
		}
		catch (SQLException e){
				throw new  DbException(e.getMessage());
		}
		finally {
				DB.closeStatement(st);
				DB.closeResultSet(rs);
		}

	}

	/*
	@Override
	public List<Venda> findByNome(String no_Venda) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
				st = conn.prepareStatement(
				  			  "select * from Venda  "
							+ " where no_Venda like ?"
								);
				st.setString(1, ("%" + no_Venda + "%"));
				//st.setString(1, no_Venda);
				
				

				rs = st.executeQuery();
				
				System.out.println("Veja ---> " + rs.getRow());
				List <Venda> list = new ArrayList<>();

				while (rs.next()) {
					Venda obj = instantiateVenda(rs);

					list.add(obj);
				}
				return list;
		}
		catch (SQLException e){
				throw new  DbException(e.getMessage());
		}
		finally {
				DB.closeStatement(st);
				DB.closeResultSet(rs);
		}
	
	}*/


}
