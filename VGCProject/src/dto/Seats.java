package dto;
/**
 * Seats dto<br/>
 * Seats의 순수한 데이터 객체로 속성에 접근하기 위한 getter, setter 메소드만 가짐.
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 */
public class Seats {
	private int id;
	private String type;
	private int row;
	private int col;
	
	public Seats() {}
	/**
	 * Seats 생성자
	 * @param id 좌석 고유번호
	 * @param type 영화관 유형
	 * @param row 행
	 * @param col 열
	 */
	public Seats(int id, String type, int row, int col) {
		this.id = id;
		this.type =type;
		this.row = row;
		this.col = col;
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
	 * 필드 type의 accessor
	 * @return type을 String형으로 반환
	 */
	public String getType() {
		return type;
	}
	/**
	 * 필드 type의 mutator
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 필드 row의 accessor
	 * @return row를 int형으로 반환
	 */
	public int getRow() {
		return row;
	}
	/**
	 * 필드 row의 mutator
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}
	/**
	 * 필드 col의 accessor
	 * @return col을 int형으로 반환
	 */
	public int getCol() {
		return col;
	}
	/**
	 * 필드 col의 mutator
	 * @param col
	 */
	public void setCol(int col) {
		this.col = col;
	}
	
	
}