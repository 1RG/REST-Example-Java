package lt.rg.example.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.LinkedList;

import lt.rg.example.controler.Element;
import lt.rg.example.controler.Spalva;

public class DB_Queries {
	/** CONFIG */
	
	public String driver = "org.mariadb.jdbc.Driver";
	public String url = "jdbc:mariadb://localhost:3306/testmariadb_java?useUnicode=yes&characterEncoding=UTF-8";
	public String user = "root";
	public String pass = "root";
	
	/** SELECT */
	
	public LinkedList<Element> getAllData() {
		LinkedList<Element> elements = new LinkedList<Element>();
		
		Connection connection = null;
		Statement stmt = null;
		
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, pass);
			stmt = connection.createStatement();
			
			String sql = "SELECT t.id, t.name, t.kiekis, s.pavadinimas AS 'spalva' FROM test_table t, spalva s WHERE t.spalva_id = s.id ORDER BY t.id";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				elements.add(new Element(rs.getInt("id"), rs.getString("name"), rs.getInt("kiekis"), rs.getString("spalva")));
			}

			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				stmt.close();
			} catch (Exception e2) {
				System.err.println(e2);
			}
			try {
				connection.close();
			} catch (Exception e2) {
				System.err.println(e2);
			}
		}
		
		return elements;
	}

	public Element getDataById(int id){
		Element element = null;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, pass);
			
			String sql = "SELECT t.id, t.name, t.kiekis, s.pavadinimas AS 'spalva' FROM test_table t, spalva s WHERE t.spalva_id = s.id  AND t.id = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, id);
					
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				element = new Element(rs.getInt("id"), rs.getString("name"), rs.getInt("kiekis"), rs.getString("spalva"));
			}

			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				pstmt.close();
			} catch (Exception e2) {
				System.err.println(e2);
			}
			try {
				connection.close();
			} catch (Exception e2) {
				System.err.println(e2);
			}
		}
		
		return element;
	}

	public LinkedList<Spalva> getAllSpalva() {
		LinkedList<Spalva> spalvas = new LinkedList<Spalva>();
		
		Connection connection = null;
		Statement stmt = null;
		
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, pass);
			stmt = connection.createStatement();
			
			String sql = "SELECT id, pavadinimas FROM spalva";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				spalvas.add(new Spalva(rs.getInt("id"), rs.getString("pavadinimas")));
			}

			rs.close();
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				stmt.close();
			} catch (Exception e2) {
				System.err.println(e2);
			}
			try {
				connection.close();
			} catch (Exception e2) {
				System.err.println(e2);
			}
		}
		
		return spalvas;
	}

	/** UPDATE */
	
	public Element setDataById(int id, String name, int kiekis, int spalva_id) {
		Element element = null;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, pass);
			
			String sql_up = "UPDATE test_table SET name = ?, kiekis = ?, spalva_id = ? WHERE id = ?";
			
			pstmt = connection.prepareStatement(sql_up);
			pstmt.setString(1, name);
			pstmt.setInt(2, kiekis);
			pstmt.setInt(3, spalva_id);
			pstmt.setInt(4, id);
			
			int row_count = pstmt.executeUpdate();
			pstmt.close();
			
			if(row_count == 1) {
				String sql = "SELECT t.id, t.name, t.kiekis, s.pavadinimas AS 'spalva' FROM test_table t, spalva s WHERE t.spalva_id = s.id  AND t.id = ?";
				
				pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, id);
				
				ResultSet rs = pstmt.executeQuery();
						
				while(rs.next()){
					element = new Element(rs.getInt("id"), rs.getString("name"), rs.getInt("kiekis"), rs.getString("spalva"));
				}

				rs.close();
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				pstmt.close();
			} catch (Exception e2) {
				System.err.println(e2);
			}
			try {
				connection.close();
			} catch (Exception e2) {
				System.err.println(e2);
			}
		}
		
		return element;
	}

	/** INSERT */
	
	public Element addData(String name, int kiekis, int spalva_id) {
		Element element = null;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, pass);
			
			String sql_up = "INSERT INTO test_table VALUES(null, ?, ?, ?)";
			
			pstmt = connection.prepareStatement(sql_up);
			pstmt.setString(1, name);
			pstmt.setInt(2, kiekis);
			pstmt.setInt(3, spalva_id);
			
			int row_count = pstmt.executeUpdate();
			
			ResultSet rs_id = pstmt.getGeneratedKeys();

			
			int new_id = 0;
			
			while(rs_id.next()){
				new_id = rs_id.getInt(1);
			}

			rs_id.close();
			pstmt.close();
			
			if(row_count == 1 && new_id != 0) {
				String sql = "SELECT t.id, t.name, t.kiekis, s.pavadinimas AS 'spalva' FROM test_table t, spalva s WHERE t.spalva_id = s.id  AND t.id = ?";
				
				pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, new_id);
				
				ResultSet rs = pstmt.executeQuery();
						
				while(rs.next()){
					element = new Element(rs.getInt("id"), rs.getString("name"), rs.getInt("kiekis"), rs.getString("spalva"));
				}

				rs.close();
			}
			
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				pstmt.close();
			} catch (Exception e2) {
				System.err.println(e2);
			}
			try {
				connection.close();
			} catch (Exception e2) {
				System.err.println(e2);
			}
		}
		
		return element;
	}
	
	/** DELETE */
	
	public boolean removeDataById(int id) {
		boolean r_status = false;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, pass);
			
			String sql = "DELETE FROM test_table WHERE id = ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			int row_count = pstmt.executeUpdate();
			pstmt.close();
			
			if(row_count == 1) {
				
				r_status = true;
				
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				pstmt.close();
			} catch (Exception e2) {
				System.err.println(e2);
			}
			try {
				connection.close();
			} catch (Exception e2) {
				System.err.println(e2);
			}
		}
		
		return r_status;
	}
}
