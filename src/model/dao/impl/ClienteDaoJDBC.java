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
import model.entities.Departamento;
import model.entities.Fornecedor;
import model.entities.Grupo;
import model.entities.Produto;
import model.entities.Secao;
import model.entities.SubGrupo;
import model.entities.UF;

public class ClienteDaoJDBC implements ClienteDao {
	


    private Connection conn;

    public ClienteDaoJDBC(Connection conn) {
                this.conn=conn;
    }
	private Cliente instantiateCliente(ResultSet rs, UF uf) 
			  throws SQLException {
		Cliente obj = new Cliente();
		obj.setId(rs.getInt("id"));
		obj.setNo_cliente(rs.getString("no_cliente"));
		obj.setNo_apelido(rs.getString("no_apelido"));
		obj.setDt_nascimento(rs.getDate("dt_nascimento"));
		obj.setFl_sexo(rs.getString("fl_sexo"));
		obj.setNo_email1(rs.getString("no_email1"));
		obj.setNo_email2(rs.getString("no_email2"));
		obj.setNr_telefone1(rs.getString("nr_telefone1"));
		obj.setNr_telefone2(rs.getString("nr_telefone2"));
		obj.setNr_cep(rs.getString("nr_cep"));
		obj.setNo_endereco(rs.getString("no_endereco"));
		obj.setNr_numero(rs.getInt("nr_numero"));
		obj.setNo_complemento(rs.getString("no_complemento"));
		obj.setNo_cidade(rs.getString("no_cidade"));
		obj.setUF(uf);
		obj.setNo_observacao(rs.getString("no_observacao"));
		obj.setNo_bairro(rs.getString("no_bairro"));
		obj.setNr_documento(rs.getString("nr_documento"));
		return obj;
	}
    
	private UF instantiateUF(ResultSet rs) throws SQLException {
		UF uf = new UF();
		uf.setSg_uf(rs.getString("sg_uf"));
		uf.setNo_unidade(rs.getString("no_unidade"));
		return uf;
	}
	
	@Override
	public void insert(Cliente obj) {
		PreparedStatement st = null;
		try {
				st = conn.prepareStatement("insert into cliente "
							+ " (no_cliente, no_apelido, dt_nascimento, fl_sexo, "
							+ " no_email1, no_email2, nr_telefone1, nr_telefone2, "
							+ " nr_cep, no_endereco, nr_numero, no_complemento, "
							+ " no_cidade, sg_uf, no_observacao, no_bairro, nr_documento) "
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
				st.setString(14, obj.getUF().getSg_uf());
				st.setString(15, obj.getNo_observacao());
				st.setString(16, obj.getNo_bairro());
				st.setString(17,obj.getNr_documento());

				
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
				st = conn.prepareStatement("update  cliente set "
							+ "no_cliente = ?, "
							+ " no_apelido = ?, "
							+ " dt_nascimento = ?, "
							+ " fl_sexo = ?, "
							+ " no_email1 = ?, "
							+ " no_email2 = ?, "
							+ " nr_telefone1 = ?, "
							+ " nr_telefone2 = ?, "
							+ " nr_cep = ? , "
							+ " no_endereco = ?, "
							+ " nr_numero = ?, "
							+ " no_complemento = ?, "
							+ " no_cidade = ?, "
							+ " sg_uf = ?, "
							+ " no_observacao = ?, "
							+ " no_bairro = ? , "
							+ " nr_documento = ? "
							+ "where id = ? "
								);

				st.setString(1, obj.getNo_cliente());
				st.setString(2, obj.getNo_apelido());
				st.setDate(3,obj.getDt_nascimento());
				st.setString(4, obj.getFl_sexo());
				st.setString(5, obj.getNo_email1());
				st.setString(6, obj.getNo_email2());
				st.setString(7, obj.getNr_telefone1());
				st.setString(8, obj.getNr_telefone2());
				st.setString(9, obj.getNr_cep());
				st.setString(10, obj.getNo_endereco());
				st.setInt(11, obj.getNr_numero());
				st.setString(12, obj.getNo_complemento());
				st.setString(13, obj.getNo_cidade());
				st.setString(14, obj.getUF().getSg_uf());
				st.setString(15, obj.getNo_observacao());
				st.setString(16, obj.getNo_bairro());
				st.setString(17, obj.getNr_documento());

				
				
				st.setInt(18,obj.getId());


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
				st = conn.prepareStatement("delete from cliente  "
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
								"select * from cliente  "
								+ "where id = ?"
								);
				st.setInt(1, id);
				rs = st.executeQuery();
				if (rs.next()) {
					//Cliente obj = instantiateCliente(rs);
					UF uf = instantiateUF(rs);
					
					Cliente obj = instantiateCliente(rs,uf);
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
								"select * from cliente "
								+ "left join unidade_federacao "
								+ "on  unidade_federacao.sg_uf = cliente.sg_uf"
								);
				
				rs = st.executeQuery();
				List <Cliente> list = new ArrayList<>();

				while (rs.next()) {
				//	Cliente obj = instantiateCliente(rs);
					UF uf = instantiateUF(rs);
					
					Cliente obj = instantiateCliente(rs,uf);
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

	@Override
	public List<Cliente> findByNome(String no_cliente) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
				st = conn.prepareStatement(
				  			  "select * from cliente  "
							+ " where no_cliente like ?"
								);
				st.setString(1, ("%" + no_cliente + "%"));
				//st.setString(1, no_cliente);
				
				

				rs = st.executeQuery();
				
				System.out.println("Veja ---> " + rs.getRow());
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


}
