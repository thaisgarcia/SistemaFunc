
package dao;

import modelo.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import principal.ConnectionFactory;

/**
 *
 * @author geraldo
 */
public class FuncionarioDAO {
    private Connection conexao=null;
    
    public FuncionarioDAO() throws SQLException {

		this.conexao = new ConnectionFactory().getConnection();
		
	}
    
    public void adicionar(Funcionario funcionario) throws SQLException {
                this.conexao.setAutoCommit(false);
                try{
                
                    PreparedStatement pstmt = this.conexao
                                    .prepareStatement("insert into tbl_funcionarios " +
                                                    "(nome,sobrenome,email,idade,salario,"
                                                  + "cidade,estado,estadocivil) values (?,?,?,?,?,?,?,?)");

                    pstmt.setString(1, funcionario.getNome());
                    pstmt.setString(2, funcionario.getSobrenome());
                    pstmt.setString(3, funcionario.getEmail());
                    pstmt.setInt(4, funcionario.getIdade());
                    pstmt.setDouble(5, funcionario.getSalario());
                    pstmt.setString(6, funcionario.getCidade());                
                    pstmt.setString(7, funcionario.getEstado());
                    pstmt.setString(8, funcionario.getEstCivil());

                    pstmt.execute();

                    this.conexao.commit();
                    pstmt.close();
                }
		catch(SQLException ex){
                    System.out.println(ex.getMessage());
                    this.conexao.rollback();
                }		
		
                
                this.conexao.close();

	}       

	
	
	
	public List<Funcionario> getLista() throws SQLException {

		
		
		PreparedStatement pstmt = this.conexao.prepareStatement("select * from tbl_funcionarios");
		
		ResultSet rs = pstmt.executeQuery();
		
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		while (rs.next()) { 
			
			Funcionario funcionario = new Funcionario();
			funcionario.setId(rs.getInt("id"));
			funcionario.setNome(rs.getString("nome"));
                        funcionario.setSobrenome(rs.getString("sobrenome"));
			funcionario.setEmail(rs.getString("email"));
                        funcionario.setIdade(rs.getInt("idade"));
                        funcionario.setSalario(rs.getDouble("salario"));
                        funcionario.setCidade(rs.getString("cidade"));
			funcionario.setEstado(rs.getString("estado"));
                        funcionario.setEstCivil(rs.getString("estadocivil"));
			
			funcionarios.add(funcionario);
		}
	
		rs.close();
		
		pstmt.close();

                this.conexao.close();
                
		return funcionarios;
	}

        
        public List<Funcionario> getListaOrdemAlfabetica() throws SQLException {

		
		
		PreparedStatement stmt = this.conexao.prepareStatement("select * from tbl_funcionarios order by nome");
		
		ResultSet rs = stmt.executeQuery();
		
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		while (rs.next()) { 
			
			Funcionario funcionario = new Funcionario();
			funcionario.setId(rs.getInt("id"));
			funcionario.setNome(rs.getString("nome"));
                        funcionario.setSobrenome(rs.getString("sobrenome"));
			funcionario.setEmail(rs.getString("email"));
                        funcionario.setIdade(rs.getInt("idade"));
                        funcionario.setSalario(rs.getDouble("salario"));
                        funcionario.setCidade(rs.getString("cidade"));
			funcionario.setEstado(rs.getString("estado"));
			funcionario.setEstCivil(rs.getString("estadocivil"));
			funcionarios.add(funcionario);
		}
	
		rs.close();
		
		stmt.close();

                this.conexao.close();
                
		return funcionarios;
	}
        
	public Funcionario pesquisarID(int id) throws SQLException {

		Funcionario funcionario = new Funcionario();
		PreparedStatement stmt = this.conexao
                    .prepareStatement("select * from tbl_funcionarios where id=?");
		
                stmt.setInt(1, id);

             
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			funcionario.setId(rs.getInt("id"));
			funcionario.setNome(rs.getString("nome"));
                        funcionario.setSobrenome(rs.getString("sobrenome"));
			funcionario.setEmail(rs.getString("email"));
                        funcionario.setIdade(rs.getInt("idade"));
                        funcionario.setSalario(rs.getDouble("salario"));
                        funcionario.setCidade(rs.getString("cidade"));
			funcionario.setEstado(rs.getString("estado"));
                        funcionario.setEstCivil(rs.getString("estadocivil"));
		}

		return funcionario;
	}

	public void alterar(Funcionario funcionario) throws SQLException {

		PreparedStatement stmt = this.conexao.prepareStatement("update tbl_funcionarios set nome=?,sobrenome=?,email=?, "
                        + "idade=?,salario=?,cidade=?,estado=?,estadocivil=? where id=?");
		stmt.setString(1, funcionario.getNome());
                stmt.setString(2, funcionario.getSobrenome());
		stmt.setString(3, funcionario.getEmail());
                stmt.setInt(4, funcionario.getIdade());
                stmt.setDouble(5, funcionario.getSalario());
		stmt.setString(6, funcionario.getCidade());
                stmt.setString(7, funcionario.getEstado());
		stmt.setString(8, funcionario.getEstCivil());
                stmt.setInt(9, funcionario.getId());
                
		stmt.execute();
		stmt.close();

                this.conexao.close();
	}
	
	public void remover(int id) throws SQLException {

		PreparedStatement stmt = this.conexao.prepareStatement("delete from tbl_funcionarios where id=?");
		
		stmt.setInt(1, id);
		stmt.execute();
		stmt.close();

                this.conexao.close();

		
	}

    public void aumentarSalario(double porcentagem) {
        try {
            Map<Integer, Double> mapa = new HashMap<Integer, Double>();
            
            PreparedStatement pstmt = this.conexao.prepareStatement("select id, salario from tbl_funcionarios");
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                mapa.put(rs.getInt("id"),rs.getDouble("salario"));
            }
            
            pstmt = this.conexao.prepareStatement("update tbl_funcionarios "
                    + " set salario = ? "
                    + " where id = ?");
            
            double novoSal=0;
            
            for (Integer i: mapa.keySet()){
                System.out.println("chave: "+i+"\tsalario: "+mapa.get(i));
                novoSal = mapa.get(i)+ mapa.get(i)*porcentagem/100;
                System.out.println("novoSal: "+novoSal);
                pstmt.setDouble(1, novoSal);
                pstmt.setInt(2, i);
                pstmt.executeUpdate();
            }
            
            
            pstmt.close();
            this.conexao.close();
            
        } catch (SQLException ex) {
            System.out.println("não inseriu porque: "+ex.getMessage());
        }
			
    }
  
}
