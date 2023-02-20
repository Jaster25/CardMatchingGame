# 같은 그림 찾기 게임

세종대학교 Java 수업 팀 프로젝트

🔗 [발표 PPT 링크](https://onedrive.live.com/view.aspx?resid=37D925D4498BC6CA!1666&ithint=file%2Cpptx&authkey=!AEPMe6szJlL-xOw)

🔗 [카드 이미지 출처](https://game-icons.net/)

| <img src="https://imgur.com/c4DIVs7.png" width=350/> | <img src="https://imgur.com/bopgn23.png" width=330/> |
| :--------------------------------------------------: | :--------------------------------------------------: |
|                       첫 화면                        |                       인 게임                        |

| <img src="https://imgur.com/sABKw0L.gif" /> | <img src="https://imgur.com/4Hws9bN.gif"/> | <img src="https://imgur.com/dLO7TUg.gif" /> |
| :-----------------------------------------: | :----------------------------------------: | :-----------------------------------------: |
|                    Easy                     |                   Normal                   |                    Hard                     |

<br>

## **🔍 Features**

- 3가지 난이도
- 일시 정지 기능
- 배경 음악 및 효과음
- 점수 알고리즘

  |        | 카드 수 | 보너스 타임 시간(초) |
  | ------ | ------- | -------------------- |
  | Easy   | 8       | 20                   |
  | Normal | 16      | 30                   |
  | Hard   | 24      | 45                   |

  - 맞춘 경우
    ```java
    if (maxTime > sec)
    	score += (100 + (maxTime - sec) * 50) * ++combo;
    else
    	score += 100 * ++combo;
    ```
  - 틀린 경우
    ```java
    combo = 0;
    ```

- 덱 생성 알고리즘

  ```java
  List<Card> deck = new ArrayList<>();
  List<Integer> usedCardTypes = new ArrayList<>();

  for (int i = 1; i <= 4; ++i) {
  	int randomType;
  	while (true) {
  		randomType = (int) (Math.random() * CARD_TYPES + 1);
  		if (!usedCardTypes.contains(randomType)) {
  			usedCardTypes.add(randomType);
  			break;
  		}
  	}

  	Card newCard1 = new Card(randomType);
  	Card newCard2 = new Card(randomType);

  	deck.add(newCard1);
  	deck.add(newCard2);
  }
  ```

<br>

## **🏗 Architecture**

![architecture](https://imgur.com/lgvWSYs.png)

<br>

## **🧱 Tech Stack**

![자바 뱃지](https://img.shields.io/badge/JAVA%2011-007396?style=for-the-badge&logo=java&logoColor=white.png)

<br>

## **📸 Preview**

- 진행 순서
  ![진행 순서](https://imgur.com/jvOCi8E.png)
- 난이도별 화면
  ![난이도별 화면](https://imgur.com/FOGpqGF.png)
- 인 게임 화면
  ![인 게임 화면](https://imgur.com/OKuPiqR.png)
- 일시 정지 기능
  ![일시 정지 기능](https://imgur.com/0vZjqc0.png)

<br>

## 🐾 Dev History

- 11월 16일
  - 프로젝트 시작
  - 카드 클래스, 보드 클래스 구현
- 11월 20일
  - 프로젝트 구조 설계
- 11월 22일
  - 이미지 샘플로 게임 기능 구현
  - 사운드 추가
    - 카드 뒤집힐 때
    - 정답/오답
- 11월 23일
  - 코드 리팩토링
  - 게임 난이도 설정 UI 구현
- 11월 25일
  - 카드 이미지 변경
- 11월 26일
  - 게임 종료 UI 생성
  - 난이도별 버튼에 맞게 이미지 사이즈 조정
- 11월 27일
  - 버튼 클래스를 생성해서 공통으로 사용하도록 변경
- 11월 28일
  - (게임 종료/메뉴로/재시작) 창 UI 클래스 구현 방식 변경
  - 점수 기능 추가(알고리즘 미정)
  - 타이머 버튼 형식 추가
  - 버튼 클릭음 추가
  - 배경화면 이미지 추가
- 11월 30일
  - 점수 알고리즘 구현
- 11월 31일 ~ 12월 2일
  - 게임 내 버그 수정 시도(실패)
- 12월 3일
  - 버그 수정
    - 게임 재시작 시 게임 정보 변수들이 멈추는 버그(해결)
    - 게임 재시작 시 타이머가 x배로 빨라지는 버그(해결)
  - 카드 이미지 가볍게 변경
- 12월 4일
  - GameStartUI 배경화면 추가
  - 타이머 사운드 추가
  - 대량의 카드 이미지들 중에 랜덤으로 뽑아서 덱 생성하는 알고리즘 추가
  - 일시 정지 상태에서 메뉴 버튼 클릭 시 일시 정지 풀리는 버그(해결)
- 12월 5일
  - 디자인 변경
  - **프로젝트 구현 완료**

<br>

## **⚠️ License**

Copyright © 2019 [Jaster25](https://github.com/Jaster25), [donut0310](https://github.com/donut0310)

This project is MIT licensed.
