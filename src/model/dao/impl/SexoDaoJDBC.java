package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SexoDao;
import model.entities.Sexo;

public class SexoDaoJDBC implements SexoDao {
	


    private Connection conn;

    public SexoDaoJDBC(Connection conn) {
                this.conn=conn;
    }



	@Override
	public List<Sexo> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
								"select * from sexo  "
								);
				
				rs = st.executeQuery();
				List <Sexo> list = new ArrayList<>();

				while (rs.next()) {
					Sexo obj = instantiateSexo(rs);

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

	private Sexo instantiateSexo(ResultSet rs) throws SQLException {
		Sexo forn = new Sexo();
		forn.setSg_sexo(rs.getString("sg_sexo"));
		forn.setNo_sexo(rs.getString("no_sexo"));
		return forn;
	}


}
