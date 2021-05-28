package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.Users;

/**
 * DB를 사용하여 user 데이터를 조작하는 UserDao클래스
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 */
public class UserDao {
	private UserDao() {}
	
	private static UserDao instance = new UserDao();
	/**
	 * 싱글톤 생성
	 * @return instance UserDao객체 반환
	 */
	public static UserDao getInstance() {
		return instance;
	}
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	private static boolean checkId = false;
	/**
	 * userId를 이용하여 user테이블안의 id, user_id, birth_date, phone을 추출한다.<br/>
	 * <ol>
	 * <li> DB연결 </li>
	 * <li> sql 전송 후 실행 </li>
	 * <li> executeQuery 실행 후 결과 집합 반환</li>
	 * </ol>
	 * 
	 * @param userId 유저아이디
	 * @return user변수 반환
	 */
	public Users selectOne(String userId) {
		String sql = "SELECT * FROM user WHERE USER_ID = ?";
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			Users user = new Users();
			if(rs.next()) {
				user.setId(rs.getInt("ID"));
				user.setUserId(rs.getString("USER_ID"));
				user.setBirthDate(rs.getInt("BIRTH_DATE"));
				user.setPhone(rs.getString("PHONE"));
				
				conn.close();
				return user;
			}else {
				conn.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean CheckId(String userId) {
		String sql = "SELECT * FROM user WHERE USER_ID = ?";
		conn = DBConnection.getConnection();
		boolean checkid = false;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			checkid = rs.next();
			return checkid;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkid;
	}
	
}