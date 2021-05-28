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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dao.DBConnection;
import dao.UserDao;
import dto.Movies;


/**
 * 영화선택을 보여주는 클래스로 당일 TOP10에 포함되어 있는 영화를 보여준다.<br>
 * DB와 연동하여 해당 요일에 맞는 영화를 불러오고 영화의 상영관람 나이와 상영시간을 보여준다.
 * 
 * 
 * <p>
 * 이떄 클래스에서 사용하는 네이버, 영화진흥원 API 상영시간과 상영관람 나이를 제공하지 않아서 임의로 넣는 작업을 하였고
 * 프로그램을 개선할 떄 각 영화에 맞는 상영관람나이와 상영시간을 불러오는 것을 추가할 예정이다
 * </p>
 * 
 * 
 * @author JungHoJune,kimHyunYoung
 * @version 1.0.0
 * @since 2021.05.24
 */

@SuppressWarnings("serial")
public class SelectMovie1GUI extends CustomUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JLabel lbBox[], lbMovieName[], lbTime[], lbTitle;
	private JButton btnBack;
	
	private static final String SQL = "SELECT * FROM movie WHERE id = ?";
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	private int addnum;
	private int movieId;
	private ArrayList<Movies> movies = new ArrayList<>();

	private String userId, reserveDate;
	
	/**
	 * 
	 * 만든 GUI를 보여주는 메소드로 뒤로가기 버튼을 만들어서 
	 * 원하는 날짜가 아니라면 
	 * 다른 날짜를 다시 선택하기 위해 만들었다.
	 * 
	 * 
	 * @param userId 사용자의 ID
	 * @param reserveDate 사용자가 원하는 예매 날짜
	 */
	
	public SelectMovie1GUI(String userId, String reserveDate) {
		this.userId = userId;
		this.reserveDate = reserveDate;

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();

		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SelectDateGUI(userId, "Movie");
				frame.dispose();
			}
		});

		frame.setSize(426, 779);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	/**
	 * <p>
	 * 우리가 보고싶은 화면을 만들고 DB와 연결하는 메인으로
	 * DB와 연결하여 저장되어 있는 영화의 목록을 가져온 후 
	 * panel에 붙여주는 작업을 한다.<br>
	 * </p>
	 * 
	 * <p>
	 * <code>MouseListener</code>를 사용하여 사용자가 원하는 영화제목을 클릭할 시
	 * 자리 선택하는 창으로 넘어가도록 설정하였으며 제목이 아닌 상영시간, 상영가능 나이를 클릭할 때에는
	 * 동작하지 않도록 하였다.<br>
	 * 또한, 뒤로가기 버튼을 만들어서 다시 원하는 날짜를 다시 선택 할 수 있도록 만들었다. 
	 * </p>
	 * 
	 * <p>
	 * 영화를 패널 자체에 붙인다면 우리가 사용하는 frame의 크기안에 10개의 영화가 다 들어오지 않아서 스크롤을 할 수 있는 
	 * panel을 만들어서 영화 선택창을 만든다음 기존의 panel에 Scrollpanel을 붙이는 작업을 하였다. 
	 * 그렇게 해서 우리가 원하는 영화를 스크롤을 통하여 다 보이게 하였고 깔끔함을 더했다.
	 * </p>
	 *  
	 */
	private void init() {
		conn = DBConnection.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, reserveDate);
			rs = pstmt.executeQuery();

			for (int no = 0; no < 10; ++no) {
				pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, no+1);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					Movies movie = new Movies();
					movie.setId(rs.getInt("ID"));
					movie.setTitle(rs.getString("TITLE"));
					movie.setRunningTime(rs.getInt("RUNNING_TIME"));
					movies.add(movie);
				}
			}

			backgroundPanel = new JPanel();
			frame.setContentPane(backgroundPanel);
			frame.setTitle("");

			CustomUI custom = new CustomUI(backgroundPanel);
			custom.setPanel();

			lbBox = new JLabel[movies.size()];
			lbMovieName = new JLabel[movies.size()];
			lbTime = new JLabel[movies.size()];

			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.setBackground(Color.WHITE);

			for (int j = 0; j < movies.size(); j++) {
				int moveY = 55;
				addnum++;
				lbBox[j] = custom.setLbBox("lbBox" + j, "12", 35, 20 + (moveY * j), panel);
				lbMovieName[j] = custom.setLb("lbMovieName" + j, movies.get(j).getTitle(), 75, 22 + (moveY * j), 300, 20, "left", 14, "plain", panel);
				lbTime[j] = custom.setLb("lbTime" + j, movies.get(j).getRunningTime() + "분", 80, 22 + (moveY * j), 300, 20, "right", 13, "plain", panel);

				panel.add(lbBox[j]);
				panel.add(lbMovieName[j]);
				panel.add(lbTime[j]);

				lbMovieName[j].addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {}
					public void mousePressed(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseClicked(MouseEvent e) {
						String movieTitle = e.getSource().toString();
					

						for (int i = 0; i < lbMovieName.length; i++) {
							if (movieTitle.contains(movies.get(i).getTitle())) {
								movieId = movies.get(i).getId();
							
							}
						}
						
						new MovieDetailGUI(userId,reserveDate,movieId);
						frame.dispose();


					}
				});
			}
			panel.setPreferredSize(new Dimension(400, 20 + 55 * addnum));

			JScrollPane sp = new JScrollPane();
			sp.setViewportView(panel);
			sp.setBounds(0, 120, 422, 500);
			backgroundPanel.add(sp);

			lbTitle = custom.setLb("lbTitle", "예매 내역", 100, 85, 220, 185, "center", 20, "bold");
			btnBack = custom.setBtnWhite("btnBack", "이전으로", 650);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}