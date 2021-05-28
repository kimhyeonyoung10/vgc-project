package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.UserDao;
import dto.Users;
/**
 * 회원정보확인 GUI클래스
 * <ol>
 * <li>회원정보확인 기본 틀 생성</li>
 * <li>버튼에 리스너 추가</li>
 * </ol>
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class UserInfoGUI extends JFrame{
	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JLabel lbTitleId, lbId, lbTitleBirth, lbBirth, lbTitleTel, lbTel;
	private JButton btnMain;
	
	/**
	 * 유저 아이디와 일치하는 회원 정보를 보여주는 화면이다.<br/>
	 * db를 이용해 유저 아이디와 일치하는 회원의 특정 정보를 가져온다.<br/>
	 * 
	 * @param userId 유저아이디
	 */
	
	public UserInfoGUI(String userId) {
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		init();
		
		UserDao uDao = UserDao.getInstance();
		Users user = uDao.selectOne(userId);
		
		lbId.setText(user.getUserId());
		lbBirth.setText(user.getBirthDate() +"");
		lbTel.setText(user.getPhone());
		
		btnMain.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new MainGUI(userId);
				frame.dispose();
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
	 * <li> 프레임에 패널 추가 {@link UserInfoGUI#setPanel()} 참조 </li>
	 * <li> 패널에 각 라벨 부착 {@link UserInfoGUI#getTitleLabel(String, int, int, int, int)},{@link UserInfoGUI#getLabel(String, int, int, int, int)} 참조</li>
	 * <li> 패널에 각 버튼 부착 {@link UserInfoGUI#setMainBtn(String, int)} 참조 </li>
 	 * </ol>
	 */
	private void init() {
		setPanel();
		
		lbTitleId = getTitleLabel("아이디", 35, 300, 100, 20);
		lbId = getLabel("", 195, 300, 180, 20);

		lbTitleBirth = getTitleLabel("생년월일", 35, 360, 100, 20);
		lbBirth = getLabel( "", 195, 360, 180, 20);

		lbTitleTel = getTitleLabel("전화번호", 35, 420, 100, 20);
		lbTel = getLabel("", 195, 420, 180, 20);
		btnMain = setMainBtn("메인으로", 605);
	}
	/**
	 * 패널 위치와 배경색 설정
	 */
	private void setPanel() {
		backgroundPanel = new JPanel();
		backgroundPanel.setLayout(null);
		backgroundPanel.setBackground(Color.WHITE);
	}
	/**
	 * 라벨의 세부사항 설정(폰트이름, 크기, 배경색 등등)
	 * 
	 * @param text 내용
	 * @param x 가로위치
	 * @param y 세로위치
	 * @param width 가로크기
	 * @param height 세로크기
	 * @return label의 내용 반환
	 */
	private JLabel getTitleLabel(String text, int x, int y, int width, int height) {

		JLabel label = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", 1, 20);
		label.setFont(lbFont);
		label.setForeground(new Color(114,114,114));
		label.setHorizontalAlignment(2);
		label.setBounds(x, y, width, height);
		
		backgroundPanel.add(label);

		return label;
	}
	/**
	 *
	 * 라벨의 세부사항 설정(폰트이름, 크기, 배경색 등등)
	 * 
	 * @param text 내용
	 * @param x 가로위치
	 * @param y 세로위치
	 * @param width 가로크기
	 * @param height 세로크기
	 * @return label의 내용 반환
	 * 
	 */
	private JLabel getLabel(String text, int x, int y, int width, int height) {

		JLabel label = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", 1, 20);
		label.setFont(lbFont);
		label.setHorizontalAlignment(4);
		label.setBounds(x, y, width, height);
		backgroundPanel.add(label);

		return label;
	}
	
	/**
	 * 버튼의 세부사항 설정(폰트이름, 크기, 배경색 등등)
	 * @param text 내용
	 * @param y 세로위치
	 * @return 버튼 반환
	 */
	
	protected JButton setMainBtn(String text, int y) {

		JButton btn = new JButton(text);

		Font btnFont = new Font("맑은 고딕", Font.PLAIN, 18);
		btn.setFont(btnFont);
		btn.setBackground(new Color(53, 121, 247));
		btn.setForeground(Color.WHITE);
		

		btn.setBorderPainted(false);
		btn.setBounds(35, y, 350, 45);
		btn.setText(text);
		backgroundPanel.add(btn);

		return btn;
	}

}