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
 	<img src="https://user-images.githubusercontent.com/81146708/120014079-92d54600-c01c-11eb-9de5-f0fd08630c8a.PNG" width="700"> 
	<img src="https://user-images.githubusercontent.com/81146708/120015078-d54b5280-c01d-11eb-8939-f4fb79f21978.png" width="800"> 



<div>
	<a href="https://www.youtube.com/watch?v=y2-JB5auRo0" target="_blank"><image src = "https://img.youtube.com/vi/y2-JB5auRo0/mqdefault.jpg"></a>	

</div>

[JavaDoc 보러가기](https://kimhyeonyoung10.github.io/vgc-project/VGCProject/doc/index.html)

