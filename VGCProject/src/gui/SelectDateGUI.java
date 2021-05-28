package gui;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



/**
 * <p>
 * 날짜를 선택하는 클래스로 해당 달에 맞는 요일을 표현하여다.<br>
 * 또한 지난 날에 대해서는 라이트그레이로 선택을 하지 못하게 하였고 오늘 날짜는 파란색으로 강조를 하였으며 
 * 이후 날짜는 진한그레이로 표현하였다. 원하는 달을 선택하는 것은 추가하지 않앗다.
 * </p>
 * 
 * @author JungHoJune,kimHyunYoung
 * @version 1.0.0
 * @since 2021.05.24
 */

@SuppressWarnings("serial")
public class SelectDateGUI extends JFrame{
	private JFrame frame = new JFrame();
	private JLabel lbTitle, lbDayNames[], lbDay;
	private JButton btnBack;
	private JPanel backgroundPanel;

	private String userId, beforePage;
	private MouseListener listner;
	
	
	/**
	 * 날짜선택 GUI를 화면에 보여준다. 또한 <code>MouseLIstener</code>를 사용하여
	 * 원하는 날짜를 선택하게 하였고 지난 날을 선택하면 오류 메시지가 뜨도록 설정하였다.
	 * 뒤로가기 버튼을 사용하여 이전 페이지로 넘어가는 작업을 하였다.
	 * 
	 * 
	 * @param userId 사용자가 사용하는 아이디
	 * @param beforePage 이전페이지의 이름을 받아와서 이전 페이지로 넘어간다.
	 */
	
	public SelectDateGUI(String userId,  String beforePage) {
		this.userId = userId;
		this.beforePage = beforePage;
		
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		listner = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getSource() instanceof JLabel) {
					JLabel lb = (JLabel)e.getSource();
					int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
					int selectDay =  Integer.parseInt(lb.getText());
					
					if(today > selectDay) {
						JOptionPane.showMessageDialog(frame, "이전 일자는 선택할 수 없습니다.","오류",JOptionPane.ERROR_MESSAGE);
					}else {
						String reserveDate =  lbTitle.getText() + selectDay + "일";
						new SelectMovie1GUI(userId, reserveDate);
						frame.dispose();
						
					}
					
				}
				
			}
		};
		
		init();
		
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(beforePage.equals("Movie")){ 
					new MainGUI(userId);
					frame.dispose();
				}
			}
		});
		frame.setContentPane(backgroundPanel);
		frame.setTitle("");
		frame.setSize(426,779);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	/**
	 * 우리가 사용할 창을 구체적으로 만들어준다.<br> 
	 * 기본적으로 panel안에 우리가 원하는 label과 button을 붙여주는 작업을 하였고
	 * 날짜를 알기 위해 <code> Calender</code>를 사용하여 현재 월,일을 계산하여 보여준다.
	 * 
	 * 
	 */
	
	private void init() {
		setPanel();
		
		Calendar current = Calendar.getInstance();
		int year = current.get(Calendar.YEAR);
		int month = current.get(Calendar.MONTH);
		int day = current.get(Calendar.DAY_OF_MONTH);
		
		String dayNames[] = {"일","월","화","수","목","금","토"};
		
		lbTitle = getTitle("lbTitle",year + "년 " + (month+1) + "월", 100, 85, 220, 185);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1 );
		
		Calendar iterator =(Calendar) calendar.clone();
		iterator.add(Calendar.DAY_OF_MONTH, -(iterator.get(Calendar.DAY_OF_WEEK)-1));
		Calendar maximum = (Calendar) calendar.clone();
		maximum.add(Calendar.MONTH, +1);
		
		lbDayNames = new JLabel[dayNames.length];
		int moveRow = 0;
		for(int i =0; i<dayNames.length; ++i) {
			lbDayNames[i] = getLabel("lbDayNames",dayNames[i], 50 + moveRow, 210, 35, 30);
			backgroundPanel.add(lbDayNames[i]);
			moveRow += 50;
		}
		moveRow = 0;
		int moveCol = 0;
		
		while (iterator.getTimeInMillis() < maximum.getTimeInMillis()) {
			int iMonth = iterator.get(Calendar.MONTH);
			int iYear = iterator.get(Calendar.YEAR);
			
			if(moveRow + 50 > 380) {
				moveCol += 50;
				moveRow = 0;
			}
			
			lbDay = getTitle("lbDay"+iterator.getTimeInMillis(),"", 50 + moveRow, 260 + moveCol, 35, 30);
			
			if((year == iYear) && (month == iMonth)) {
				int iDay = iterator.get(Calendar.DAY_OF_MONTH);
				lbDay.setText(Integer.toString(iDay));
				
				if(day == iDay) {
					lbDay.setForeground(Color.BLUE);
				}
				if(day > iDay) {
					lbDay.setForeground(Color.LIGHT_GRAY);
				}
				
				lbDay.addMouseListener(listner);
			}
			
			backgroundPanel.add(lbDay);
			iterator.add(Calendar.DAY_OF_YEAR, +1);
			moveRow += 50;
			
		}
		
		btnBack = setBtnWhite("btnBack","이전으로", 655);
	}
	
	private void setPanel() {
		backgroundPanel = new JPanel();
		backgroundPanel.setLayout(null);
		backgroundPanel.setBackground(Color.WHITE);
	}
	
	private JLabel getTitle(String name,String text, int x, int y, int width, int height) {

		JLabel label = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", 1, 20);
		label.setFont(lbFont);
		label.setForeground(new Color(114,114,114));
		label.setHorizontalAlignment(0);
		label.setBounds(x, y, width, height);
		label.setName(name);
		backgroundPanel.add(label);

		return label;
	}
	
	private JLabel getLabel(String name,String text, int x, int y, int width, int height) {

		JLabel label = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", 1, 20);
		label.setFont(lbFont);
		label.setHorizontalAlignment(4);
		label.setBounds(x, y, width, height);
		label.setName(name);
		backgroundPanel.add(label);

		return label;
	}
	
	private JButton setBtnWhite(String name, String text, int y) {
		JButton btn = new JButton();
		Font btnFont = new Font("맑은 고딕", Font.PLAIN, 20);
		btn.setFont(btnFont);
		btn.setBackground(Color.WHITE);
		btn.setForeground(new Color(53, 121, 247));
		btn.setBounds(35, y, 350, 45);
		btn.setText(text);
		btn.setName(name);
		backgroundPanel.add(btn);
		return btn;
	}
	
}