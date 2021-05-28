package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.DBConnection;
import dao.UserDao;
/**
 * 
 * 회원등록 GUI클래스
 * <ol>
 * <li>회원가입 기본 틀 생성</li>
 * <li>각 버튼에 리스너 추가</li>
 * </ol>
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 * 
 *
 */
public class JoinGUI {
	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JTextField txtUserId, txtPassword, txtcheck, txtbirth, txtmobile,txtUserName;
	private JButton btnJoinComplete, btnback;

	private static Connection conn;
	private static PreparedStatement pstmt;

	private static final String SQL = "INSERT INTO USER (id, name, user_Id, password, birth_Date, phone) VALUES (null, ?, ?, ?, ?, ?)";
	private static final String SQL2 = "SELECT * FROM USER WHERE USER_ID = ?";
	
	
	private static final String pattern1 = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$";
	/**
	 * 각 textfield 조건 검사
	 * <ol>
	 * <li>아이디 글자 수 확인</li>
	 * <li>패스워드 정규식 일치 여부 확인</li>
	 * <li>패스워드 동일성 여부 확인</li>
	 * <li>생년월일 & 전화번호 최소 글자수 확인</li>
	 * <li>db를 이용하여 아이디 중복성 체크</li>
	 * <ul>
	 * <li>user테이블의 user_id와 같은 user_id를 추출해오는 sql문을 전송 후 실행</li>
	 * <li>결과값이 있다면 1을 반환 후 아이디 재설정</li>
	 * </ul>
	 * <li>위의 조건들이 모두 다 일치하면 입력한 회원의 정보를 db를 이용하여<br/>
	 * user(회원)테이블에 저장하고 로그인창으로 넘어간다.
	 * </ol>
	 * 
	 */
	public JoinGUI() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();

		btnJoinComplete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userid = txtUserId.getText();
				if (userid.length() < 6) {
					JOptionPane.showMessageDialog(null, "아이디는 최소 6개 이상이어야 합니다");
					txtUserId.setText("");
				}
				
				String userName = txtUserName.getText();
				if (userName.isEmpty()) {
					JOptionPane.showMessageDialog(null, "이름을 입력해 주세요");
					txtUserName.setText("");
				}
				String password = txtPassword.getText();
				
				if (!password.matches(pattern1)) {
					JOptionPane.showMessageDialog(null, "비밀번호는 영어,숫자,특수문자를 조합해야 합니다");
					txtPassword.setText("");
				}
				String passwordCheck = txtcheck.getText();
				if (!(password.equals(passwordCheck))) {
					JOptionPane.showMessageDialog(null, "입력하신 비밀번호와 일치하지 않습니다.");
					txtPassword.setText("");
					txtcheck.setText("");
				}

				String regExp = "^[0-9]+$";
				String birth = txtbirth.getText();
				if (birth.length() != 8) {
					JOptionPane.showMessageDialog(null, "생년월일 8자리를 입력해주세요 ex)19940815");
					txtbirth.setText("");
				} else if (!(birth.matches(regExp))) {
					JOptionPane.showMessageDialog(null, "생년월일은 숫자만 입력해야 합니다.");
					txtbirth.setText("");
				}
				
				String mobile = txtmobile.getText();
				if (!(mobile.length() == 10 || mobile.length() == 11)) {
					JOptionPane.showMessageDialog(null, "핸드폰 번호는 10~11자리를 입력해주세요");
					txtmobile.setText("");
				} else if (!(mobile.matches(regExp))) {
					JOptionPane.showMessageDialog(null, "숫자만 입력해야 합니다.");
					txtmobile.setText("");
				}
				
				
				
				
				UserDao dao = UserDao.getInstance();
				boolean checkId = dao.CheckId(userid);
				
				if(checkId == true) {
					JOptionPane.showMessageDialog(null, "이미 사용중인 아이디 입니다.\n"
							+ "다른 아이디를 이용해주세요.");
				}

				if (userid.length() >= 6 &&  password.equals(passwordCheck)
						&& birth.length() == 8 && (mobile.length() == 10 || mobile.length() == 11)
						&& checkId == false) {

					conn = DBConnection.getConnection();
					int returnCnt = 0;
					try {
						pstmt = conn.prepareStatement(SQL);
						pstmt.setString(1, userName);
						pstmt.setString(2, userid);
						pstmt.setString(3, password);
						pstmt.setString(4, birth);
						pstmt.setString(5, mobile);

						returnCnt = pstmt.executeUpdate();
						conn.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}

					if(returnCnt == 1) { 
						JOptionPane.showMessageDialog(null, "회원가입 완료");
						new LoginGUI();
						frame.dispose();
					} else { 
						JOptionPane.showMessageDialog(null, "회원가입을 실패하였습니다. 조건에 맞게 다시 입력하세요");
					}
				}
			}
		});
		
		
		btnback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoginGUI();
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
	 * <li>프레임에 패널 추가</li>
	 * <li>패널에 각 textField 부착
	 * {@link CustomUI#setTextField(String, String, int, int, int, int)} 참조</li>
	 * <li>패널에 각 passwordField 부착
	 * {@link CustomUI#setPasswordField(String, String, int, int, int, int)} 참조</li>
	 * <li>패널에 각 버튼 부착
	 * {@link CustomUI#setBtnBlue(String, String, int)},{@link CustomUI#setBtnWhite(String, String, int)}
	 * 참조</li>
	 * </ol>
	 */
	private void init() {
		backgroundPanel = new JPanel();
		frame.setContentPane(backgroundPanel);
		frame.setTitle("");

		CustomUI custom = new CustomUI(backgroundPanel);
		custom.setPanel();

		txtUserId = custom.setTextField("txtUserId", "ID", 35, 180, 350, 40);
		txtUserName = custom.setTextField("txtUserName", "NAME", 35, 220, 350, 40);
		txtPassword = custom.setPasswordField("txtPassword", "PASSWORD", 35, 260, 350, 40);
		txtcheck = custom.setPasswordField("txtcheck", "PASSWORD", 35, 300, 350, 40);
		txtbirth = custom.setTextField("txtbirth", "BIRTH", 35, 340, 350, 40);
		txtmobile = custom.setTextField("txtmobile", "MOBILE", 35, 380, 350, 40);

		btnJoinComplete = custom.setBtnBlue("btnBlue", "회원가입하기", 550);
		btnback = custom.setBtnWhite("btnWhite", "뒤로가기", 605);
	}
}