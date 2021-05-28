package dto;
/**
 * MovieDetails dto<br/>
 * MovieDetails의 순수한 데이터 객체로 속성에 접근하기 위한 getter, setter 메소드만 가짐.
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 *
 */
public class MovieDetails {
	private int id;
	private int movieId;
	private String Director;
	private String Actor;
	private String grade;
	private String poster;
	
	public MovieDetails() {}
	
	/**
	 * 
	 * @param id 예약번호
	 * @param movieId 영화고유번호
	 * @param Director 감독
	 * @param Actor 배우
	 * @param grade 평점
	 * @param poster 포스터URL
	 */
	public MovieDetails(int id, int movieId, String Director, String Actor,String grade, String poster) {
		this.id = id;
		this.movieId = movieId;
		this.Director = Director;
		this.Actor = Actor;
		this.grade = grade;
		this.poster = poster;
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
	 * 필드 Director의 accessor
	 * @return Director를  String형으로 반환
	 */
	public String getDirector() {
		return Director;
	}
	/**
	 * 필드 director의 mutator
	 * @param director
	 */
	public void setDirector(String director) {
		Director = director;
	}
	/**
	 * 필드 Actor의 accessor
	 * @return Actor를 String형으로 반환
	 */
	public String getActor() {
		return Actor;
	}
	/**
	 * 필드 Actor의 mutator
	 * @param actor
	 */
	public void setActor(String actor) {
		Actor = actor;
	}

	/**
	 * 필드 grade의 accessor
	 * @return grade를 String형으로 반환
	 */
	public String getGrade() {
		return grade;
	}
	
	/**
	 * 필드 grade의 mutator
	 * @param grade
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	/**
	 * 필드 poster의 accessor
	 * @return poster(URL)를 String으로 반환
	 */
	public String getPoster() {
		return poster;
	}

	/**
	 * 필드 poster의 mutator
	 * @param poster
	 */
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	
}