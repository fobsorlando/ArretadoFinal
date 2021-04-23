package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.ProdutoDao;
import model.entities.Departamento;
import model.entities.Fornecedor;
import model.entities.Grupo;
import model.entities.Produto;
import model.entities.Secao;
import model.entities.SubGrupo;

public class ProdutoDaoJDBC implements ProdutoDao {
	
	private Connection conn;
	
	public ProdutoDaoJDBC(Connection conn) {
		this.conn=conn;
	}

	@Override
	public void insert(Produto obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("insert into produto "
					+ "(no_produto, no_produto_forn, cd_ean13, vl_venda, vl_custo, "
					+ "id_departamento, id_secao, id_grupo, "
					+ "id_subgrupo, id_fornecedor) "
					+ "values (?,?,?,?,?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS
					);
			
			st.setString(1, obj.getNo_produto());
			st.setString(2, obj.getNo_produto_forn());
			st.setLong(3, obj.getCd_ean13());
			st.setDouble(4, obj.getVl_venda());
			st.setDouble(5, obj.getVl_custo());
			st.setInt(6, obj.getDepartamento().getId());
			st.setInt(7, obj.getSecao().getId());
			st.setInt(8, obj.getGrupo().getId());
			st.setInt(9, obj.getSubGrupo().getId());
			st.setInt(10, obj.getFornecedor().getId());
			
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
				throw new DbException ("Erro Inexperado: Não  houve Inclusão!");  
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
	public void udpdate(Produto obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("update produto set "
					+ "no_produto = ?, no_produto_forn = ?, cd_ean13 = ?, "
					+ "vl_venda = ?, vl_custo = ?, "
					+ "id_departamento = ?, id_secao = ?, id_grupo = ?, "
					+ "id_subgrupo = ?, id_fornecedor = ? "
					+ "where id = ?"
					);
			
			st.setString(1, obj.getNo_produto());
			st.setString(2, obj.getNo_produto_forn());
			st.setLong(3, obj.getCd_ean13());
			st.setDouble(4, obj.getVl_venda());
			st.setDouble(5, obj.getVl_custo());
			st.setInt(6, obj.getDepartamento().getId());
			st.setInt(7, obj.getSecao().getId());
			st.setInt(8, obj.getGrupo().getId());
			st.setInt(9, obj.getSubGrupo().getId());
			st.setInt(10, obj.getFornecedor().getId());
			st.setInt(11,obj.getId());
			
			int rowsAffected = st.executeUpdate();
			

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
			st = conn.prepareStatement("delete from produto  "
					+ "where id = ?"
					);

			st.setInt(1,id);
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected == 0 ) {
				throw  new DbException("ID NÃO EXITE PARA EXCLUIR!");
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
	public Produto findByid(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select * from produto " 
					+ "join departamento on departamento.id  = produto.id_departamento "
					+ "join secao on secao.id = produto.id_secao "
					+ "join grupo on grupo.id = produto.id_grupo "
					+ "join subgrupo on subgrupo.id = produto.id_subgrupo "
					+ "join fornecedor on fornecedor.id = produto.id_subgrupo "
					+ "where produto.id = ?"
					);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				
				Departamento dep = instantiateDepartamento(rs);
				Secao sec = instantiateSecao(rs);
				Grupo grp = instantiateGrupo(rs);
				SubGrupo subG = instantiateSubGrupo(rs);
				Fornecedor forn = instantiateFornecedor(rs);
				
				Produto obj = instantiateProduto(rs,dep,sec,grp,subG,forn);

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

	private Produto instantiateProduto(ResultSet rs, Departamento dep, Secao sec, Grupo grp, SubGrupo subG,
			Fornecedor forn) throws SQLException {
		Produto obj = new Produto();
		obj.setId(rs.getInt("id"));
		obj.setNo_produto(rs.getString("no_produto"));
		obj.setNo_produto_forn(rs.getString("no_produto_forn"));
		obj.setCd_ean13(rs.getLong("cd_ean13"));
		obj.setVl_venda(rs.getDouble("vl_venda"));
		obj.setVl_custo(rs.getDouble("vl_custo"));
		
		obj.setDepartamento(dep);
		obj.setSecao(sec);
		obj.setGrupo(grp);
		obj.setSubGrupo(subG);
		obj.setFornecedor(forn);
		return obj;
	}



	private Fornecedor instantiateFornecedor(ResultSet rs) throws SQLException {
		Fornecedor forn = new Fornecedor();
		forn.setId(rs.getInt("id_fornecedor"));
		forn.setNo_fornecedor(rs.getString("no_fornecedor"));
		return forn;
	}

	private SubGrupo instantiateSubGrupo(ResultSet rs) throws SQLException {
		SubGrupo subG = new SubGrupo();
		subG.setId(rs.getInt("id_subgrupo"));
		subG.setNo_subGrupo(rs.getString("no_subgrupo"));
		return subG;
	}

	private Grupo instantiateGrupo(ResultSet rs) throws SQLException {
		Grupo grp = new Grupo();
		grp.setId(rs.getInt("id_grupo"));
		grp.setNo_grupo(rs.getString("no_grupo"));
		return grp;
	}

	private Secao instantiateSecao(ResultSet rs) throws SQLException {
		Secao sec = new Secao();
		sec.setId(rs.getInt("id_secao"));
		sec.setNo_secao(rs.getString("no_secao"));
		return sec;
	}

	private Departamento instantiateDepartamento(ResultSet rs) throws SQLException {
		 	Departamento dep = new Departamento();
			dep.setId(rs.getInt("id_departamento"));
			dep.setNo_departamento(rs.getString("no_departamento"));
			return dep;
	}

	@Override
	public List<Produto> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select * from produto " 
					+ "join departamento on departamento.id  = produto.id_departamento "
					+ "join secao on secao.id = produto.id_secao "
					+ "join grupo on grupo.id = produto.id_grupo "
					+ "join subgrupo on subgrupo.id = produto.id_subgrupo "
					+ "join fornecedor on fornecedor.id = produto.id_subgrupo "
					+ "order by departamento.no_departamento"
					);
			
			rs = st.executeQuery();
			
			List <Produto> list = new ArrayList<>();
			Map <Integer, Departamento> map = new HashMap<>();
			
			while (rs.next()) {
				// Cria um MAP como chave o Id do departamento
				// Cria uma tabela (id e dep)
				Departamento dep = map.get(rs.getInt("id_departamento"));

				// Se não existir um Departmento instanciado, instancia. 
				if (dep == null) {
					dep =  instantiateDepartamento(rs);
					// salva dep no MAP
					map.put(rs.getInt("id_departamento"), dep);
				}
				
				// Não fiz igual ao Departamento
				// Depois vou entender pq
				Secao sec = instantiateSecao(rs);
				Grupo grp = instantiateGrupo(rs);
				SubGrupo subG = instantiateSubGrupo(rs);
				Fornecedor forn = instantiateFornecedor(rs);
				
				Produto obj = instantiateProduto(rs,dep,sec,grp,subG,forn);
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

	@Override
	public List<Produto> findByDepartamento(Departamento departamento) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select * from produto " 
					+ "join departamento on departamento.id  = produto.id_departamento "
					+ "join secao on secao.id = produto.id_secao "
					+ "join grupo on grupo.id = produto.id_grupo "
					+ "join subgrupo on subgrupo.id = produto.id_subgrupo "
					+ "join fornecedor on fornecedor.id = produto.id_subgrupo "
					+ "where produto.id_departamento = ? "
					+ "order by departamento.no_departamento"
					);
			
			st.setInt(1, departamento.getId());
			rs = st.executeQuery();
			
			List <Produto> list = new ArrayList<>();
			Map <Integer, Departamento> map = new HashMap<>();
			
			while (rs.next()) {
				// Cria um MAP como chave o Id do departamento
				// Cria uma tabela (id e dep)
				Departamento dep = map.get(rs.getInt("id_departamento"));

				// Se não existir um Departmento instanciado, instancia. 
				if (dep == null) {
					dep =  instantiateDepartamento(rs);
					// salva dep no MAP
					map.put(rs.getInt("id_departamento"), dep);
				}
				
				// Não fiz igual ao Departamento
				// Depois vou entender pq
				Secao sec = instantiateSecao(rs);
				Grupo grp = instantiateGrupo(rs);
				SubGrupo subG = instantiateSubGrupo(rs);
				Fornecedor forn = instantiateFornecedor(rs);
				
				//Produto obj = instantiateProduto(rs,dep,sec,grp,subG,forn);
				Produto obj = instantiateProduto(rs,dep,null,null,null,null);
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

}
