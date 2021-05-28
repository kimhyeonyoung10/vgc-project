package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.MovieDetails;


/**
 * DB를 사용하여 movieDetails 테이블의 데이터를 조작하는 MovieDetailDao클래스
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 *
 */
public class MovieDetailDao {
	public MovieDetailDao() {}
	
	private static MovieDetailDao instance = new MovieDetailDao();
	
	/**
	 * 싱글톤 생성
	 * 
	 * @return instance MovieDetailDao객체 반환
	 */
	public static MovieDetailDao getInstance() {
		return instance;
	}
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	private String sql;
	
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
	 * 
	 * @return movieInfo에 담긴 모든 영화정보를 반환
	 * 
	 */
	public MovieDetails selectMoiveInfo(int movieID) {
		
		sql = "SELECT * FROM movie_Info where movie_id= ?";
		conn = DBConnection.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, movieID);
			rs = pstmt.executeQuery();
			
			MovieDetails movieInfo = new MovieDetails();
			
			if (rs.next()) {
				movieInfo.setId(rs.getInt(1));
				movieInfo.setMovieId(rs.getInt(2));
				movieInfo.setDirector(rs.getString(3));
				movieInfo.setActor(rs.getString(4));
				movieInfo.setGrade(rs.getString(5));
				movieInfo.setPoster(rs.getString(6));
			}

			conn.close();
			return movieInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
}