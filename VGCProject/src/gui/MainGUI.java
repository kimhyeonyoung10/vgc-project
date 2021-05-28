package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * 영화예매 기본창 GUI클래스
 * <ol>
 * <li>영화예매 기본 틀 생성(날짜별 예매, 예매확인, 유저정보확인)</li>
 * <li>각 버튼에 리스너 추가</li>
 * </ol>
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 *
 */
@SuppressWarnings("serial")
public class MainGUI extends JFrame{
	
	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JButton btnMovie, btnTheather, btnList, btnInfo, btnLogout;
	private String userId;
	/**
	 * 
	 * <ol>
	 * <li>날짜별예매 이벤트리스너 연결</li>
	 * <li>예매확인 이벤트리스너 연결</li>
	 * <li>유저정보확인 이벤트리스너 연결</li>
	 * </ol>
	 * 
	 * @param userId 유저아이디
	 */
	public MainGUI(String userId) {
		this.userId = userId;
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		init();
		
		btnMovie.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new SelectDateGUI(userId, "Movie");
				frame.dispose();
			}
		});
	
		btnList.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				new BookingList(userId);
				frame.dispose();
			}
		});
		
		btnInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new UserInfoGUI(userId);
				frame.dispose();
			}
		});
		
		btnLogout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoginGUI();
				
			}
		});
		frame.setContentPane(backgroundPanel);
		frame.setTitle("");
		
		
		
		frame.setSize(426, 779);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	/**
	 * <ol>
	 * <li>프레임에 패널 추가 {@link MainGUI#setPanel()}참조</li>
	 * <li>패널에 각 버튼 부착
	 * {@link MainGUI#setBtn(String, String, int, int)}<br/>
	 * {@link MainGUI#setLogoutBtn(String, String, int)}<br/>
	 * {@link MainGUI#setResrveBtn(String, String, int, int)}참조</li>
	 * </ol>
	 * 
	 */
	private void init() {
		
		setPanel();
		
		btnMovie = setResrveBtn("btnMovie","영화별 예매", 33, 240);
		btnList = setBtn("btnList","예매 보기", 33, 400);
		btnInfo = setBtn("btnInfo","내 정보 보기", 212, 400);
		btnLogout = setLogoutBtn("btnLogout","로그아웃",  650);
		
		
		
	}
	/**
	 * 프레임에 패널 부착하는 함수<br/>
	 * 위치설정, 배경색 설정
	 */
	private void setPanel() {
		backgroundPanel = new JPanel();
		backgroundPanel.setLayout(null);
		backgroundPanel.setBackground(Color.WHITE);
	}
	/**
	 * 버튼의 세부사항 설정(폰트이름, 크기, 배경색 등등)
	 * 
	 * @param name button이름
	 * @param text button내용
	 * @param y 세로위치
	 * @return 버튼 반환
	 */
	private JButton setLogoutBtn(String name, String text, int y) {

		JButton btn = new JButton(text);

		Font btnFont = new Font("맑은 고딕", Font.PLAIN, 18);
		btn.setFont(btnFont);
		btn.setBackground(new Color(53, 121, 247));
		btn.setForeground(Color.WHITE);
		

		btn.setBorderPainted(false);
		btn.setBounds(35, y, 350, 45);
		btn.setText(text);
		btn.setName(name);
		backgroundPanel.add(btn);

		return btn;
	}
	/**
	 * 
	 * 버튼의 세부사항 설정(폰트이름, 크기, 배경색 등등)
	 * 
	 * @param name button이름
	 * @param text button내용
	 * @param x 가로위치
	 * @param y 세로위치
	 * @return 버튼 반환
	 */
	private JButton setBtn(String name, String text, int x, int y) {

		JButton btn = new JButton(text);

		Font btnFont = new Font("맑은 고딕", Font.PLAIN, 18);
		btn.setFont(btnFont);
		btn.setBackground(new Color(53, 121, 247));
		btn.setForeground(Color.WHITE);
		

		btn.setBorderPainted(false);
		btn.setBounds(x, y, 170, 150);
		btn.setText(text);
		btn.setName(name);
		backgroundPanel.add(btn);

		return btn;
	}
	/**
	 * 
	 * 버튼의 세부사항 설정(폰트이름, 크기, 배경색 등등)
	 * 
	 * @param name button이름
	 * @param text button내용
	 * @param x 가로위치
	 * @param y 세로위치
	 * @return 버튼 반환
	 * 
	 */
	private JButton setResrveBtn(String name, String text, int x, int y) {

		JButton btn = new JButton(text);

		Font btnFont = new Font("맑은 고딕", Font.PLAIN, 18);
		btn.setFont(btnFont);
		btn.setBackground(new Color(53, 121, 247));
		btn.setForeground(Color.WHITE);
		

		btn.setBorderPainted(false);
		btn.setBounds(x, y, 350, 150);
		btn.setText(text);
		btn.setName(name);
		backgroundPanel.add(btn);

		return btn;
	}
	
	
	
}