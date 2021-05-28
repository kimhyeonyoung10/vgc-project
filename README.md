메가 IT 아카데미 미니 Java 프로젝트

## VGC Program

### 1. 프로젝트 소개

VGC Program은 영화를 예약하는 프로그램이다. 일일 박스오피스 순위의 영화리스트를 보여주고 영화 제목을 통해 더 상세한 정보가 나타나도록 구현하였다. 영화의 정보들을 가져오기 위해서 영화진흥위원회 API와 네이버 검색 영화 API를 사용했다. API에 요청하여 받아온 정보들은 데이터베이스를 생성하여 저장한 후 관리했다.



### 2. 개발 환경

- JDK 11
- IDE: Eclipse
- DB : MariaDB 10.5.8
- 외부 라이브러리 : gson-2.8.6.jar & json-simple-1.1.jar & kobis-opcl-rest-v1.0.jar
- https://www.kobis.or.kr/kobisopenapi/homepg/main/main.do (영화진흥위원회 API)
- https://developers.naver.com/docs/search/movie/ (네이버 Developers 검색 영화 API)



### 3. 상세 기능

- 로그인

- 회원가입(아이디 없을 시 아이디 생성 후 db에 추가)

- 영화별 예매(영화리스트에서 영화 선택 후 영화 상세정보 보여준 후 좌석 선택)

- 예매 확인(예매한 날짜 기준으로 예매 리스트를 보여줌)

- 내 정보 보기(유저의 기본정보를 보여줌)

- #### 영화진흥위원회 API 사용
```java
private final static String KEY = "855c2ee7bd599013c83a8ae6c3713a66"; // 요청할 키 값
private final static String TARGET_DT = dt; // 요청할 어제 날짜
...
    
public MovieApi() {

		String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json"; //값을 요청할 url
		String parameter = "?key=" + KEY + "&targetDt=" + TARGET_DT; // 키값과 어제 날짜를 파라미터로 API에게 요청

		Map<String, String> map = new HashMap<>();
		map.put("key", KEY);
		map.put("targetDt", TARGET_DT);
```
- #### 네이버 Developers 검색 영화 API 사용
```java
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
...
    		String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text; // 값을 요청할 url

				Map<String, String> requestHeaders = new HashMap<>();
				requestHeaders.put("X-Naver-Client-Id", clientId);
				requestHeaders.put("X-Naver-Client-Secret", clientSecret);
				String responseBody = get(apiURL, requestHeaders);
```
##### 로그인창

영화를 예매하기 위해 제일 먼저 로그인을 하게 된다. 아이디가 없을 시 회원가입 버튼을 통해 먼저 아이디를 생성한다. 그 후 로그인을 하게 되면 메인 창으로 넘어간다.

##### 회원가입 창

각각의 필드(아이디, 이름, 비밀번호, 비밀번호 확인, 전화번호, 생년월일)의 조건이 일치하는지 정규식을 통해 검사해준다. 조건이 맞지 않는다면 다시 입력하도록 한다.

조건이 모두 일치한다면 회원가입이 정상적으로 완료되고 다시 로그인창으로 돌아간다.

##### 메인 창

로그인 후 보이는 예매 사이트의 첫 화면이다. 크게 3가지로 나누어진다.

- 영화별 예매
- 예매 확인
- 내 정보 보기

#####  영화별 예매

먼저 날짜를 선택하게 된다. 현재 기준으로 이전 날짜는 선택할 수 없게 비활성화 시켰고 연한 색상으로 표현하였다. 날짜를 선택한 후 API를 통해 받아온 10개의 영화가 보여진다.

영화를 선택하게 되면 그 영화에 맞는 좀 더 자세한 정보가 보여진다. 그 다음 좌석을 선택한다. 인원수는 최대 4명까지로 설정해 두었고, 선택한 인원수를 초과할 수 없게 설정했다.

마지막으로 예약하기 버튼을 누르면 정상적으로 예약이 완료 되고 예약정보를 보여준다.

##### 예매 확인

내가 예매한 내역을 보여준다. 예매일시, 영화제목, 상영시간 순으로 나열되있고, 예매한 순서대로 보여진다.

##### 내 정보 보기

나의 기본 정보를 보여준다.



<div>
	<a href="https://www.youtube.com/watch?v=y2-JB5auRo0" target="_blank"><image src = "https://img.youtube.com/vi/y2-JB5auRo0/mqdefault.jpg"></a>	

</div>

[JavaDoc 보러가기](https://kimhyeonyoung10.github.io/vgc-project/VGCProject/doc/index.html)

