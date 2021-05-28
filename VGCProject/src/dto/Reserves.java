package dto;

/**
 * Reserves dto<br/>
 * Reserves의 순수한 데이터 객체로 속성에 접근하기 위한 getter, setter 메소드만 가짐.
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 *
 */
public class Reserves {
	private int id;
	private String userId;
	private int movieId;
	private String reserveDate;
	private String reserveTime;
	private int reserveCnt;
	private String seat;

	public Reserves() {
	}
	/**
	 * Reserves 생성자
	 * @param id 예약번호
	 * @param userId user아이디
	 * @param movieId 영화고유번호
	 * @param reserveDate 상영날짜
	 * @param reserveTime 예약한날짜
	 * @param reserveCnt 예약인원
	 * @param seat 좌석
	 */
	public Reserves(int id, String userId, int movieId, String reserveDate, String reserveTime, int reserveCnt,
			String seat) {

		this.id = id;
		this.userId = userId;
		this.movieId = movieId;
		this.reserveDate = reserveDate;
		this.reserveTime = reserveTime;
		this.reserveCnt = reserveCnt;
		this.seat = seat;
	}

	/**
	 * 필드 id의 accessor
	 * @return id를 int형으로 반환
	 */
	public int getId() {
		return id;
	}
	/**
	 * 필드 id의 mutator
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}	
	/**
	 * 필드 userId의 accessor
	 * @return userId를 String형으로 반환
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 필드 userId의 mutator
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 필드 movieId의 accessor
	 * @return movieId를 int형으로 반환
	 */
	public int getMovieId() {
		return movieId;
	}
	/**
	 * 필드 movieId의 mutator
	 * @param movieId
	 */
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	/**
	 * 필드 reserveDate의 accessor
	 * @return reserveDate를 String형으로 반환
	 */
	public String getReserveDate() {
		return reserveDate;
	}
	/**
	 * 필드 reserveDate의 mutator
	 * @param reserveDate
	 */
	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}
	/**
	 * 필드 reserveTime의 accessor
	 * @return reserveTime을 String형으로 반환
	 */
	public String getReserveTime() {
		return reserveTime;
	}
	/**
	 * 필드 reserveTime의 mutator
	 * @param reserveTime
	 */
	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}
	/**
	 * 필드 reserveCnt의 accessor
	 * @return reserveCnt을 int형으로 반환
	 */
	public int getReserveCnt() {
		return reserveCnt;
	}
	/**
	 * 필드 reserveCnt의 mutator
	 * @param reserveCnt
	 */
	public void setReserveCnt(int reserveCnt) {
		this.reserveCnt = reserveCnt;
	}
	/**
	 * 필드 seat의 accessor
	 * @return seat을 String형으로 반환
	 */
	public String getSeat() {
		return seat;
	}
	/**
	 * 필드 seat의 mutator
	 * @param seat
	 */
	public void setSeat(String seat) {
		this.seat = seat;
	}
}