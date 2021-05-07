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
import model.dao.ClienteDao;
import model.entities.Cliente;
import model.entities.ProdutoBack;

public class ClienteDaoJDBC implements ClienteDao {
	


    private Connection conn;

    public ClienteDaoJDBC(Connection conn) {
                this.conn=conn;
    }

	@Override
	public void insert(Cliente obj) {
		PreparedStatement st = null;
		try {
				st = conn.prepareStatement("insert into cliente "
							+ " (no_cliente, no_apelido, dt_nascimento, fl_sexo, "
							+ " no_email1, no_email2, nr_telefone1, nr_telefone2, "
							+ " nr_cep, no_endereco, nr_numero, no_complemento, "
							+ " no_cidade, sg_uf, no_observacao no_bairro) "
							+ " values  "
							+  " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ",
								Statement.RETURN_GENERATED_KEYS
								);

				st.setString(1, obj.getNo_cliente());
				st.setString(2, obj.getNo_apelido());
				st.setDate(3, obj.getDt_nascimento());
				st.setString(4, obj.getFl_sexo());
				
				st.setString(5, obj.getNo_email1());
				st.setString(6, obj.getNo_email2());
				st.setString(7, obj.getNr_telefone1());
				st.setString(8, obj.getNr_telefone2());
				
				st.setString(9, obj.getNr_cep());
				st.setString(10, obj.getNo_endereco());
				st.setInt(11,obj.getNr_numero());
				st.setString(12, obj.getNo_complemento());
				
				st.setString(13, obj.getNo_cidade());
				st.setString(14, obj.getSg_uf());
				st.setString(15, obj.getNo_observacao());
				st.setString(16, obj.getNo_bairro());

				
				
				
				
				

				int rowsAffected = st.executeUpdate();

				if (rowsAffected > 0) {
							ResultSet rs = st.getGeneratedKeys();
							if  (rs.next()) {
									int id = rs.getInt(1);
									obj.setId(id);
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
	public void udpdate(Cliente obj) {
				PreparedStatement st = null;
		try {
				st = conn.prepareStatement("update  departamento set "
								+ "no_departamento = ? "
								+ "where id = ? "
								);

				st.setString(1, obj.getNo_departamento());
				 st.setInt(2,obj.getId());


				int rowsAffected = st.executeUpdate();

				if (rowsAffected > 0) {
					// nada por enquanto
				}
				else {
						throw new DbException ("Cliente não Existe para Alteração!");
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
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
				st = conn.prepareStatement("delete from departamento  "
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

	@Override
	public Cliente findByid(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
								"select * from departamento  "
								+ "where id = ?"
								);
				st.setInt(1, id);
				rs = st.executeQuery();
				if (rs.next()) {
					Cliente obj = instantiateCliente(rs);

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
	public List<Cliente> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
								"select * from departamento  "
								);
				
				rs = st.executeQuery();
				List <Cliente> list = new ArrayList<>();

				while (rs.next()) {
					Cliente obj = instantiateCliente(rs);

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

	private Cliente instantiateCliente(ResultSet rs) throws SQLException {
		Cliente forn = new Cliente();
		forn.setId(rs.getInt("id"));
		forn.setNo_cliente(rs.getString("no_Cliente"));
		return forn;
	}


}
