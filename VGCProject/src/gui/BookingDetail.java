package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dao.DBConnection;
import dao.MovieDao;
import dao.ReserveDao;
import dto.Movies;
import dto.Reserves;
/**
 * 
 * 영화예매 개별확인창 GUI클래스
 * <ol>
 * <li>영화예매 개별확인 기본 틀 생성</li>
 * <li>각 버튼에 리스너 추가</li>
 * </ol>
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class BookingDetail {
	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JLabel lbTitleReserveDt, lbTitleReserveCnt, lbTitleSeat, lbTitlePrice, lbTitleInsDt;
	private JLabel lbMovieTitle, lbReserveDt, lbReserveCnt, lbSeat, lbPrice, lbInsDt;
	private JButton btnCancel, btnBack;

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private String userId;
	private String reserveTime;
	/**
	 * <p>
	 * 유저아이디와 예약번호가 일치한 값을 인자로 받는다.<br/>
	 * 인자로 받은 값을 dao(ReserveDao,MovieDao)를 이용하여 얻고자 하는 값을 불러온다.<br/>
	 * 취소하기 버튼에 액션이벤트 리스너를 연결해주어 동작 가능하도록 해준다.
	 * <ul>
	 * <li>유저아이디와 예약번호값을 가지고 이전 방법과 동일하게 dao를 이용하여 일치하는 값을 삭제한다.</li>
	 * </ul>
	 * 뒤로가기 버튼에 액션이벤트 리스너를 연결해주어 동작 가능하도록 해준다.
	 * @param userId    유저아이디
	 * @param reserveId 예약번호
	 */
	public BookingDetail(String userId, String reserveId) {
		this.userId = userId;
		this.reserveTime = reserveId;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();

		ReserveDao rDao = ReserveDao.getInstance()  ;
		Reserves reserve = rDao.selectOneDetails(userId, reserveTime);
		
		MovieDao mDao = MovieDao.getInstance();
		Movies title = mDao.selectOne(reserve.getMovieId());
		
		lbMovieTitle.setText(title.getTitle());
		
		lbReserveDt.setText(reserve.getReserveDate());
		lbReserveCnt.setText(reserve.getReserveCnt()+ "명");
		lbSeat.setText(reserve.getSeat());
		lbInsDt.setText(reserve.getReserveTime());
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "예매를 취소하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					int resultCnt = delete(userId, reserveTime);
					if(resultCnt == 0) {
						JOptionPane.showMessageDialog(null, "해당되는 데이터가 없습니다.");
					} else {
						JOptionPane.showMessageDialog(null, "예매가 취소되었습니다.");
						new BookingList(userId);
						frame.dispose();
					}
				}
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new BookingList(userId);
				frame.dispose();
			}
		});

		frame.setSize(426, 779);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	/**
	 * 유저아이디와 예약날짜를 이용하여 원하는 값을 삭제한다.<br/>
	 * <ol>
	 * <li>DB연결</li>
	 * <li>sql 전송 후 실행</li>
	 * <li>executeUpdate를 이용하여 결과 값(int)반환</li>
	 * </ol>
	 * @param userId 유저아이디
	 * @param reserveTime 예약날짜
	 * @return returnCnt 실행된 레코드의 수를 반환
	 */
	public int delete(String userId, String reserveTime) {
		String sql = "DELETE FROM reserve WHERE user_Id =? AND reserve_Time = ?";
		conn = DBConnection.getConnection();
		try {
			int returnCnt = 0;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, reserveTime);
			returnCnt = pstmt.executeUpdate();

			conn.close();
			return returnCnt;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}
	/**
	 * <ol>
	 * <li>프레임에 패널 추가</li>
	 * <li>패널에 각 라벨 부착{@link CustomUI#setLb(String, String, int, int, int, int, String, int, String)} 참조
	 * <li>패널에 각 버튼(취소하기, 뒤로가기) 부착 {@link CustomUI #setBtnBlue(String, String, int)},{@link CustomUI #setBtnWhite(String, String, int)} 참조 </li>
	 * </ol>
	 */
	private void init() {
		backgroundPanel = new JPanel();
		frame.setContentPane(backgroundPanel);
		frame.setTitle("");

		CustomUI custom = new CustomUI(backgroundPanel);
		custom.setPanel();
		
		lbMovieTitle = custom.setLb("lbMovieTitle", "", 100, 85, 220, 185, "center", 20, "bold");

		lbTitleReserveDt = custom.setLb("lbTitleReserveDt", "상영일시", 35, 265, 100, 20, "left", 17, "bold");
		lbReserveDt = custom.setLb("lbReserveDt", "", 195, 265, 180, 20, "right", 17, "plain");

		lbTitleReserveCnt = custom.setLb("lbTitleReserveCnt", "예매인원", 35, 325, 100, 20, "left", 17, "bold");
		lbReserveCnt = custom.setLb("lbReserveCnt", "", 195, 325, 180, 20, "right", 17, "plain");

		lbTitleSeat = custom.setLb("lbTitleSeat", "좌석번호", 35, 385, 100, 20, "left", 17, "bold");
		lbSeat = custom.setLb("lbSeat", "", 195, 385, 180, 20, "right", 17, "plain");

		lbTitleInsDt = custom.setLb("lbTitleInsDt", "예매일자", 35, 445, 130, 20, "left", 17, "bold");
		lbInsDt = custom.setLb("lbInsDt", "", 195, 445, 180, 20, "right", 17, "plain");

		btnCancel = custom.setBtnBlue("btnCancel", "예매취소", 600);
		btnBack = custom.setBtnWhite("btnBack", "이전으로", 655);
	}

}