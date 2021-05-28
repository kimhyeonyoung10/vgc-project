package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dao.DBConnection;
import dao.ReserveDao;
import dao.SeatDao;
import dto.Reserves;
import dto.Seats;

/**
 * <p>
 * 우리가 원하는 좌석을 선택하기 위한 창을 보여준다.<br>
 * </p>
 * 
 * <p>
 * DB에 저장되어 있는 좌석 정보를 받아오는 작업을 통하여 해당 영화에 맞는 좌석을 보여준다. 아직은 모든 좌석은 25개로 고정되어
 * 있기때문에 25개의 좌석만 보여지지만 좀 더 많은 좌석을 추가하여야 한다
 * </p>
 * 
 * <p>
 * 인원수를 선택하지 않았을때는 기본값으로 1명이 선택되어 있고 최대 4명까지 선택할 수 있다. 자리는 원하는 좌석을 선택하면 주황색으로
 * 바뀌게 되고 만약 이미 선택되어 있는 좌석이라면 파란색으로 표현되며, 좌석을 선택하여도 반응이 없도록 작업하였다.
 * </p>
 * 
 * <p>
 * 좌석선택을 끝내고 예약하기 버튼을 누르면 예매날짜,영화,좌석 정보가 사용하고 있는 DB에 넘어가며 예매 결과창으로 넘어가게 된다.
 * </p>
 * 
 * @author JungHoJune,kimHyunYoung
 * @version 1.0.0
 * @since 2021.05.24
 */

@SuppressWarnings("serial")
public class SeatGUI extends JFrame {
	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JLabel lbCnt;
	private JButton btnSeat[];
	private JButton btnReserve, btnBack, btnScreen;
	private JComboBox<String> comboCnt;
	private ActionListener btnListener;

	private int selectedCnt, peopleCnt;
	private ArrayList<Integer> selectedSeatNum = new ArrayList<Integer>();

	private String userId, reserveDate;
	private int movieId, placeId, theaterId;

	private static Connection conn;
	private static PreparedStatement pstmt;
	private static final String SQL = "INSERT INTO reserve(user_Id, movie_Id, reserve_Date, reserve_Cnt, seat) VALUES "
			+ "(?,?,?,?,?)";
	private String selectedSeats = "";

	/**
	 * <p>
	 * 만든 좌석 GUI를 보여주는 메소드로 기본적으로 <code>MouseListener,ActionListener</code> 를 이용하여
	 * 각각에 맞게 동작하도록 작업하였다.
	 * </p>
	 * 
	 * <p>
	 * <code>ActionListener</code>는 예약하기와 뒤로가기 버튼에서 사용되며 가장 중요한 좌석 선택에서 사용된다.<br>
	 * 좌석을 선택하게 되면 좌석의 색깔이 주황색으로 변하면서 원하는 사람수에서 -1을 해준다. 그리고 다시 선택한 좌석을 클릭하게 되면 선택이
	 * 풀어지고 선택한 사람수에 +1을 해준다. 만약 우리가 선택한 사람수보다 좌석수를 많이 선택하였을 경우 선택이 되지 않으면서 오류메시지를
	 * 출력하게 되고 적게 선택하였을 경우에도 예매하기 버튼을 눌렀을때고 동작하지 않게 되어있다. <br>
	 * <code>MouseListener<code>는 프레임에서 어디에 처음 포인트를 잡아줄것인지를 결정하기 위해 
	 * 사용되어있고 별다른 기능은 없다,
	 * </p> 
	 * 
	 * <p>
	 * 예약버튼을 눌렀을때 DB와 연동이 되면서 예매날짜, 선택한 영화, 선택한 인원 수, 선택한 좌석이 우리가 사용하는 DB로 넘어가게 되고 
	 * 결과창을 보여준다.<br>
	 * 
	 * 뒤로 가기 버튼은 눌렀을 경우에는 다시 영화를 선택하는 창이 나온다.
	 * 
	 * 마지막으로 버튼으로 되어 있는 스크린을 선택이 되지 않게 하기 위해 <code>btnScreen.setEnabled(false);</code>를
	 * 사용하였다.
	 * </p>
	 * 
	 * @param userId      유저가 사용하는 ID
	 * @param movieId     영화의 제목
	 * @param reserveDate 유저가 선택한 날짜정보
	 */
	public SeatGUI(String userId, String reserveDate, int movieId) {
		this.userId = userId;
		this.movieId = movieId;
		this.reserveDate = reserveDate;

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JButton) {
					JButton btn = (JButton) e.getSource();
					peopleCnt = comboCnt.getSelectedIndex() + 1;
					int btnNum = Integer.parseInt(btn.getText());
					if (btn.getBackground().equals(Color.ORANGE)) {
						btn.setBackground(new Color(230, 236, 240));
						int arrayIndex = selectedSeatNum.indexOf(btnNum);
						selectedSeatNum.remove(arrayIndex);
						selectedCnt--;
					} else {
						if (selectedCnt < peopleCnt && selectedCnt >= 0) {
							btn.setBackground(Color.ORANGE);
							selectedSeatNum.add(btnNum);
							selectedCnt++;
						} else {
							JOptionPane.showMessageDialog(frame, "선택한 인원 " + peopleCnt + "명을 초과하여 선택할 수 없습니다.", "오류",
									JOptionPane.ERROR_MESSAGE);
						}
					}

				}
			}
		};

		init();

		btnScreen.setEnabled(false);

		btnReserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				peopleCnt = comboCnt.getSelectedIndex() + 1;
				if (selectedSeatNum.size() == peopleCnt) {
					selectedSeatNum.sort(null);
					for (Integer i : selectedSeatNum) {
						if (selectedSeats.equals("")) {
							selectedSeats = i + "";
						} else {
							selectedSeats += "," + i;
						}
					}

					JOptionPane.showMessageDialog(frame, "예약을 완료 하시겠습니까?", "예약확인", JOptionPane.YES_NO_OPTION);
					conn = DBConnection.getConnection();
					
					try {
//						user_Id, movie_Id, reserve_Date, reserve_Cnt, seat
						pstmt = conn.prepareStatement(SQL);
						pstmt.setString(1, userId);
						pstmt.setInt(2, movieId);
						pstmt.setString(3, reserveDate);
						pstmt.setInt(4, selectedCnt);
						pstmt.setString(5, selectedSeats);

						pstmt.execute();

					} catch (SQLException e2) {
						e2.printStackTrace();
					} finally {
						try {
							if (pstmt != null) {
								pstmt.close();
							}
							if (conn != null) {
								conn.close();
							}
						} catch (Exception e3) {
							e3.printStackTrace();
						}
					}
					new ResultGUI(userId, selectedSeats, movieId);
					frame.dispose();
				 }else {
					JOptionPane.showMessageDialog(frame, "선택한 인원 " + peopleCnt + "명만큼의 좌석을 선택하지 않았습니다.", "오류",
							JOptionPane.ERROR_MESSAGE);
				}
//				conn = DBConnection.getConnection();
//
//				try {
////					user_Id, movie_Id, reserve_Date, reserve_Cnt, seat
//					pstmt = conn.prepareStatement(SQL);
//					pstmt.setString(1, userId);
//					pstmt.setInt(2, movieId);
//					pstmt.setString(3, reserveDate);
//					pstmt.setInt(4, selectedCnt);
//					pstmt.setString(5, selectedSeats);
//
//					pstmt.execute();
//
//				} catch (SQLException e2) {
//					e2.printStackTrace();
//				} finally {
//					try {
//						if (pstmt != null) {
//							pstmt.close();
//						}
//						if (conn != null) {
//							conn.close();
//						}
//					} catch (Exception e3) {
//						e3.printStackTrace();
//					}
//				}
//				new ResultGUI(userId, selectedSeats, movieId);
//				frame.dispose();
			}

		});

		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SelectMovie1GUI(userId, reserveDate);
				frame.dispose();
			}
		});

		frame.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				frame.requestFocus();
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});

		frame.setSize(426, 779);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	/**
	 * 우리가 사용할 버튼들의 위치를 정해주는 역활을 한다.<br>
	 * 이때 DB에 잇는 좌석의 ROW,COL의 크기를 가져와 버튼의 수를 정하고<br>
	 * 인원선택의 수는 우리가 임의로 1~4명으로 정하였다.<br>
	 * 수정을 하게 된다면 한번에 받을 수 있는 인원수의 크기를 좌석의 수만큼 늘리는 작업을 할 것이다.<br>
	 * 또한, 좌석의 위치를 정확하게 하기 위해서 반복문을 사용하여 스크린의 위치를 기준으로 각각의 좌석들이 1~25까지 정확하게 순서대로
	 * 들어가게 만들었다.<br>
	 */
	private void init() {
		backgroundPanel = new JPanel();
		frame.setContentPane(backgroundPanel);
		frame.setTitle("");

		CustomUI custom = new CustomUI(backgroundPanel);
		custom.setPanel();

		lbCnt = custom.setLb("lbCnt", "인원수 선택", 45, 165, 100, 20, "left", 17, "bold");
		String cnt[] = { "1", "2", "3", "4" };
		comboCnt = custom.setCombo("comboCnt", cnt, 330, 165, 50, 25);
		btnScreen = custom.setbtnBar("screenBar", "SCREEN", 220);

		SeatDao dao = SeatDao.getInstance();
		Seats seat = dao.selectOne(1);
		String seatRow[] = new String[seat.getRow()];
		String seatCol[] = new String[seat.getCol()];
		btnSeat = new JButton[seat.getRow() * seat.getCol()];

		for (int row = 0; row < seatRow.length; row++) {
			for (int col = 0; col < seatCol.length; col++) {
				int num = (row * seatCol.length) + (col + 1);
				int moveX = 70;
				int moveY = 60;
				btnSeat[num - 1] = custom.setBtnSeat("btnSeat" + num, num + "", 45 + (moveX * col),
						275 + (moveY * row));
				btnSeat[num - 1].addActionListener(btnListener);
			}
		}

		ReserveDao rDao = ReserveDao.getInstance();
		Reserves reserve = rDao.selectSeatAll(movieId, reserveDate);

		
		if (reserve.getSeat() != null) {
			String splitAlredySelectedSeat[] = reserve.getSeat().split("\\,");
//			System.out.println(Arrays.toString(splitAlredySelectedSeat));

			for (int i = 0; i < splitAlredySelectedSeat.length; i++) {
				int reserveSeat = Integer.parseInt(splitAlredySelectedSeat[i]);
				btnSeat[reserveSeat-1].setBackground(new Color(53, 121, 247));
				btnSeat[reserveSeat-1].setEnabled(false);
			}
		}

		btnReserve = custom.setBtnBlue("btnReserve", "예약하기", 600);
		btnBack = custom.setBtnWhite("btnBack", "영화선택", 655);
	}
}