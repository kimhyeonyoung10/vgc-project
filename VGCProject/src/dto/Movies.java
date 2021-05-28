package dto;
/**
 * movies dto<br/>
 * movies의 순수한 데이터 객체로 속성에 접근하기 위한 getter, setter 메소드만 가짐.
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 *
 */
public class Movies {
	private int id;
	private String title;
	private int runningTime;
	private String startTime;
	
	public Movies() {}
	/**
	 * 
	 * @param id movie 영화 고유번호
	 * @param title 영화제목
	 * @param runningTime 상영시간
	 * @param startTime 시작시간
	 */
	public Movies(int id, String title,  int runningTime, String startTime) {
		this.id = id;
		this.title = title;
	
		this.runningTime = runningTime;
	}

	/**
	 * 필드 startTime의 accessor
	 * @return startTime을 String형으로 반환
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * 필드 startTime의 mutator
	 * @param startTime
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * 필드 runningTime의 accessor
	 * @return runningTime을 int형으로 반환
	 */
	public int getRunningTime() {
		return runningTime;
	}
	/**
	 * 필드 runningTime의 mutator
	 * @param runningTime
	 */
	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}
	/**
	 * 필드 id의 accessor
	 * @return id을 int형으로 반환
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
	 * 필드 title의 accessor
	 * @return title을 String형으로 반환
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 필드 title의 mutator
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}