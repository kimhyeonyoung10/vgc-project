package api;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dao.MovieDao;
import dao.MovieDetailDao;

/**
 * 
 * NaverApi는 public class로 영화진흥원 API에서 전날의 영화 TOP10 을 받아온 후
 * 영화 제목을 사용하여 네이버 API에서 우리가 원하는 정보를 가져온 후 
 * 사용하는 DB에 값을 넣는 작업을 한다.
 * 
 * <p>
 * 우리가 여기서 필요한 값들은 DB와 연동시키기 위한 JDBC_DRIVER, DB_URL, USERNAME, PASSWORD<br>
 * 네이버 API를 사용하기 위한 clientId, clientSecret 값이 필요하다.<br>
 * </p>
 * 
 *  
 * <ul>
 * <li> 네이버API연결
 * <li> 이미지 URL 받아오기
 * <li> DB에 영화제목, URL 넣기(URL 넣는 것은 추가해야 함)
 * </ul>
 * 
 * @author JungHoJune,kimHyunYoung
 * @version 1.0.0
 * @since 2021.05.22
 */


public class NaverApi {
	private final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost:3306/VGC_Project?" 
									+ "useUnicode=true"
									+ "&characterEncoding=utf8";
	
	private final String USERNAME = "root";
	private final String PASSWORD = "1234";
	
	
	public List<String> movieTitle = new ArrayList<String>();
	public List<String> directorList = new ArrayList<String>();
	public List<String> actorList = new ArrayList<String>();
	public List<String> userRatingList = new ArrayList<String>();
	public List<String> imageList = new ArrayList<String>();
	
	
	/**
	 * 
	 * <p>
	 * 첫번째 순서로 영화진흥원API를 사용하는 객체를 생성한다.<br>
	 * 그 후 반복문을 사용하여 네이버 API를 사용한다. <br> 
	 * 이때 동일 영화제목의 값이 많이 나오기 떄문에 우리는 가장 최신의 영화만 받아온다.<br>
	 * 영화 제목을 받아오면 DB에 추가 해준다.
	 * </p>
	 * 
	 *<ul>
	 *<li> clientId : 네이버에서 발급 받은 클라이언트 아이디 값
	 *<li> clientSecret : 네이버에서 발급 받은 클라이언트 시크릿 값
	 *<li> apiURL : 우리가 사용할 네이버 API주소 
	 *<ul>
	 * 
	 */
	public NaverApi() {
			Connection conn = null;
			PreparedStatement insertMoviePs =null;
			PreparedStatement insertMovieInfoPs =null;
			PreparedStatement deletePs = null;
			PreparedStatement ps0 = null;
			PreparedStatement ps1 = null;
			String clientId = "CzGOzynaewt6X1wWR9h6"; // // 애플리케이션 클라이언트 아이디값"
			String clientSecret = "tbr1mNdVr6";  // 애플리케이션 클라이언트 시크릿값"

			MovieApi name = new MovieApi();
			String movieName = "";
			
			//List<String> imgurlList = new ArrayList<>();
			// 영화 title 반복문으로 movieName애 저장한 후 찾은 후 image url 받아오기
			for (int i = 0; i < name.nameList.size(); ++i) {
				movieName = name.nameList.get(i);
				movieTitle.add(movieName);

				
				// 영화 title 반복문으로 text애 저장한 후 찾은 후 apiurl설정
				String text = null;
				try {
					text = URLEncoder.encode(movieName, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException("검색어 인코딩 실패", e);
				}
				


				String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text;

				Map<String, String> requestHeaders = new HashMap<>();
				requestHeaders.put("X-Naver-Client-Id", clientId);
				requestHeaders.put("X-Naver-Client-Secret", clientSecret);
				String responseBody = get(apiURL, requestHeaders);
				


				//////////////////////////이미지 url 받아오기
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject;
				

				String path = "C:\21_03_22_hyun0";
				String format = "gif";

				
				
				try {
					jsonObject = (JSONObject) jsonParser.parse(responseBody);
					JSONArray infoArray = (JSONArray) jsonObject.get("items");
					JSONObject itemObject = (JSONObject) infoArray.get(0);
					
					
					String actor = (String)itemObject.get("actor");
					actorList.add(actor);
					
					
					String director = (String)itemObject.get("director");
					directorList.add(director);
					
					String userRating = (String)itemObject.get("userRating");
					userRatingList.add(userRating);
					
					String url = (String) itemObject.get("image");
					imageList.add(url);
					
					
					
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
				////////////////////////////////////////////movieDB에 정보 넣기
			
				try {
					Class.forName(JDBC_DRIVER);
					conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
					
					String set0Sql = "SET FOREIGN_KEY_CHECKS=0";
					String deleteSql = "TRUNCATE TABLE movie";
					String set1Sql = "SET FOREIGN_KEY_CHECKS=1";
					
					ps0 =conn.prepareStatement(set0Sql);
					ps0.execute();
					
					deletePs = conn.prepareStatement(deleteSql);
					deletePs.execute();
					
					ps1 =conn.prepareStatement(set1Sql);
					ps1.execute();

				} catch (SQLException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						if(ps1 != null ) {
							ps1.close();
						}
						if (deletePs != null) {
							deletePs.close();
						}
						if(ps0 != null) {
							ps0.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
	
			
			for(int i = 0; i<movieTitle.size(); ++i) { 
				try {
					
					
					Class.forName(JDBC_DRIVER);
					conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
					
					
					
					String insertSql ="INSERT INTO movie VALUES(NULL, ?,?)";
					insertMoviePs = conn.prepareStatement(insertSql);
					
					insertMoviePs.setString(1, movieTitle.get(i));
					insertMoviePs.setInt(2, (int)(Math.random()*20) + 100);
					insertMoviePs.execute();

				} catch (SQLException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						if(insertMoviePs != null) {insertMoviePs.close();}
						if(conn != null) {conn.close();}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		//////////////////////////
			
			for(int i = 0; i<movieTitle.size(); ++i) {
				try {
					Class.forName(JDBC_DRIVER);
					conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
					
					MovieDao dao = MovieDao.getInstance();
					int  id = dao.selectId(movieTitle.get(i));
					
					
					
					String sql = "INSERT INTO movie_info(movie_id, Director, Actor, Grade, poster) VALUES(?,?,?,?,?)";
					insertMovieInfoPs =conn.prepareStatement(sql);
					
					insertMovieInfoPs.setInt(1, id);
					insertMovieInfoPs.setString(2, directorList.get(i));
					
					insertMovieInfoPs.setString(3, actorList.get(i));
				
					insertMovieInfoPs.setString(4, userRatingList.get(i));
					
					insertMovieInfoPs.setString(5, imageList.get(i));
					
					
					insertMovieInfoPs.execute();
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {
						if(insertMovieInfoPs != null) {insertMovieInfoPs.close();}
						if(conn != null) {conn.close();}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} 
			
	}
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * 
	 * <p>
	 * 우리가 사용할 네이버API와 와 연동되어 원하는 값을 불러온다.<br>
	 * </p>
	 * 
	 * 
	 * @param apiUrl 우리가 사용할 네이버API의 주소
	 * @param requestHeaders 우리가 사용할 네이버API의 아이디와 비밀번호가 있다.
	 * @return readBody() : api와 정상적으로 연결되었지만 원하는 값이 있을 경우,<br>
	 * 			RuntimeException : api와 연결이 되었지만 원하는 값이 없을 경우.
	 */
	private static String get(String apiUrl, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {// 정상 호출
				return readBody(con.getInputStream());
			} else { // 에러 발생
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}
	/**
	 * <p>
	 * 네이버API와 직접적으로 연동시켜준다.
	 * </p>
	 * 
	 * @param apiUrl
	 * @return (HttpURLConnection) url.openConnection() : 연동이 잘된다면<br>
	 * 			 RuntimeException : APIURL이 잘못된 주소라면<br>
	 * 			RuntimeException : 연동이 되지 않는다면.
	 */
	private static HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다.: " + apiUrl, e);
		}
	}
	/**
	 * <p>
	 * 연동이 되었다면 우리가 원하는 검색어의 내용을 읽어온다.
	 * </p>
	 * 
	 * @param body
	 * @return responseBody.toString() : 검색어가 있다면<br>
	 * 			RuntimeException : 검색어가 없다면.
	 */
	private static String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}
			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
		}
	}
}