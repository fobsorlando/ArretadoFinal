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
import model.dao.SecaoDao;
import model.entities.Departamento;
import model.entities.Secao;

public class SecaoDaoJDBC implements SecaoDao {
	


    private Connection conn;

    public SecaoDaoJDBC(Connection conn) {
                this.conn=conn;
    }

	@Override
	public void insert(Secao obj) {
		PreparedStatement st = null;
		try {
				st = conn.prepareStatement("insert into Secao "
								+ "(no_Secao) "
								+ "values (?) ",
								Statement.RETURN_GENERATED_KEYS
								);

				st.setString(1, obj.getNo_secao());

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
	public void udpdate(Secao obj) {
				PreparedStatement st = null;
		try {
				st = conn.prepareStatement("update  Secao set "
								+ "no_Secao = ? "
								+ "where id = ? "
								);

				st.setString(1, obj.getNo_secao());
				 st.setInt(2,obj.getId());


				int rowsAffected = st.executeUpdate();

				if (rowsAffected > 0) {
					// nada por enquanto
				}
				else {
						throw new DbException ("Secao não Existe para Alteração!");
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
				st = conn.prepareStatement("delete from Secao  "
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
	public Secao findByid(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
								"select * from Secao  "
								+ "where id = ?"
								);
				st.setInt(1, id);
				rs = st.executeQuery();
				if (rs.next()) {
					Secao obj = instantiateSecao(rs);

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
	public List<Secao> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
								"select * from Secao  "
								);
				
				rs = st.executeQuery();
				List <Secao> list = new ArrayList<>();

				while (rs.next()) {
					Secao obj = instantiateSecao(rs);

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

	private Secao instantiateSecao(ResultSet rs) throws SQLException {
		Secao forn = new Secao();
		forn.setId(rs.getInt("id"));
		forn.setNo_secao(rs.getString("no_Secao"));
		return forn;
	}


}



