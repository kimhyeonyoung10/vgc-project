package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Reserves;
/**
 * DB를 사용하여 reserve 테이블의 데이터를 조작하는 ReserveDao클래스
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 *
 */
public class ReserveDao {
	public ReserveDao() {
	}
	
	private static ReserveDao instance = new ReserveDao();
	/**
	 * 싱글톤 생성
	 * 
	 * @return instance ReserveDao객체 반환
	 */
	public static ReserveDao getInstance() {
		return instance;
	}

	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	private String sql;
	ArrayList<Reserves> reserve1;
	/**
	 * 
	 * movieId와 reserveDate를 이용하여 reserve 테이블에서 유저들이 선택한 모든 좌석을 추출한다.<br/>
	 * <ol>
	 * <li>DB연결</li>
	 * <li>sql 전송 후 실행</li>
	 * <li>executeQuery 실행 후 결과 집합 반환</li>
	 * </ol>
	 * 
	 * @param movieId     영화 고유번호
	 * @param reserveDate 예약한날짜
	 * @return 예약된 좌석 반환
	 * 
	 */
	public Reserves selectSeatAll(int movieId,String reserveDate) {
		
		sql = "SELECT GROUP_CONCAT(seat SEPARATOR ',') FROM reserve where movie_id= ? and reserve_Date = ?";
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
		
			pstmt.setInt(1, movieId);
			pstmt.setString(2, reserveDate);
			rs = pstmt.executeQuery();
			
			Reserves reserve = new Reserves();
			
			while (rs.next()) {
				reserve.setSeat(rs.getString(1));	
			}
	
			conn.close();
			return reserve;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	/**
	 * 
	 * userId, reserveId, selectedSeats을 이용하여 reserve 테이블에서 모든 정보를 추출한다.<br/>
	 * <ol>
	 * <li>DB연결</li>
	 * <li>sql 전송 후 실행</li>
	 * <li>executeQuery 실행 후 결과 집합 반환</li>
	 * </ol>
	 * 
	 * @param userid        유저아이디
	 * @param reserveId     예약번호
	 * @param selectedSeats 좌석
	 * @return reserve변수 반환
	 * 
	 */
	public Reserves selectOne(String userid, int reserveId,String selectedSeats) {
		
		sql = "SELECT * FROM reserve where user_id = ? and movie_id= ? and seat = ?";
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setInt(2, reserveId);
			pstmt.setString(3, selectedSeats);
			rs = pstmt.executeQuery();
			
			Reserves reserve = new Reserves();
			
			if (rs.next()) {
				reserve.setId(rs.getInt(1));
				reserve.setUserId(rs.getString(2));
				reserve.setMovieId(rs.getInt(3));
				reserve.setReserveDate(rs.getString(4));
				reserve.setReserveTime(rs.getString(5));
				reserve.setReserveCnt(rs.getInt(6));
				reserve.setSeat(rs.getString(7));
			}

			conn.close();
			return reserve;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	/**
	 * 
	 * userId와 reserveTime를 이용하여 reserve 테이블에서 예약 상세정보에 들어갈 정보를 추출한다.<br/>
	 * <ol>
	 * <li>DB연결</li>
	 * <li>sql 전송 후 실행</li>
	 * <li>executeQuery 실행 후 결과 집합 반환</li>
	 * </ol>
	 * 
	 * @param userid      유저아이디
	 * @param reserveTime 예약한날짜
	 * @return reserve변수 반환
	 */	
public Reserves selectOneDetails(String userid, String reserveTime) {
		
		sql = "SELECT * FROM reserve where user_id = ? and reserve_Time= ? ";
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, reserveTime);
			rs = pstmt.executeQuery();
			
			Reserves reserve = new Reserves();
			if (rs.next()) {
				reserve.setId(rs.getInt(1));
				reserve.setUserId(rs.getString(2));
				reserve.setMovieId(rs.getInt(3));
				reserve.setReserveDate(rs.getString(4));
				reserve.setReserveTime(rs.getString(5));
				reserve.setReserveCnt(rs.getInt(6));
				reserve.setSeat(rs.getString(7));
			}

			conn.close();
			return reserve;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
/**
 * 
 * userId를 이용하여 reserve테이블에서 특정 정보를 추출한다.<br/>
 * <ol>
 * <li>DB연결</li>
 * <li>sql 전송 후 실행</li>
 * <li>executeQuery 실행 후 결과 집합 반환</li>
 * </ol>
 * 
 * @param userid 유저아이디
 * @return arrayList배열 생성 후 추출값을 추가 후 그 배열(reserve1)을 반환
 */
	public List<Reserves> selectMovieId(String userid){
		sql = "SELECT id, movie_Id, reserve_Date FROM reserve where user_id = ?";
		reserve1 = new ArrayList<Reserves>();
		Reserves r = new Reserves();
		
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				r.setId(rs.getInt(1));
				r.setMovieId(rs.getInt(3));
				r.setReserveDate(rs.getString(4));
				reserve1.add(r);
				System.out.println(reserve1);
			}
		} catch (Exception e) {
			
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return reserve1;
	}
}