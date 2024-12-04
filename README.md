# ERD Practice
[생각하는 데이터베이스 모델링](https://www.inflearn.com/course/%EC%83%9D%EA%B0%81%ED%95%98%EB%8A%94-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%EB%AA%A8%EB%8D%B8%EB%A7%81)

### 데이터 모델링
현실 세계에 존재하는 데이터를 컴퓨터 세계의 데이터 베이스로 옮기는 과정

### 데이터 모델링 순서
1. 요구사항 분석
    1. 엔티티(객체) 찾기
        1. 객체 - 명사
        2. 관계 - 동사
    2. 속성 찾기
    3. 관계 찾기
    4. 요구사항 정의서 작성
2. 개념적 모델링
    1. ERD 그리기
3. 논리적 모델링
    1. Excel Table 정의서 작성
    2. PK, FK 설정
    3. RM(Relation Model) 만들기
4. 물리적 모델링
    1. 테이블 정의서
    2. SQL Script 작성
    3. 최종 PM(Physical Model) 만들기
5. DBMS 구현
    1. Reverse Engineering
    2. Forward Engineering

### DM #1 데이터베이스 요구사항 분석
* 요구사항 분석을 통한 데이터 찾기
* 순서: 엔티티(객체) 찾기 → 속성(필드) 찾기 → 관계 찾기

**요구사항 예시**
1. 고객은 고객코드, 고객명, 전화번호, 이메일, 주소(기본주소, 상세주소), 지역, 가입일로 되어있다.
2. 고객은 지역별로 관리되도록 한다.
3. 지역은 지역 코드와 지명으로 되어 있고, 지명은 대한민국의 지역코드(02: 서울특별시)를 이용한다.
4. 한 지역에는 여러 고객이 있을 수 있다.
5. 제품은 제품코드, 제품명, 제품구분, 제품색상, 가격으로 되어있다.
6. 하나의 제품은 여러 색상을 가질 수 있다.
7. 고객은 등록된 제품을 구매할 수 있다.
8. 한 명의 고객은 여러 제품을 구매할 수 있고, 하나의 제품은 여러 고객이 구매할 수 있다.
9. 고객이 제품을 구매 시 구매 수량과 구매 일자를 기록한다.

> 고객 T  
**고객코드(PK)**  
*지역코드(FK)*  
고객명  
전화번호  
이메일  
주소(기본주소)  
주소(상세주소)  
지역  
가입일

>지역 T  
**지역코드(PK)**  
지명

>제품 T  
**제품코드(PK)**  
제품명  
제품구분  
제품색상  
가격

>색상 T  
**색상코드(PK)**  
*제품코드(FK)*

>구매 T  
**구매 번호(PK)**  
*고객코드(FK, N)*  
*제품코드(FK, M)*  
구매 수량  
구매 일자

>관리 T  
*고객코드(FK, N)*  
*지역코드(FK, 1)*

Entity 종류
* 기준 Entity: 한 번 만들어지면 거의 변경이 없는 Entity(예: 지역 등)
* 마스터 Entity: 한 번 만들어지면 자주 바뀌지 않는 Entity(예: 고객, 제품 등)
* 관계 Entity: 행위를 기록하기 위한 Entity(예: 구매 등)
  * ***다대다 관계의 경우, 관계 Entity에 의해 설명되어야 함***
