package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import api.NaverApi;
import dao.DBConnection;
/**
 * 로그인 GUI클래스
 * <ol>
 * <li>로그인 기본 틀 생성</li>
 * <li>api불러오기</li>
 * <li>각 버튼에 리스너 추가</li>
 * </ol>
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 *
 */

public class LoginGUI {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JTextField txtUserId;
	private JPasswordField txtPassword;
	private JButton btnLogin, btnJoin;

	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	private static final String SQL = "SELECT * FROM USER WHERE USER_ID = ? AND PASSWORD = ?";

	/**
	 * <p>
	 * 영화예매 시스템의 첫 화면인 로그인 창에서 Api를 이tjhoho 치는 방법, 2.버튼을 누르는 방법)<br/>
	 * db를 이용해 회원 유무를 판별하여 접속 여부를 확인한다.<br/>
	 * 접속 가능하다면 영화예매 창으로 넘어간다.
	 * </p>
	 * 
	 */
	public LoginGUI() {
		new NaverApi();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		
		
		txtPassword.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnLogin.doClick();
				}
			}
			public void keyPressed(KeyEvent e) {}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userId = txtUserId.getText();
				String password = String.valueOf(txtPassword.getPassword());
				
				conn = DBConnection.getConnection();
				
				try {
					pstmt = conn.prepareStatement(SQL);
					pstmt.setString(1, userId);
					pstmt.setString(2, password);
					rs = pstmt.executeQuery();

					if (rs.next()) {
						new MainGUI(userId);
						frame.dispose();
					} else {
						JOptionPane.showMessageDialog(frame, "일치하는 사용자가 없습니다", "오류", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(frame, "인증되지 않았습니다.");
				}
			}
		});
		
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JoinGUI();
				frame.dispose();
			}
		});

		frame.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				frame.requestFocus();
			}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		});
		
		frame.setSize(426, 779);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	/**
	 * <ol>
	 * <li> 프레임에 패널 추가</li>
	 * <li> 패널에 각 textField 부착 {@link CustomUI#setTextField(String, String, int, int, int, int)} 참조 </li>
	 * <li> 패널에 각 passwordField 부착 {@link CustomUI#setPasswordField(String, String, int, int, int, int)} 참조 </li>
	 * <li> 패널에 각 버튼 부착 {@link CustomUI#setBtnBlue(String, String, int)},{@link CustomUI#setBtnWhite(String, String, int)} 참조 </li>
 	 * </ol>
	 * 
	 */
	private void init() {
		backgroundPanel = new JPanel();
		frame.setContentPane(backgroundPanel);
		frame.setTitle("");

		CustomUI custom = new CustomUI(backgroundPanel);
		custom.setPanel();
		
		
		txtUserId = custom.setTextField("txtUserId", "ID", 35, 290, 350, 45);
		txtPassword = custom.setPasswordField("txtPassword", "Password", 35, 345, 350, 45);
		btnLogin = custom.setBtnBlue("btnLogin", "로그인", 425);
		btnJoin = custom.setBtnWhite("btnJoin", "회원가입", 480);
	}

	public static void main(String[] args) {
		new LoginGUI();
	}
}