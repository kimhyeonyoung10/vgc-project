package dto;

/**
 * Users dto<br/>
 * User의 순수한 데이터 객체로 속성에 접근하기 위한 getter, setter 메소드만 가짐.
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 *
 */

public class Users {
	private int id;
	private String userId;
	private String password;
	private int birthDate;
	private String phone;
	
	public Users() {
	}
	/**
	 * Users 생성자
	 * @param id 유저 고유번호
	 * @param userId 유저아이디
	 * @param password 비밀번호
	 * @param birthDate 생년월일
	 * @param phone 전화번호
	 */
	public Users(int id, String userId, String password, int birthDate, String phone) {
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.birthDate = birthDate;
		this.phone = phone;

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
	 * 	필드 password의 accessor
	 * @return password를 String형으로 반환
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 필드 password의 mutator
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 필드 birthDate의 accessor
	 * @return birthDate를 int형으로 반환
	 */
	public int getBirthDate() {
		return birthDate;
	}
	/**
	 * 필드 birthDate의 mutator
	 * @param birthDate
	 */
	public void setBirthDate(int birthDate) {
		this.birthDate = birthDate;
	}
	/**
	 * 필드 phone의 accessor
	 * @return phone을 String형으로 반환
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 필드 phone의 mutator
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

}