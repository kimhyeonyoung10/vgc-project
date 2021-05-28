package gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.MovieDao;
import dao.MovieDetailDao;
import dto.MovieDetails;
import dto.Movies;

/**
 * 영화 상세 정보보기 GUI클래스
 * <ol>
 * <li>영화상세 정보기본 틀 생성</li>
 * <li>각 버튼과 라벨에 리스너 추가</li>
 * </ol>
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 */

@SuppressWarnings("serial")
public class MovieDetailGUI extends CustomUI  {
	private String userId, reserveDate;
	private int movieId;
	
	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JLabel lbMovieName, lbRunningTime, lbDirector, lbActor, lbPoster, lbGrade;
	private JLabel MovieName,RunningTime,Director,Actor,Grade;
	private JButton btnBack,btnSelectSeat;
	
	/**
	 * <p>
	 * 선택된 영화의 상세 정보를 만들어 놓은 각각의 패널에 추가하는 작업을 하였다.<br>
	 * 또한, 포스터를 불러오기 위하여 저장되어 있는 URL을 아용하여 image를 생성하고 생성된 image를
	 * imageIcon으로 변경후 lbPoster의 Icon으로 사용하였다.</p>
	 * 뒤로가기 버튼에 <code>ActionListener</code>를 영화선택GUI로 넘어가도록 하였다.<br>
	 * 좌석선택 버튼에 <code>ActionListener</code>를 좌석선택GUI로 넘어가도록 하였다.
	 * 
	 * @param userId 사용자의 ID
	 * @param reserveDate 사용자가 선택한 예매날짜
	 * @param movieId 영화재목에 맞는 ID
	 */
	
	public MovieDetailGUI(String userId, String reserveDate, int movieId) {
		this.userId = userId;
		this.reserveDate = reserveDate;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		
		MovieDao mDao = MovieDao.getInstance();
		Movies title = mDao.selectOne(movieId);
		
		MovieName.setText(title.getTitle());
		RunningTime.setText(title.getRunningTime() + "분");
		
		MovieDetailDao detailDao = MovieDetailDao.getInstance();
		MovieDetails detail = detailDao.selectMoiveInfo(movieId);
		
		Director.setText(detail.getDirector().substring(0, detail.getDirector().length()-1));
		Actor.setText(detail.getActor());
		Grade.setText(detail.getGrade() + "점");
		
		URL url;
		try {
			url = new URL(detail.getPoster());
			Image image = ImageIO.read(url);
			image = image.getScaledInstance(150, 150, image.SCALE_SMOOTH);
			ImageIcon ImageIc = new ImageIcon(image);
			lbPoster.setIcon(ImageIc);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new SelectMovie1GUI(userId, reserveDate);
				frame.dispose();
			}
		});
		
		btnSelectSeat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new SeatGUI(userId, reserveDate, movieId);
				frame.dispose();
				
			}
		});
		
		frame.setSize(426, 779);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	/**
	 * 
	 * GUI에 보여질 라벨들 (lbPoster,lbMovieName, MovieName, lbRunningTime, RunningTime
	 * , lbDirector, Director, lbActor, Actor,lbGrade, Grade) 버튼(btnSelectSeat, btnBack)
	 * 을 생성하는 작업을 하였다.
	 * 
	 */
	private void init() {
		backgroundPanel = new JPanel();
		frame.setContentPane(backgroundPanel);
		frame.setTitle("");
		CustomUI custom = new CustomUI(backgroundPanel);
		custom.setPanel();
		

		lbPoster = custom.setLbImg("lbPoster","" , 150, 150);

		lbMovieName = custom.setLb("lbMovieName", "영화 제목", 35, 310, 100, 20, "left", 17, "bold");
		MovieName = custom.setLb("MovieName", "", 195, 310, 180, 20, "right", 17, "plain");

		lbRunningTime = custom.setLb("lbRunningTime", "상영 시간", 35, 360, 100, 20, "left", 17, "bold");
		RunningTime = custom.setLb("RunningTime", "", 195, 360, 180, 20, "right", 17, "plain");

		lbDirector = custom.setLb("lbDirector", "감독", 35, 410, 100, 20, "left", 17, "bold");
		Director = custom.setLb("Director", "", 195, 410, 180, 20, "right", 17, "plain");

		lbActor = custom.setLb("lbActor", "출연 배우", 35, 460, 100, 20, "left", 17, "bold");
		Actor = custom.setLb("Actor", "", 120, 460, 250, 20, "right", 17, "plain");
		
		lbGrade = custom.setLb("lbGrade", "평점", 35, 510, 100, 20, "left", 17, "bold");
		Grade = custom.setLb("Grade", "", 195, 510, 180, 20, "right", 17, "plain");
		
		btnSelectSeat = custom.setBtnBlue("btnSelectSeat", "좌석 선택", 600);
		btnBack = custom.setBtnWhite("btnBack", "영화 선택", 655);
		
	}
}