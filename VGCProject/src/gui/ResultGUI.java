package gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.MovieDao;
import dao.ReserveDao;
import dto.Movies;
import dto.Reserves;


/**
 * 
 * 앞에서 결정했던 모든 정보들을 보여주는 창으로<br>
 * DB에 저장되어 있는 정보들을 불러와 보여준다.
 * 
 * 
 * @author JungHoJune,kimHyunYoung
 * @version 1.0.0
 * @since 2021.05.24	
 */


@SuppressWarnings("serial")
public class ResultGUI extends JFrame  {
	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JLabel lbIcon, lbTitle;
	private JLabel lbTitleMovie, lbMovie, lbTitleDate, lbDate, lbTitleCnt, lbCnt, lbTitleSeat, lbSeat, lbTitlePrice, lbPrice, lbTitleDt, lbDt;
	private JButton btnMain;
	
	private String userId;

	/**
	 * 저장되어 있는 것을 화면에 보여주는 메소드로 각각에 맞는 정보를 DB에서 불러와 label에 붙여주는 작업을한다.
	 * 메인으로 가는 버튼을 만들어 결과를 본 후 창이 닫히는 것이 아닌 메인으로 돌아가 다른 작업들을 할 수 있게 해준다.
	 * 
	 * @param userId 사용자의 ID
	 * @param movieId 사용자가 예매한 영화제목
	 */
	public ResultGUI(String userId, String selectedSeats, int movieId) {
		this.userId = userId;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();

		ReserveDao rDao = ReserveDao.getInstance();
		Reserves reserve = rDao.selectOne(userId, movieId,selectedSeats);
		
		MovieDao mDao = MovieDao.getInstance();
		Movies title = mDao.selectOne(movieId);
		
		lbMovie.setText(title.getTitle());
		lbDate.setText(reserve.getReserveDate());
		lbCnt.setText(reserve.getReserveCnt()+"명");
		lbSeat.setText(reserve.getSeat().replace(",", ", "));
		lbDt.setText(reserve.getReserveTime());
		
		btnMain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MainGUI(userId);
				frame.dispose();
			}
		});

		frame.setSize(426, 779);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	/**
	 * 각각의 정보들을 panel에 붙여주는 작업을 하고 
	 * 영화제목, 예매날짜, 인원 수, 좌석정보, 예매 일시가 보여질 곳을 정해준다.
	 *  
	 */
	private void init() {
		backgroundPanel = new JPanel();
		frame.setContentPane(backgroundPanel);
		frame.setTitle("");

		CustomUI custom = new CustomUI(backgroundPanel);
		custom.setPanel();

		lbTitle = custom.setLb("lbTitle", "예매가 완료되었습니다!", 100, 150, 220, 185, "center", 20, "bold");

		lbTitleMovie = custom.setLb("lbTitleMovie", "영화 제목", 35, 310, 100, 20, "left", 17, "bold");
		lbMovie = custom.setLb("lbMovie", "", 195, 310, 180, 20, "right", 17, "plain");

		lbTitleDate = custom.setLb("lbTitleDate", "예매 날짜", 35, 360, 100, 20, "left", 17, "bold");
		lbDate = custom.setLb("lbDate", "", 195, 360, 180, 20, "right", 17, "plain");

		lbTitleCnt = custom.setLb("lbTitleCnt", "인원 수", 35, 410, 100, 20, "left", 17, "bold");
		lbCnt = custom.setLb("lbCnt", "", 195, 410, 180, 20, "right", 17, "plain");

		lbTitleSeat = custom.setLb("lbTitleSeat", "좌석 정보", 35, 460, 100, 20, "left", 17, "bold");
		lbSeat = custom.setLb("lbSeat", "", 195, 460, 180, 20, "right", 17, "plain");


		lbTitleDt = custom.setLb("lbTitleDt", "예매일시", 35, 510, 130, 20, "left", 17, "bold");
		lbDt = custom.setLb("lbDt", "19.10.05 12:27", 195, 510, 180, 20, "right", 17, "plain");

		btnMain = custom.setBtnBlue("btnMain", "메인으로", 655);
	}
}