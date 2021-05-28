package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dao.DBConnection;
import dto.Movies;
import dto.Reserves;
/**
 * 영화예매 확인창 GUI클래스
 * <ol>
 * <li>영화예매 확인 기본 틀 생성</li>
 * <li>각 버튼과 라벨에 리스너 추가</li>
 * </ol>
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class BookingList extends CustomUI {
	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JButton btnBack;
	private JLabel lbTitle, lbreserveDate[], lbMovieTitle[], lbTime[],lbreserveTime[];

	private Connection conn, conn2;
	private PreparedStatement pstmt1, pstmt2;
	private ResultSet rsMovie, rsReserve;
	private ArrayList<Movies> mv = new ArrayList<Movies>();
	
	private String userId;
	
	/**<p>
	 * 유저아이디값과 일치한 값을 인자로 받아서 그 값에 대한 결과를 보여준다.<br/>
	 * 뒤로가기 버튼에 액션이벤트 리스너를 연결해주어 동작 가능하도록 해준다.
	 * </p>
	 * @param userId 유저아이디
	 */
	public BookingList(String userId) {
		this.userId = userId;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();

		btnBack.addActionListener(new ActionListener() {
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
	 * <ol>
	 * <li>프레임에 패널 추가 </li>
	 * <li>각 라벨에 들어갈 값을 reserveDao와 reserves를 이용하여 추출</li>
	 * <ul>
	 * <li>유저아이디를 이용하여 특정 값 추출하기 위해 db연결 {@link DBConnection#getConnection()}참조</li>
	 * <li>sql전송 후 실행</li>
	 * <li>executeQuery 실행 후 결과 집합을 arrayList배열(id)을 생성후 그 안에 저장</li>
	 * <li>id배열안의 값을 이용하여 영화제목과 상영시간을 추출하기 위해 다시 한번 db연결</li>
	 * <li>sql전송 후 실행</li>
	 * <li>executeQuery 실행 후 결과 집합을 arrayList배열(movieInfo)을 생성후 그 안에 저장</li>
	 * </ul>
	 * <li>추출해온 값을 알맞은 라벨에 부착 {@link CustomUI#setLb(String, String, int, int, int, int, String, int, String, JPanel)}참조 </li>
	 * <li>스크롤바,버튼(이전으로),라벨(예매내역) 추가후 부착</li>
	 * 
	 */
	private void init() {
		backgroundPanel = new JPanel();
		frame.setContentPane(backgroundPanel);
		frame.setTitle("");

		CustomUI custom = new CustomUI(backgroundPanel);
		custom.setPanel();

		String sql_Reserve = "SELECT id, movie_Id, reserve_Date, reserve_Time FROM reserve where user_id = ?";
		ArrayList<Reserves> reserve = new ArrayList<Reserves>();
		Reserves r = new Reserves();
		ArrayList<Integer> id = new ArrayList<Integer>(); // reserveid
		ArrayList<String> date = new ArrayList<String>(); // 상영일시
		ArrayList<String> time = new ArrayList<String>(); // 예약시간
		
		conn = DBConnection.getConnection();

		try {
			pstmt1 = conn.prepareStatement(sql_Reserve);
			pstmt1.setString(1, userId);
			rsReserve = pstmt1.executeQuery();

			while (rsReserve.next()) {
				r.setId(rsReserve.getInt("ID"));
				r.setMovieId(rsReserve.getInt("movie_Id"));
				r.setReserveDate(rsReserve.getString("reserve_Date"));
				r.setReserveTime(rsReserve.getString("reserve_Time"));
				
				id.add(rsReserve.getInt("movie_Id"));
				date.add(rsReserve.getString("reserve_Date"));
				time.add(rsReserve.getString("reserve_Time"));
				
				reserve.add(r);
			}

		} catch (Exception e) {

		} finally {
			try {
				if (rsReserve != null) {
					rsReserve.close();
				}
				if (pstmt1 != null) {
					pstmt1.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		String sql_Movie = "SELECT title, running_Time FROM movie WHERE id = ?";
		ArrayList<Movies> movieInfo = new ArrayList<Movies>();
		Movies m = new Movies();

		conn2 = DBConnection.getConnection();

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		int y = 0;
		for (int i = 0; i < reserve.size(); ++i) {
			try {
				pstmt2 = conn2.prepareStatement(sql_Movie);
				pstmt2.setInt(1, id.get(i)); // == >2
				rsMovie = pstmt2.executeQuery();

				if (rsMovie.next()) {
					m.setTitle(rsMovie.getString("TITLE"));

					m.setRunningTime(rsMovie.getInt("RUNNING_TIME"));

					movieInfo.add(m);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (rsMovie != null) {
						rsMovie.close();
					}
					if (pstmt2 != null) {
						pstmt1.close();
					}
					if (conn2 != null) {
						conn.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

			lbreserveDate = new JLabel[reserve.size()];
			lbMovieTitle = new JLabel[reserve.size()];
			lbTime = new JLabel[reserve.size()];
			lbreserveTime = new JLabel[reserve.size()];
			
			int moveY = 55 * i;
			++y;
			// System.out.println(movieInfo.size());
			lbreserveDate[i] = custom.setLb("lbreserveDate" + i, date.get(i), 0, 20 + moveY, 300, 20, "left", 12,
					"plain", panel);
			lbMovieTitle[i] = custom.setLb("lbMovieTitle" + i, movieInfo.get(i).getTitle(), 100, 20 + moveY, 300, 20,
					"left", 14, "plain", panel);
			lbTime[i] = custom.setLb("lbTime" + i, movieInfo.get(i).getRunningTime() + "분", 80, 20 + moveY, 300, 20,
					"right", 13, "plain", panel);
			lbreserveTime[i] = custom.setLb("lbreserveTime" + i, time.get(i), 100, 20 + moveY, 300, 20,
					"left", 14, "plain", panel);
			lbreserveTime[i].setForeground(Color.WHITE);
			
			panel.add(lbreserveDate[i]);
			panel.add(lbMovieTitle[i]);
			panel.add(lbTime[i]);
			panel.add(lbreserveTime[i]);

			lbreserveTime[i].addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseClicked(MouseEvent e) {
					JLabel lb = (JLabel) e.getSource();
					String reserveTime = lb.getText();
					new BookingDetail(userId, reserveTime);
					frame.dispose();
				}
			});
		}
		///////////////
		panel.setPreferredSize(new Dimension(400, 20 + 55 * y));

		JScrollPane sp = new JScrollPane();
		sp.setViewportView(panel);
		sp.setBounds(0, 120, 422, 500);
		backgroundPanel.add(sp);

		lbTitle = custom.setLb("lbTitle", "예매 내역", 100, 85, 220, 185, "center", 20, "bold");
		btnBack = custom.setBtnWhite("btnBack", "이전으로", 650);
	}

}