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
import model.dao.GrupoDao;
import model.entities.Grupo;

	public class GrupoDaoJDBC implements GrupoDao {
		


	    private Connection conn;

	    public GrupoDaoJDBC(Connection conn) {
	                this.conn=conn;
	    }

		@Override
		public void insert(Grupo obj) {
			PreparedStatement st = null;
			try {
					st = conn.prepareStatement("insert into Grupo "
									+ "(no_Grupo) "
									+ "values (?) ",
									Statement.RETURN_GENERATED_KEYS
									);

					st.setString(1, obj.getNo_grupo());

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
		public void udpdate(Grupo obj) {
					PreparedStatement st = null;
			try {
					st = conn.prepareStatement("update  Grupo set "
									+ "no_Grupo = ? "
									+ "where id = ? "
									);

					st.setString(1, obj.getNo_grupo());
					 st.setInt(2,obj.getId());


					int rowsAffected = st.executeUpdate();

					if (rowsAffected > 0) {
						// nada por enquanto
					}
					else {
							throw new DbException ("Grupo não Existe para Alteração!");
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
					st = conn.prepareStatement("delete from Grupo  "
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
		public Grupo findByid(Integer id) {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
					st = conn.prepareStatement(
									"select * from Grupo  "
									+ "where id = ?"
									);
					st.setInt(1, id);
					rs = st.executeQuery();
					if (rs.next()) {
						Grupo obj = instantiateGrupo(rs);

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
		public List<Grupo> findAll() {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
					st = conn.prepareStatement(
									"select * from Grupo  "
									);
					
					rs = st.executeQuery();
					List <Grupo> list = new ArrayList<>();

					while (rs.next()) {
						Grupo obj = instantiateGrupo(rs);

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

		private Grupo instantiateGrupo(ResultSet rs) throws SQLException {
			Grupo forn = new Grupo();
			forn.setId(rs.getInt("id"));
			forn.setNo_grupo(rs.getString("no_Grupo"));
			return forn;
		}


	}
