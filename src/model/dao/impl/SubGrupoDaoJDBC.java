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
import model.dao.SubGrupoDao;
import model.entities.SubGrupo;
import model.entities.Produto;

public class SubGrupoDaoJDBC implements SubGrupoDao {
	


    private Connection conn;

    public SubGrupoDaoJDBC(Connection conn) {
                this.conn=conn;
    }

	@Override
	public void insert(SubGrupo obj) {
		PreparedStatement st = null;
		try {
				st = conn.prepareStatement("insert into SubGrupo "
								+ "(no_SubGrupo) "
								+ "values (?) ",
								Statement.RETURN_GENERATED_KEYS
								);

				st.setString(1, obj.getNo_subGrupo());

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
	public void udpdate(SubGrupo obj) {
				PreparedStatement st = null;
		try {
				st = conn.prepareStatement("update  SubGrupo set "
								+ "no_SubGrupo = ? "
								+ "where id = ? "
								);

				st.setString(1, obj.getNo_subGrupo());
				 st.setInt(2,obj.getId());


				int rowsAffected = st.executeUpdate();

				if (rowsAffected > 0) {
					// nada por enquanto
				}
				else {
						throw new DbException ("SubGrupo não Existe para Alteração!");
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
				st = conn.prepareStatement("delete from SubGrupo  "
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
	public SubGrupo findByid(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
								"select * from SubGrupo  "
								+ "where id = ?"
								);
				st.setInt(1, id);
				rs = st.executeQuery();
				if (rs.next()) {
					SubGrupo obj = instantiateSubGrupo(rs);

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
	public List<SubGrupo> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
								"select * from SubGrupo  "
								);
				
				rs = st.executeQuery();
				List <SubGrupo> list = new ArrayList<>();

				while (rs.next()) {
					SubGrupo obj = instantiateSubGrupo(rs);

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

	private SubGrupo instantiateSubGrupo(ResultSet rs) throws SQLException {
		SubGrupo forn = new SubGrupo();
		forn.setId(rs.getInt("id"));
		forn.setNo_subGrupo(rs.getString("no_SubGrupo"));
		return forn;
	}


}
