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
import model.dao.UFDao;
import model.entities.UF;
import model.entities.ProdutoBack;

public class UFDaoJDBC implements UFDao {
	


    private Connection conn;

    public UFDaoJDBC(Connection conn) {
                this.conn=conn;
    }



	@Override
	public List<UF> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
								"select * from UF  "
								);
				
				rs = st.executeQuery();
				List <UF> list = new ArrayList<>();

				while (rs.next()) {
					UF obj = instantiateUF(rs);

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

	private UF instantiateUF(ResultSet rs) throws SQLException {
		UF forn = new UF();
		forn.setSg_uf(rs.getString("sg_uf"));
		forn.setNo_unidade(rs.getString("no_unidade_federacao"));
		return forn;
	}


}
