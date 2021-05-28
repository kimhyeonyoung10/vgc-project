package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.Seats;
/**
 * DB를 사용하여 seat 테이블의 데이터를 조작하는 SeatDao클래스
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 *
 */
public class SeatDao {
	private SeatDao() {}
	
	private static SeatDao instance = new SeatDao();
	/**
	 * 
	 * 싱글톤 생성
	 * 
	 * @return instance SeatDao객체 반환
	 */

	public static SeatDao getInstance() {
		return instance;
	}
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private ResultSet rs;
	/**
	 * id를 이용하여 seat테이블의 원하는 모든 정보를 추출한다.<br/>
	 * <ol>
	 * <li>DB연결</li>
	 * <li>sql 전송 후 실행</li>
	 * <li>executeQuery 실행 후 결과 집합 반환</li>
	 * </ol>
	 * 
	 * @param id 좌석 고유번호
	 * @return seat변수 반환
	 */
	public Seats selectOne(int id) {
		String sql = "SELECT * FROM seat WHERE id = ?";
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			Seats seat = new Seats();
			if(rs.next()) {
				seat.setId(rs.getInt("ID"));
				seat.setRow(rs.getInt("SEAT_ROW"));
				seat.setCol(rs.getInt("SEAT_COL"));
				
				conn.close();
				return seat;
			}else {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}