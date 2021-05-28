package dao;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * mariaDB와 java 연결해주는 클래스
 * 
 * <p>
 * mariadb-java-client-2.5.4.jar 라이브러리를 참조하여 DB와 java를 연결해준다.<br/>
 * 데이터베이스 드라이버를 로드시키고 주소와 데이터베이스 유저이름과 비밀번호를 이용하여 연결해준다.
 * </p>
 * 
 * @version 1.0.0
 * @author KimHyunYoung, JungHoJune
 * 
 */
public class DBConnection {
	private final static String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private final static  String DB_URL = "jdbc:mysql://localhost:3306/VGC_Project?" + "useUnicode=true"
			+ "&characterEncoding=utf8";

	private final static String USERNAME = "root";
	private final static String PASSWORD = "1234";

	public static Connection getConnection() {

		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}