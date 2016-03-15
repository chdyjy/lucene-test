package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class M {

	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/mongo";
	private String user = "root";
	private String password = "69431589";

	private Connection conn;
	private String table = null;
	private String condition;
	private String field = "*";
	private int limit = 1;

	private ResultSet rs = null;
	
	public M(){
		try {
			Class.forName(driver);
			this.conn = DriverManager.getConnection(this.url, this.user, this.password);
			if (!this.conn.isClosed()) {
				System.out.println("[static] Succeeded connecting to the Database!");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public M(String table) {
		try {
			this.table = table;
			Class.forName(driver);
			this.conn = DriverManager.getConnection(this.url, this.user, this.password);
			if (!this.conn.isClosed()) {
				System.out.println("[static] Succeeded connecting to the Database!");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public M where(String condition) {
		this.condition = condition;
		return this;
	}

	public M field(String field) {
		if (!field.isEmpty()) {
			this.field = field;
		}
		return this;
	}

	public M limit(int limit) {
		this.limit = limit;
		return this;
	}

	private String constructSQL() {
		String sql = null;
		sql = "SELECT " + this.field + " FROM `" + this.table + "` WHERE 1=1 " + this.condition + " LIMIT "
				+ this.limit;
		return sql;
	}

	public ResultSet select() {
		ResultSet rs = null;
		try {
			Statement statement = this.conn.createStatement();
			String sql = this.constructSQL();
			System.out.println("[sql] " + sql);
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// select ... limit 1
	public ResultSet find(String condition) {
		ResultSet rs = null;
		this.limit = 1;
		try {
			Statement statement = this.conn.createStatement();
			String sql = this.constructSQL();
			System.out.println("[sql] " + sql);
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// SQL query
	public ResultSet query(String sql) {
		ResultSet rs = null;
		try {
			Statement statement = this.conn.createStatement();
			System.out.println("[sql] " + sql);
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void close() {
		try {
			this.rs.close();
			this.conn.close();
			System.out.println("[mysql] connection closed.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/*testQuery,use it in Lucene*/
	public ResultSet testQuery(){
		ResultSet rs = null;
		String sql = "SELECT"
				+ " cp_member_files.file_list_id,file_name,file_content,member_file_name,member_file_ext"
				+ " FROM cp_file_list INNER JOIN cp_member_files"
				+ " ON cp_file_list.file_list_id = cp_member_files.file_list_id"
				+ " WHERE file_content IS NOT NULL";
		rs = this.query(sql);
		return rs;
	}
	
	public static void main(String[] args) {

		//M model = new M("cp_member_files");
		//String condition = "{'':'2312',}";
		//model.rs = model.where("").field("").limit(5).select();

		
		M model = new M();
		
		String sql = "SELECT"
				+ " cp_member_files.file_list_id,file_name,file_content,member_file_name,member_file_ext"
				+ " FROM cp_file_list INNER JOIN cp_member_files"
				+ " ON cp_file_list.file_list_id = cp_member_files.file_list_id"
				+ " WHERE file_content IS NOT NULL"
				+ " LIMIT 0,20";
		
		model.rs = model.query(sql);
		try {
			while (model.rs.next()) {
				System.out.println("[result]" + model.rs.getString("member_file_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		model.close();
	}

}


