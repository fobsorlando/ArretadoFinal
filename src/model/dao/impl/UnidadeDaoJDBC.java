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
import model.dao.UnidadeDao;
import model.entities.Unidade;
import model.entities.Unidade;

public class UnidadeDaoJDBC implements UnidadeDao {
	


    private Connection conn;

    public UnidadeDaoJDBC(Connection conn) {
                this.conn=conn;
    }

	@Override
	public void insert(Unidade obj) {
		PreparedStatement st = null;
		try {
				st = conn.prepareStatement("insert into Unidade "
								+ "(sg_unidade,no_unidade) "
								+ "values (?,?) ",
								Statement.RETURN_GENERATED_KEYS
								);

				st.setString(1, obj.getSg_unidade());
				st.setString(2, obj.getNo_unidade());

				int rowsAffected = st.executeUpdate();

				if (rowsAffected > 0) {
							ResultSet rs = st.getGeneratedKeys();
							if  (rs.next()) {
									String id = rs.getString(1);
									obj.setSg_unidade(id);
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
	public void udpdate(Unidade obj) {
				PreparedStatement st = null;
		try {
				st = conn.prepareStatement("update  unidade set "
								+ "no_unidade = ? "
								+ "where sg_unidade= ? "
								);

				st.setString(1, obj.getNo_unidade());
				 st.setString(2,obj.getSg_unidade());


				int rowsAffected = st.executeUpdate();

				if (rowsAffected > 0) {
					// nada por enquanto
				}
				else {
						throw new DbException ("Unidade não Existe para Alteração!");
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
	public void deleteById(String id) {
		PreparedStatement st = null;
		try {
				st = conn.prepareStatement("delete from Unidade  "
								+ "where sg_unidade = ?"
								);

				st.setString(1,id);

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
	public Unidade findByid(String id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
								"select * from Unidade  "
								+ "where sg_unidade = ?"
								);
				st.setString(1, id);
				rs = st.executeQuery();
				if (rs.next()) {
					Unidade obj = instantiateUnidade(rs);

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
	public List<Unidade> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
								"select * from unidade  "
								);
				
				rs = st.executeQuery();
				List <Unidade> list = new ArrayList<>();

				while (rs.next()) {
					Unidade obj = instantiateUnidade(rs);

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

	private Unidade instantiateUnidade(ResultSet rs) throws SQLException {
		Unidade forn = new Unidade();
		forn.setSg_unidade(rs.getString("sg_unidade"));
		forn.setNo_unidade(rs.getString("no_unidade"));
		return forn;
	}


}
