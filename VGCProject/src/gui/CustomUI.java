package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


/**
 * <p>
 * 전체 GUI를 만든데 필요한 정보들을 저장한 클래스로 <br>
 * background 패널에 붙이는 작업을 한다.
 * Label, Button, TextField를 원하는 곳에 원하는 모양으로 붙일수 있다.
 * </p>
 * @author JungHoJune,kimHyunYoung
 * @version 1.0.0
 * @since 2021.05.22
 */
@SuppressWarnings("serial")
public class CustomUI extends JFrame{
	JPanel backgroundPanel;
	
	/**
	 * 기본 생성자
	 */
	public CustomUI() {}
	
	/**
	 * 
	 * backgroundPanel을 설정해주는 생성자
	 * 
	 * @param backgroundPanel
	 */
	public CustomUI(JPanel backgroundPanel) {
		this.backgroundPanel = backgroundPanel;
	}
	
	/**
	 * <p>
	 * 우리가 원하는 모양을 만들기 전에 backgroundPanel을 설정해주는 것으로, <br>
	 * 특별한 레이아웃은 없고 배경색은 흰색,<br>
	 * 가장위에 색은 blue,그 밑은 gray로 설정해준다.<br>
	 * 또한, 설정을 해줄 때 원하는 위치를 설정하기 위해 X,Y와 크기를 설정해준다.
	 * </p>
	 */
	protected void setPanel() {
		backgroundPanel.setLayout(null);
		backgroundPanel.setBackground(Color.WHITE);
		
		JPanel topBluePanel = new JPanel();
		topBluePanel.setBounds(0, 0, 420, 70);
		topBluePanel.setBackground(new Color(53, 121, 247));
		backgroundPanel.add(topBluePanel);
		
		JPanel topGrayPanel = new JPanel();
		topGrayPanel.setBounds(0, 70, 420, 50);
		topGrayPanel.setBackground(new Color(230, 236, 240));
		backgroundPanel.add(topGrayPanel);
	}
	
	/**
	 * 
	 *<p>
	 *로그인 GUI에서 ID와 회원가입 GUI에서 이름,ID,생년월일,핸드폰 번호를 입력받을 TextField를 생성할 떄 사용한다.<br>
	 *또한, 우리가 사용자에게 입력받을 정보가 있다면 위치정보와 크기 정보를 정해 이 함수를 이용하면 좀 더 쉽게 생성할 수 있다.
	 *</p>
	 * 
	 * 
	 * @param name : TextField의 이름을 정해준다.
	 * @param placeholder : TextField에 들어갈 내용을 정해준다.
	 * @param x : panel에서 x좌표 위치
	 * @param y : panel에서 y좌표 위치
	 * @param width : 사용할 TextField의 가로 길이
	 * @param height : 사용항 TextField의 세로 길이
	 * @return txt : 우리가 정해준 TextField를 반환해준다.
	 */
	protected JTextField setTextField(String name, String placeholder, int x, int y, int width, int height) {
		JTextField txt = new JTextField();
		
		if (placeholder == null) {
			txt.setText("Please input here");
		} else {
			txt.setText(placeholder);
		}
		
		Font tfFont = new Font("맑은 고딕", Font.PLAIN, 20);
		txt.setFont(tfFont);
		txt.setBackground(Color.white);
		txt.setForeground(Color.gray.brighter());
		
		txt.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				JTextField tf = (JTextField)e.getComponent();
				if(tf.getText().equals("")) {
					if (placeholder == null) {
						tf.setForeground(Color.gray.brighter());
						tf.setText("Please input here");
					} else {
						tf.setForeground(Color.gray.brighter());
						tf.setText(placeholder);
					}
				}
			}
			public void focusGained(FocusEvent e) {
				JTextField tf = (JTextField)e.getComponent();
				if (tf.getText().equals(placeholder) || tf.getText().equals("Please input here") || tf.getText().equals("")) {
					tf.setText("");
					tf.setForeground(Color.BLACK);
				}
			}
		});
		
		txt.setBounds(x, y, width, height);
		backgroundPanel.add(txt);
		txt.setName(name);
		
		return txt;
	}
	
	/**
	 * 
	 * <p>
	 *로그인 GUI에서 ID와 회원가입 GUI에서 비밀번호 입력받을 TextField를 생성할 떄 사용한다.<br>
	 *</p>
	 * @param name : TextField의 이름을 정해준다.
	 * @param placeholder : TextField에 들어갈 내용을 정해준다
	 * @param x : panel에서 x좌표 위치
	 * @param y : panel에서 y좌표 위치
	 * @param width : 사용할 TextField의 가로 길이
	 * @param height : 사용항 TextField의 세로 길이
	 * @return txt : 우리가 정해준 TextField를 반환해준다.
	 */
	
	
	protected JPasswordField setPasswordField(String name, String placeholder, int x, int y, int width, int height) {
		JPasswordField txt = new JPasswordField();
		
		if (placeholder == null) {
			txt.setText("Please input here");
		} else {
			txt.setText(placeholder);
		}
		
		Font tfFont = new Font("Arial", Font.PLAIN, 20);
		txt.setFont(tfFont);
		txt.setBackground(Color.white);
		txt.setForeground(Color.gray.brighter());
		
		txt.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				JTextField tf = (JTextField)e.getComponent();
				if(tf.getText().equals("")) {
					if (placeholder == null) {
						tf.setForeground(Color.gray.brighter());
						tf.setText("Please input here");
					} else {
						tf.setForeground(Color.gray.brighter());
						tf.setText(placeholder);
					}
				}
			}
			public void focusGained(FocusEvent e) {
				JTextField tf = (JTextField)e.getComponent();
				if (tf.getText().equals(placeholder) || tf.getText().equals("Please input here") || tf.getText().equals("")) {
					tf.setText("");
					tf.setForeground(Color.BLACK);
				}
			}
		});
		
		txt.setBounds(x, y, width, height);
		backgroundPanel.add(txt);
		txt.setName(name);
		
		return txt;
	}
	/**
	 * 
	 * <p>
	 * 우리가 만들 프로그램에서 파란색 버튼을 만들때 사용한다.<br>
	 * 버튼의 크기는 정해져있고 y좌표의 위치만 입력해주면 된다.<br>
	 * 
	 * </p>
	 * 
	 * @param name : 버튼의 이름을 정해준다.
	 * @param text : 버튼에 보여질 내용을 입력한다.
	 * @param y	: 버튼이 있어야할 위치를 입력
	 * @return btn : 버튼 자체를 리턴해준다.
	 */
	protected JButton setBtnBlue(String name, String text, int y) {

		class RoundedButton extends JButton {
			public RoundedButton() {
				super();
				decorate();
			}

			protected void decorate() {
				setBorderPainted(false); //버튼의 외곽선을 없애준다
				setOpaque(false);// 버튼을 투명하게 해준다.
			}

			protected void paintComponent(Graphics g) {
				int width = getWidth();
				int height = getHeight();
				Graphics2D graphics = (Graphics2D) g;
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				if (getModel().isArmed()) {
					graphics.setColor(getBackground().darker());
				} else if (getModel().isRollover()) {
					graphics.setColor(getBackground().brighter());
				} else {
					graphics.setColor(getBackground());
				}
				graphics.fillRoundRect(0, 0, width, height, 15, 15);
				FontMetrics fontMetrics = graphics.getFontMetrics();
				Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
				int textX = (width - stringBounds.width) / 2;
				int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();
				graphics.setColor(getForeground());
				graphics.setFont(getFont());
				graphics.drawString(getText(), textX, textY);
				super.paintComponent(g);
			}
		}

		RoundedButton btn = new RoundedButton();
		btn.setBackground(new Color(53, 121, 247));
		Font btnFont = new Font("맑은 고딕", Font.PLAIN, 20);
		btn.setFont(btnFont);
		btn.setBackground(new Color(53, 121, 247));
		btn.setForeground(Color.WHITE);
		btn.setBounds(35, y, 350, 45);
		btn.setText(text);
		backgroundPanel.add(btn);
		btn.setName(name);

		return btn;
	}
	/**
	 * 
	 * <p>
	 * 우리가 만들 프로그램에서 흰색 버튼을 만들때 사용한다.<br>
	 * 버튼의 크기는 정해져있고 y좌표의 위치만 입력해주면 된다.
	 * </p>
	 * 
	 * @param name : 버튼의 이름을 정해준다.
	 * @param text : 버튼에 보여질 내용을 입력한다.
	 * @param y	: 버튼이 있어야할 위치를 입력
	 * @return btn : 버튼 자체를 리턴해준다.
	 */
	protected JButton setBtnWhite(String name, String text, int y) {

		class RoundedBorder implements Border {
			int radius;

			RoundedBorder(int radius) {
				this.radius = radius;
			}

			public Insets getBorderInsets(Component c) {
				return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
			}

			public boolean isBorderOpaque() {
				return true;
			}

			public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
				g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
			}
		}

		JButton btn = new JButton();
		btn.setBorder(new RoundedBorder(15));
		Font btnFont = new Font("맑은 고딕", Font.PLAIN, 20);
		btn.setFont(btnFont);
		btn.setBackground(Color.WHITE);
		btn.setForeground(new Color(53, 121, 247));
		btn.setBounds(35, y, 350, 45);
		btn.setText(text);
		backgroundPanel.add(btn);
		btn.setName(name);

		return btn;
	}
	/**
	 * 
	 * <p>
	 * 영화좌석에서 보여줄 좌석을 버튼으로 표현을 했는데 이때 수 많은 좌석을 표현 한다.<br>
	 * 버튼의 크기는 정해져 있으며 버튼이 들어갈 x,y좌표를 설정해주면 된다.
	 * </p>
	 * 
	 * @param name : 버튼의 이름을 정해준다.
	 * @param text : 버튼에 보여질 내용을 입력한다.
	 * @param x : 버튼이 있어야할 x 위치를 입력.
	 * @param y	: 버튼이 있어야할 y 위치를 입력.
	 * @return btn : 버튼 자체를 리턴해준다.
	 */
	
	protected JButton setBtnSeat(String name, String seat, int x, int y) {
		JButton btn = new JButton(seat);
		
		Font btnFont = new Font("맑은 고딕", Font.BOLD, 14);
		btn.setFont(btnFont);
		btn.setBackground(new Color(230, 236, 240));
		btn.setForeground(new Color(114, 114, 114));
		btn.setBorderPainted(false);
		btn.setBounds(x, y, 53, 48);
		backgroundPanel.add(btn);
		btn.setName(name);
		
		return btn;
	}
	/**
	  * 
	 * <p>
	 * 영화좌석에서 보여줄 스크린 버튼을 표현해준다.<br>
	 * 스크린의 x위치와 크기는 정해져 있으며 들어가야할 y좌표를 입력해주면 된다.
	 * </p> 
	 * @param name : 버튼의 이름을 정해준다.
	 * @param text : 버튼에 보여질 내용을 입력한다.
	 * @param y	: 버튼이 있어야할 y 위치를 입력.
	 * @return btn : 정해진 버튼을 리턴해준다.
	 */
	protected JButton setbtnBar(String name, String text, int y) {
		JButton btn = new JButton();
		
		Font btnFont = new Font("맑은 고딕", Font.BOLD, 14);
		btn.setFont(btnFont);
		btn.setBackground(new Color(230, 236, 240));
		btn.setForeground(new Color(114, 114, 114));
		btn.setBorderPainted(false);
		btn.setBounds(45, y, 334, 40);
		btn.setText(text);
		backgroundPanel.add(btn);
		btn.setName(name);
		
		return btn;
	}
	
	/**
	 * <p>
	 *	프로그램에서 보여질 라벨중에서 왼쪽에 표현되는 라벨들을 나타낸다.<br>
	 *	이것을 사용할 때는 x,y의 위치를 정해주고 크기까지 정해줘야 한다.<br>
	 * 또한 폰트의 크기와 정렬의 위치, 폰트의 두께까지 정해줘야 한다.
	 *	
	 * </p> 
	 * @param name 라벨의 이름을 정해준다
	 * @param text 라벨에 보여줄 텍스트를 정해준다
	 * @param x 라벨의 x위치를 정해준다
	 * @param y 라벨의 y위치를 정해준다
	 * @param width 라벨의 크기중 가로길이를 정해준다
	 * @param height 라벨의 크기중 세로길이를 정해준다
	 * @param alignment 라벨에서 이름의 좌측,중앙,우측 정렬을 정해준다.
	 * @param fontSize 라벨에서의 글자 크기를 정해준다.
	 * @param weight 글자의 두께를 설정해준다
	 * @return lb 정해진 라벨을 반환해준다.
	 */
	protected JLabel setLb(String name, String text, int x, int y, int width, int height, String alignment, int fontSize, String weight) {
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", setWeight(weight), fontSize);
		lb.setFont(lbFont);
		lb.setForeground(new Color(114, 114, 114));
		lb.setHorizontalAlignment(setAlign(alignment));	
		lb.setBounds(x, y, width, height);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}
	
	/**
	 * <p>
	 *	스크롤 panel에 붙일 label들이다. setLb와 똑같지만<br>
	 *	우리가 붙일 패널을 설정해줘야 한다.
	 *  
	 * 
	 *	
	 * </p> 
	 * @param name 라벨의 이름을 정해준다
	 * @param text 라벨에 보여줄 텍스트를 정해준다
	 * @param x 라벨의 x위치를 정해준다
	 * @param y 라벨의 y위치를 정해준다
	 * @param width 라벨의 크기중 가로길이를 정해준다
	 * @param height 라벨의 크기중 세로길이를 정해준다
	 * @param alignment 라벨에서 이름의 좌측,중앙,우측 정렬을 정해준다.
	 * @param fontSize 라벨에서의 글자 크기를 정해준다.
	 * @param weight 글자의 두께를 설정해준다
	 * @param panel 우리가 붙일 패널을 설정해준다.
	 * @return lb 정해진 라벨을 반환해준다.
	 */
	protected JLabel setLb(String name, String text, int x, int y, int width, int height, String alignment, int fontSize, String weight, JPanel panel) {
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", setWeight(weight), fontSize);
		lb.setFont(lbFont);
		lb.setForeground(new Color(114, 114, 114));
		lb.setHorizontalAlignment(setAlign(alignment));	
		lb.setBounds(x, y, width, height);
		lb.setName(name);
		
		return lb;
	}
	/**
	 * <p>
	 * 영화의 상영관람나이를 보여 줄 box를 만드는 작업을한다.<br>
	 * 이것을 사용할 때는 x,y의 위치를 정해주고 크기까지 정해줘야 한다.<br>
	 * 상영관람 나이마다 표현하는 폰트의 색이 다르다.
	 * </p>
	 * @param name 라벨의 이름을 정해준다
	 * @param text 라벨에 보여줄 내용을 정해준다
	 * @param x 라벨의 x 위치를 정해준다
	 * @param y 라벨의 y 위치를 정해준다
	 * @param panel 라벨이 붙을 패널을 정해준다
	 * @return lb 정해진 라벨을 반환해준다
	 */
	protected JLabel setLbBox(String name, String text, int x, int y, JPanel panel) {
		JLabel lb = new JLabel(text);
		int age = Integer.parseInt(text);
		
		if(age == 99) {
			lb.setFont(new Font("맑은 고딕", Font.BOLD, 0));
			lb.setBackground(new Color(53, 121, 247));
		} else if(age >= 19) {
			lb.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			lb.setBackground(Color.RED);
		} else if (age <= 0) {
			lb.setFont(new Font("맑은 고딕", Font.BOLD, 10));
			lb.setBackground(Color.BLUE);
			lb.setText("��ü");
		} else {
			lb.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			lb.setBackground(Color.GREEN);
		}
		lb.setOpaque(true);
		lb.setForeground(Color.WHITE);
		lb.setHorizontalAlignment(SwingConstants.CENTER);
		lb.setBounds(x, y, 27, 27);
		lb.setName(name);
		
		return lb;
	}
	
	protected JLabel setLbImg(String name, String url, int x, int y) {
		JLabel lb = new JLabel();
		
		lb.setIcon(null);
		lb.setHorizontalAlignment(SwingConstants.CENTER);		
		lb.setBounds(x, y, 150, 150);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}
	
	/**
	 * <p>
	 * 자리를 선택할때 인원을 보여주는 ComboBox 만들어준다.<br>
	 * 배열로 보여줄 text를 받은 후, x,y 크기를 정해주면 된다. 
	 * </p>
	 * @param name JComboBox의 이름을 정해준다
	 * @param text JComboBox에 보여줄 내용을 배열로 보여준다.
	 * @param x JComboBox의 x 위치를 정해준다
	 * @param y JComboBox의 y 위치를 정해준다
	 * @param width JComboBox의 가로 크기를 정해준다
	 * @param height JComboBox의 세로 크기를 정해준다
	 * @return combo 정해진 JComboBox를 반환해준다
	 */
	protected JComboBox<String> setCombo(String name, String[] text, int x, int y, int width, int height){
		JComboBox<String> combo = new JComboBox<>(text);
		
		combo.setBackground(Color.WHITE);
		combo.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		combo.setBounds(x, y, width, height);
		backgroundPanel.add(combo);
		combo.setName(name);
		
		return combo;
	}
	/**
	 * 우리가 보여줄 내용을 좌,우,중앙 어디에 정렬할지 정해준다
	 * 
	 * @param alignment CENTER,LEFT,RIGHT를 입력받는다
	 * @return 기본값으로 0(중앙정렬) LEFT입력시 2(좌측정렬), RIGHT입력시 4(우측정렬)을 반환한다.
	 */
	private int setAlign(String alignment) {
		if(alignment.toUpperCase().equals("CENTER")) {
			return 0;
		} else if(alignment.toUpperCase().equals("LEFT")) {
			return 2;
		}  else if(alignment.toUpperCase().equals("RIGHT")) {
			return 4;
		} else {
			return 0;
		}
	}
	
	/**
	 * 
	 * 우리가 보여줄 내용의 폰트의 두께를 설쟁해준다.
	 * 
	 * @param weight BOLD,ITALIC 를 입력받는다.
	 * @return 기본값으로 plain 0(보통체), BOLD입력시 1(두껍게), ITALIC입력시 2(기울임체)를 반환한다.
	 */
	
	private int setWeight(String weight) {
		if(weight.toUpperCase().equals("BOLD")) {
			return 1;
		}  else if(weight.toUpperCase().equals("ITALIC")) {
			return 2;
		} else {
			return 0;
		}
	}

	
}