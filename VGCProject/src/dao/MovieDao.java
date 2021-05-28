package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Movies;
/**
 * DB를 사용하여 movie 테이블의 데이터를 조작하는 MovieDao클래스
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 *
 */
public class MovieDao {
	/**
	 * 기본생성자
	 */
	public MovieDao() {}
	/**
	 * 싱글톤 생성
	 */
	private static MovieDao instance = new MovieDao();
	/**
	 * 
	 * @return instance MovieDao객체 반환
	 */
	public static MovieDao getInstance() {
		return instance;
	}
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private ResultSet rs;
	/**
	 * id를 이용하여 movie테이블안의 id, title, running_Time을 추출한다.<br/>
	 * <ol>
	 * <li>DB연결</li>
	 * <li>sql 전송 후 실행</li>
	 * <li>executeQuery 실행 후 결과 집합 반환</li>
	 * </ol>
	 * 
	 * @param id 영화 고유번호
	 * @return movie변수 반환
	 */
	public Movies selectOne(int id) {
		
		String sql = "SELECT * FROM movie WHERE ID = ?";
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			Movies movie = new Movies();
			if(rs.next()) {
				movie.setId(rs.getInt("ID"));
				movie.setTitle(rs.getString("TITLE"));
				movie.setRunningTime(rs.getInt("RUNNING_TIME"));
				conn.close();
				return movie;
			}else {
				conn.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * id(영화 고유번호)를 이용하여 movie테이블안의 title, running_Time 추출한다.<br/>
	 * <ol>
	 * <li>DB연결</li>
	 * <li>sql 전송 후 실행</li>
	 * <li>executeQuery 실행 후 결과 집합 반환</li>
	 * </ol>
	 * 
	 * @param id 영화 고유번호
	 * @return arrayList배열 생성 후 추출값을 추가 후 그 배열(movieInfo)을 반환
	 */
	public List<Movies> selcetWant(int id) {
		String sql = "SELECT title, running_Time FROM movie WHERE id = ?";
		ArrayList<Movies> movieInfo = new ArrayList<Movies>(); 
		Movies m = new Movies();
		
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				m.setTitle(rs.getString("TITLE"));
				m.setRunningTime(rs.getInt("RUNNING_TIME"));
				movieInfo.add(m);
				conn.close();
				
			}else {
				conn.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return movieInfo;
	}
	
	public int selectId(String movieTitle) {
		String sql = "SELECT id FROM movie WHERE title = ?";
		Movies m = new Movies();
		
		conn = DBConnection.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, movieTitle);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				m.setId(rs.getInt(1));
				conn.close();
				
			}else {
				conn.close();
				return m.getId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return m.getId();
	}
}