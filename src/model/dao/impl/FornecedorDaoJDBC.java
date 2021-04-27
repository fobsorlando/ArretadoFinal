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
import model.dao.FornecedorDao;
import model.entities.Fornecedor;

public class FornecedorDaoJDBC implements FornecedorDao {
	


    private Connection conn;

    public FornecedorDaoJDBC(Connection conn) {
                this.conn=conn;
    }

	@Override

	public void insert(Fornecedor obj) {
		PreparedStatement st = null;
		try {
				st = conn.prepareStatement("insert into Fornecedor "
								+ "(no_Fornecedor, "
								+ " no_fantasia, "
								+ " no_contato, "
								+ " no_email, "
								+ " nr_telefone1, "
								+ " nr_telefone2, "
								+ " nr_cep, "
								+ " no_endereco, "
								+ " nr_numero, "
								+ " no_complemento, "
								+ " no_cidade, "
								+ " sg_uf, "
								+ " no_observacao "
								+ ") "
								+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?) ",
								Statement.RETURN_GENERATED_KEYS
								);

				st.setString(1, obj.getNo_fornecedor());
				st.setString(2, obj.getNo_fantasia());
				st.setString(3, obj.getNo_contato());
				st.setString(4, obj.getNo_email());
				st.setString(5, obj.getNr_telefone1());
				st.setString(6, obj.getNr_telefone2());
				st.setString(7, obj.getNr_cep());
				st.setString(8, obj.getNo_endereco());
				st.setInt(9, obj.getNr_numero());
				st.setString(10, obj.getNo_complemento());
				st.setString(11, obj.getNo_cidade());
				st.setString(12, obj.getSg_uf());
				st.setString(13, obj.getNo_observacao());
				
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
	public void udpdate(Fornecedor obj) {
				PreparedStatement st = null;
		try {
				st = conn.prepareStatement("update  Fornecedor set "
								+ "no_Fornecedor = ?, "
								+ " no_fantasaia = ?, "
								+ " no_contato   = ?, "
								+ " no_email     = ?, "
								+ " nr_telefone1 = ?, "
								+ " nr_telefone2 = ?, "
								+ " nr_cep       = ?,  "
								+ " no_endereco  = ?, "
								+ " nr_numero    = ?, "
								+ " no_complemento = ?, "
								+ " no_cidade    = ?, "
								+ " sg_uf        = ?, "
								+ " no_observacao = ? "
								+ "where id = ? "
								);

				st.setString(1, obj.getNo_fornecedor());
				st.setString(2, obj.getNo_fantasia());
				st.setString(3, obj.getNo_contato());
				st.setString(4, obj.getNo_email());
				st.setString(5, obj.getNr_telefone1());
				st.setString(6, obj.getNr_telefone2());
				st.setString(7, obj.getNr_cep());
				st.setString(8, obj.getNo_endereco());
				st.setInt(9, obj.getNr_numero());
				st.setString(10, obj.getNo_complemento());
				st.setString(11, obj.getNo_cidade());
				st.setString(12, obj.getSg_uf());
				st.setString(13, obj.getNo_observacao());
				st.setInt(14,obj.getId());


				int rowsAffected = st.executeUpdate();

				if (rowsAffected > 0) {
					// nada por enquanto
				}
				else {
						throw new DbException ("Fornecedor não Existe para Alteração!");
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
				st = conn.prepareStatement("delete from Fornecedor  "
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
	public Fornecedor findByid(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
								"select * from Fornecedor  "
								+ "where id = ?"
								);
				st.setInt(1, id);
				rs = st.executeQuery();
				if (rs.next()) {
					Fornecedor obj = instantiateFornecedor(rs);

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
	public List<Fornecedor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
								"select * from Fornecedor  "
								);
				
				rs = st.executeQuery();
				List <Fornecedor> list = new ArrayList<>();

				while (rs.next()) {
					Fornecedor obj = instantiateFornecedor(rs);

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

	private Fornecedor instantiateFornecedor(ResultSet rs) throws SQLException {
		Fornecedor forn = new Fornecedor();
		forn.setId(rs.getInt("id"));
		forn.setNo_fornecedor(rs.getString("no_Fornecedor"));
		return forn;
	}




}
