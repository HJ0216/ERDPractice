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

---
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

---
### DM #2 Conceptual Data Modeling(ERD)
* ERD
  * 엔티티(객체), 해당 속성(속성) 및 엔티티 간의 관계를 시각적으로 나타냄
![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/46b19ffd-1a34-426c-b433-0eaf71489dbe/1d62751b-5d13-41c4-8992-cf6af66da52c/image.png)

* 키 속성
  * 각 객체의 인스턴스를 식별하는 데 사용되는 속성
  * 모든 객체 인스턴스의 키 속성 값이 다름
  * 둘 이상의 속성들로 구성되기도 함

* 종류
  * PK/FK
  * 자연키/외래키

* 속성
  * Single(더 이상 쪼개지지 않은 원자 값을 갖는 속성: 나이, 학번 등)/Composite(몇 개의 요소로 분해될 수 있는 속성: 주소 → 시, 군, 구 등)
    * Single-value(나이 등)/Mutil-value(취미 등)

***엔티티 작성 후, 속성 중 후보키, 기본키, 복합 속성(하나의 속성을 2이상으로 쪼갤 수 있는 경우) 찾아 보기***

---
### DM #3 Logical Data Modeling(RM)
* 논리적 모델링  
  * DBMS에 적합한 논리적 스키마(구조, 테이블) 설계

* 주요 작업
  * 속성의 데이터 타입, 길이, 널 값 허용 여부, 기본 값, 제약조건 등을 세부적으로 결정
  * 테이블 정의서 작성

* 특징
  * ERD에서의 관계 → 외래키 설정
  * 다중 속성 → 별도의 테이블 생성
  * 다대다 관계에 있어서 테이블 별도 생성 필수  
  그렇지 않을 경우, 불필요한 데이터 중복 저장 및 PK 중복 문제 발생
  * FK로 복합키 설정(고객코드, 제품코드) 시, 중복이 발생할 수 있는 경우, 독립형 PK 생성 필요  
  FK로 복합키 설정(학번, 수강과목) 시, 중복이 발생하면 안될 경우, 상속형 PK(복합키)로 사용  
  단, 이럴 경우, 쿼리가 복잡해지는 문제가 있어, 대체키(수강번호)를 사용하기도 함
    * PK가 아닐 경우, not null + unique + auto_increment 사용 X(MySQL은 해당 설정이 된 컬럼을 PK로 설정하므로)  
    → 개발자가 인위적으로 증가시키는 방식으로 사용

* 논리적 모델링 → DDL Create SQL Script: Forward Engineering
* 논리적 모델링 ← DDL Create SQL Script: Reverse Engineering

---
### DM #4 Physical Data Modeling(PM)
![erd_practice_reverse_engineering](https://github.com/user-attachments/assets/b30c1f76-f18c-4d61-b446-81333758ff06)
```sql
-- Create the t_region table
CREATE TABLE t_region(
	region_id varchar(3) not null
	, region_name varchar(30) not null unique
	, primary key(region_id)
);

-- Create the t_customer table
CREATE TABLE t_customer(
	customer_id int not null auto_increment
	, region_id varchar(3) not null
	, phone char(11) not null unique
	, email varchar(50) not null unique
	, customer_name varchar(50) not null
	, base_address varchar(100)
	, detailed_address varchar(100)
	, registration_date datetime default now()
	, primary key(customer_id)
	, foreign key(region_id) references t_region(region_id)
);

-- Create the t_product table
CREATE TABLE t_product(
product_id int not null auto_increment
, product_name varchar(50) not null
, product_price int not null
, primary key(product_id)
);

-- Create the t_color table
CREATE TABLE t_color(
	color_id int not null auto_increment
	, color_name varchar(30) not null unique
	, primary key(color_id)
);

-- Create the t_product_color table
CREATE TABLE t_product_color(
	product_color_id int not null auto_increment
	, product_id int not null
	, color_id int not null
	, primary key(product_color_id)
	, foreign key(product_id) references t_product(product_id)
	, foreign key(color_id) references t_color(color_id)
);

-- Create the t_sales table
CREATE TABLE t_sales(
	sales_id int not null auto_increment
	, customer_id int not null
	, product_id int not null
	, sales_quantity int not null
	, sales_date datetime default now()
	, primary key(sales_id)
	, foreign key(customer_id) references t_customer(customer_id)
	, foreign key(product_id) references t_product(product_id)
);
```
