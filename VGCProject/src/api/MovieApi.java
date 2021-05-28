package api;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import kr.or.kobis.kobisopenapi.consumer.rest.exception.OpenAPIFault;
/**
 * 영화진흥원 API를 사용하여 영화 제목을 받아오는 클래스
 * 
 * @author KimHyunYoung, JungHoJune
 * @version 1.0.0
 * 
 *
 */
public class MovieApi {
		
		
		
		static String dt = SetYesterDay();
		
		
	
		private final static String KEY = "855c2ee7bd599013c83a8ae6c3713a66";
	    private final static String TARGET_DT = dt;
	
	   List<String> nameList = new ArrayList<String>();
	   /**
	    * <ol>
	    * <li> 고유 키 값과 요청변수(어제날짜)를 이용하여 API에 원하는 값 요청</li>
	    * <li> json-simple-1.1.jar 라이브러리 이용하여 특정 값 추출</li>
	    * <ul> 
	    * <li> key값을 이용하여 영화 박스오피스에서 일간 박스오피스 리스트를 추출</li>
	    * <li> 추출해온 리스트에서 영화제목만 list 배열에 담기</li>
	    * </ul>
	    * </oi>
	    * 
	    */
	   public MovieApi() {
		   
	      String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
	      String parameter = "?key=" + KEY + "&targetDt=" + TARGET_DT;
	      
	      
	      Map<String, String> map = new HashMap<>();
	      map.put("key", KEY);
	      map.put("targetDt", TARGET_DT);
	      
	      String responseBody;
	      
	      try {
	         responseBody = new KobisOpenAPIRestService(KEY).getDailyBoxOffice(true, map);
//	         System.out.println(responseBody);
	         JsonObject json = new JsonParser().parse(responseBody).getAsJsonObject();
	         JsonArray list = ((JsonObject) json.get("boxOfficeResult")).getAsJsonArray("dailyBoxOfficeList");
	          
	           for(JsonElement e : list) {
	              String s = ((JsonObject) e).get("movieNm").toString().replace("\"", "");
//	              System.out.println(s);
	              AddMovieName(s);
	           }        
	      } catch (OpenAPIFault e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
	   
	   /**
	    * API에서 받아온 영화제목을 nameList 배열에 추가하는 함수 
	    * @param name 영화제목
	    */
	   public void AddMovieName(String name) {
	    	  
		   nameList.add(name);// 영화제목 nameList배열에 넣기
	   }
	   /**
	    * API에 요청할 값으로 어제날짜를 구하는 함수
	    * 
	    * @return yesterDay값을 String형으로 반환
	    */
	   public static String SetYesterDay() {
		   SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
		   Calendar c1 = Calendar.getInstance();
		   c1.add(Calendar.DATE, -1);
		   String yesterDay = date.format(c1.getTime());
		   
		   return yesterDay;
	   }
	   
	   
	}
