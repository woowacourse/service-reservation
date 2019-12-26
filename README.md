# 회의실 내껀데 :calendar:

## 간단 소개

우아한 테크코스 1기의 마지막 step4 프로젝트입니다. Slack 내에서 Google Calendar에 접근하여 회의실 예약 및 조회 등의 업무를 간편하게 도와주는 
슬랙봇입니다.  
이제 더 이상 회의실 예약을 위해 힘들게 캘린더를 찾지 마세요! 회의실은 이제 내꺼라구요 :money_with_wings:

### 개발한 사람들

닉 :fox_face: 도넛 :doughnut: 버디 :four_leaf_clover: 코니 :books: 희봉 :potato:

## 사용법

애플리케이션은 두 가지 방법으로 사용하실 수 있는데요.  
`Recent App` 탭에 있는 앱을 클릭해 사용하시거나, `@회의실 내껀데` 멘션을 통해 사용하실 수 있습니다.

<p align="center">
  <img src="https://user-images.githubusercontent.com/44018338/71452740-e2319a00-27ca-11ea-9025-e236e510d016.png" alt="app home" width="300"/>
</p>

크게 세 가지 버튼이 있는데요, **:calendar: 전체 조회**, **:pushpin: 회의실 예약**, **:scissors: 예약 변경/취소** 가 있습니다.  

### 조회 기능

<p align="center">
  <img src="https://user-images.githubusercontent.com/44018338/71453771-69cdd780-27d0-11ea-8d82-86233de65257.png" alt="retrieve events" width="400"/>
</p>

먼저 조회 기능은 조회하고 싶은 날짜와 시간대를 통해 이용하실 수 있어요. :clock1030:  
별다른 선택을 하지 않으시면 화면에서 보시는 것처럼 기본적으로 **오늘 날짜, 10시 00분 ~ 21시 50분** 사이의 이벤트들을 조회해 줍니다.

<p align="center">
  <img src="https://user-images.githubusercontent.com/44018338/71452757-faa1b480-27ca-11ea-8a5c-ebddc91199a0.png" alt="retrieve results" width="400"/>
</p>

조회가 잘 되었네요! :open_hands:  
각 회의실 별로 어느 시간대에 누가 예약을 했는지를 한눈에 알아볼 수 있습니다.  

### 예약 기능

이번에는 예약을 해보겠습니다. **회의실 예약** 버튼을 클릭합니다.  

<p align="center">
  <img src="https://user-images.githubusercontent.com/44018338/71452849-a21ee700-27cb-11ea-9a27-741adf8814fe.png" alt="retrieve results" width="400"/>
</p>

예약할 날짜와 시간대를 입력합니다.  

<p align="center">
  <img src="https://user-images.githubusercontent.com/44018338/71452925-0c378c00-27cc-11ea-83c4-065da2cf4352.png" alt="retrieve results" width="400"/>
</p>

날짜와 시간대를 정하고 나면 해당 시간대에 예약 가능한, 비어있는 회의실의 목록을 볼 수 있습니다.  
이용하고 싶은 회의실을 선택합니다. 저는 회의실 1번을 선택할게요.  

<p align="center">
  <img src="https://user-images.githubusercontent.com/44018338/71452969-430da200-27cc-11ea-93b1-b915331ba829.png" alt="retrieve results" width="400"/>
</p>

장소까지 선택했다면 마지막으로 회의 목적과 대표 사용자 이름을 적어주세요.  
다 적으신 후 예약 버튼을 누르시면 예약이 완료됩니다.  
(만약 예약 도중에 다른 사용자가 먼저 같은 시간대와 같은 장소에 예약을 했다면 예약이 되지 않을 수도 있어요!)  

<p align="center">
  <img src="https://user-images.githubusercontent.com/44018338/71452970-456ffc00-27cc-11ea-8205-48a85d9ac71b.png" alt="retrieve results" width="400"/>
</p>

1시부터 2시까지 회의실 1번에 예약이 성공한 것을 볼 수 있습니다! 😙

### 변경 / 취소 기능

<p align="center">
  <img src="https://user-images.githubusercontent.com/44018338/71453210-80befa80-27cd-11ea-8f54-26893e50edf4.png" alt="retrieve results" width="400"/>
</p>

예약한 회의실은 변경 혹은 취소를 할 수 있습니다.  
날짜와 예약한 사람의 이름을 기준으로 검색하기 때문에, 정확히 입력해 주셔야 해당 기능을 사용하실 수 있어요 😃

<p align="center">
  <img src="https://user-images.githubusercontent.com/44018338/71453812-a7326500-27d0-11ea-9da9-44520ea8e68a.png" alt="retrieve results" width="400"/>
</p>

`닉`이라는 이름으로 검색하니, 방금 예약한 이벤트가 보이네요. 변경 버튼을 누르면 다음과 같이 예약을 변경할 수 있는 창이 보입니다.  

<p align="center">
  <img src="https://user-images.githubusercontent.com/44018338/71453659-aa792100-27cf-11ea-947b-c1f59f4c4551.png" alt="retrieve results" width="400"/>
</p>

---

<p align="center">
  <img src="https://user-images.githubusercontent.com/44018338/71453258-a3511380-27cd-11ea-9bf7-83b2980d9a13.png" alt="retrieve results" width="400"/>
</p>

취소 버튼을 누르면 위와 같이 한번 더 확인하는 메시지가 나와요.  

<p align="center">
  <img src="https://user-images.githubusercontent.com/44018338/71453542-0db68380-27cf-11ea-92cf-8f342d65f905.png" alt="retrieve results" width="400"/>
</p>

취소가 성공했네요!  

---

**회의실 내껀데** 앱과 함께 즐겁게 회의실 사용하세요 😆
