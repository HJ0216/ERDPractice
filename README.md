# ERD 학습
**📺강의:** ***[생각하는 데이터베이스 모델링](https://www.inflearn.com/course/%EC%83%9D%EA%B0%81%ED%95%98%EB%8A%94-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%EB%AA%A8%EB%8D%B8%EB%A7%81)***


## 학습 목표
데이터베이스를 모델링하는 방법과 절차에 대한 이해


## 데이터 모델링
현실 세계에 존재하는 데이터를 컴퓨터 세계의 데이터 베이스로 옮기는 것


## 데이터 모델링 순서
1. 요구사항 분석
    1. 엔티티(객체, 명사) 찾기
    2. 속성 찾기
    3. 관계(동사) 찾기
    4. 요구사항 정의서 작성
2. 개념적 모델링
    1. ERD 작성
3. 논리적 모델링
    1. 테이블 정의서 작성
    2. PK, FK 설정
    3. RM(Relation Model) 만들기
4. 물리적 모델링
    1. 테이블 정의서 작성
    2. SQL Script 작성
    3. 최종 PM(Physical Model) 만들기
5. DBMS 구현
    1. Reverse Engineering(물리적 모델 → SQL 스키마 생성)
    2. Forward Engineering(DB 객체 → 물리적 모델)

<br/>

### 1. 요구사항 분석
#### 목표: 요구사항 분석을 통한 데이터 찾기
#### 과정: 엔티티(객체, 명사) 찾기 → 속성(필드) 찾기 → 관계(동사) 찾기

**📜 예시 요구사항**
```txt
1. 고객은 고객코드, 고객명, 전화번호, 이메일, 주소(기본주소, 상세주소), 지역, 가입일로 되어있다.
2. 고객은 지역별로 관리되도록 한다.
3. 지역은 지역코드와 지명으로 되어 있고, 지명은 대한민국의 지역코드(02: 서울특별시)를 이용한다.
4. 한 지역에는 여러 고객이 있을 수 있다.
5. 제품은 제품코드, 제품명, 제품 구분, 제품 색상, 가격으로 되어있다.
6. 하나의 제품은 여러 색상을 가질 수 있다.
7. 고객은 등록된 제품만 구매할 수 있다.
8. 한 명의 고객은 여러 제품을 구매할 수 있고, 하나의 제품은 여러 고객이 구매할 수 있다.
9. 고객이 제품을 구매 시, 구매 수량과 구매 일자를 기록한다.
```

* 엔티티(명사) 찾기
  * 고객, 지역, 제품, 색상

* 속성(필드) 찾기
  * 고객: 고객코드, 고객명, 전화번호, 이메일, 주소(기본주소, 상세주소), 가입일
  * 지역: 지역코드, 지명
  * 제품: 제품코드, 제품명, 제품 구분, 제품색상, 가격
  * 색상: 색상코드, 색상명 

* 관계(동사) 찾기
  * 구매: 구매 수량, 구매 일자

<br/>

* Entity 종류
  * 기준 Entity: 한 번 만들어지면 거의 변경이 없는 Entity(예: 지역 등)
  * 마스터 Entity: 한 번 만들어지면 자주 바뀌지 않는 Entity(예: 고객, 제품 등)
  * 관계 Entity: 행위를 기록하기 위한 Entity(예: 구매 등)
    * ***다대다 관계의 경우, 관계 Entity에 의해 설명되어야 함***

**💡다대다 관계를 일대다 관계로 풀어야하는 이유**
```txt
고객:제품 = m:n

---------------
고객코드  제품코드
---------------
CUST001 PROD001
CUST001 PROD002
CUST001 PROD003
CUST002 PROD001
CUST003 PROD003
---------------

🚨문제
* 고객코드(PK)만으로는 중복 데이터가 발생하여 고객코드(PK)+제품코드(FK)의 조합으로 복합키를 설정해야함 → 관리의 복잡성 증가
* 만일 고객코드가 CUST001인 고객명이 고객1에서 고객11로 변경될 경우, 중복된 데이터를 모두 수정해야 함 → 실수로 인한 누락 등으로 데이터의 일관성을 해칠 위험 증가
* 고객이 제품을 구매한 일자도 함께 기록하고 싶을 경우, 고객테이블에 저장할지 제품테이블에 저장할지 모호 → 확장성이 떨어짐
```

<br/>

### 2. 개념적 모델링
#### 목표: 요구사항 정의서를 통해 ERD를 도출
* ERD
  * 엔티티와 해당 속성 및 관계를 시각적으로 표현한 것

* 속성
  * Single-Composite
    * Single: 더 이상 쪼개지지 않은 원자 값을 갖는 속성 (나이, 학번 등)
    * Composite: 몇 개의 요소로 분해될 수 있는 속성 (주소 → 시, 군, 구 등)
  * Single/Multi
    * Single: 하나의 값을 갖는 속성(나이 등)
    * Multi: 두 개 이상의 값을 갖는 속성(취미 등)

* 키 속성
  * 각 객체의 인스턴스를 식별하는 데 사용되는 속성
  * 모든 객체 인스턴스의 키 속성 값이 다름
  * 둘 이상의 속성들로 구성되기도 함

* 키 종류
  * 후보키: 유일성 + 최소성
    * 기본키: 후보키 중에서 선택된 테이블의 주 식별자
    * 대체키: 기본키를 제외한 나머지 후보키
  * 외래키: 한 테이블에서 다른 테이블의 기본키를 참조하는 속성

***엔티티 작성 후, 속성 중 후보키, 기본키, 복합 속성(하나의 속성을 2이상으로 쪼갤 수 있는 경우) 확인***

**📜 참고: ERD 작성 규칙**
![ERD_작성법](https://github.com/user-attachments/assets/00eb4de5-cb4c-42a7-b942-2821b9694d8f)


**✏️ 예시 ERD**
![온라인전자상거래플랫폼_ERD](https://github.com/user-attachments/assets/4c9c0270-ea21-4f42-adaf-15e79b8f830a)

<br/>

### 3. 논리적 모델링
#### 목표: 개념 모델링의 내용을 구체적으로 정의
* 주요 작업
  * 속성의 데이터 타입, 길이, 널 값 허용 여부, 기본 값, 제약조건 등을 세부적으로 결정
  * 테이블 정의서 작성

* 주요 특징
  * 다대다 관계에 있어서 테이블 별도 생성 필수  
  * 다중 속성 → 별도의 테이블 생성
  * ERD에서의 관계 → 외래키 설정
  * FK로 복합키 설정(고객코드, 제품코드) 시, 중복이 발생할 수 있는 경우, 독립형 PK 생성 필요  
  FK로 복합키 설정(학번, 수강과목) 시, 중복이 발생하면 안될 경우, 상속형 PK(복합키)로 사용  
  단, 이럴 경우, 쿼리가 복잡해지는 문제가 있어, 대체키(수강번호)를 사용하기도 함
  * PK가 아닐 경우, not null + unique + auto_increment 사용 X(MySQL은 해당 설정이 된 컬럼을 PK로 설정하므로)  
    → 개발자가 인위적으로 증가시키는 방식으로 사용

* Forward Engineering: 논리적 모델링 → DDL Create SQL Script
* Reverse Engineering: 논리적 모델링 ← DDL Create SQL Script

💡일대다 관계에서 외래키의 위치
  * 일대다(1:N) 관계에서 외래키(Foreign Key)는 일반적으로 다 쪽에 위치
```txt
* 부서:직원 = 1:다
  만일 부서 테이블에 직원 테이블의 PK(직원번호)를 FK로 설정할 경우,
    1. 부서 테이블의 PK(부서번호)에 여러 명의 직원번호가 입력 됨 → 제 1정규화 필요
    2. 제 1정규화 수행 후, 부서번호가 반복되어 나타남 → 복합키 설정 필요
    3. 복합키 설정 시, 부서 테이블이 직원 테이블에 불필요하게 종속되는 문제 발생
```

<br/>

**✏️ 예시 논리적 모델링**
![온라인전자상거래플랫폼_논리적_모델링](https://github.com/user-attachments/assets/a34398da-e9e7-4d91-a897-e7bd99d63748)

<br/>

### 4. 물리적 모델링
#### 목표: 논리 모델링을 실제 데이터베이스 시스템에 구현하기 위해 모델을 구체화하고, 실제 데이터베이스 시스템에서 사용될 테이블, 컬럼, 제약 조건 등의 요소들을 정의
* 주요 작업
  * 테이블 정의: 엔터티와 관계를 테이블로 변환
    * 테이블은 컬럼으로 구성, 각 컬럼은 데이터 타입과 제약 조건을 가짐
  * 컬럼 정의: 테이블 내에서 데이터를 저장하는 단위
    * 정수형, 문자열, 날짜 등의 데이터 타입을 선택할 수 있음
  * 제약 조건 정의: 데이터의 무결성을 유지하고 데이터베이스의 일관성을 보장하기 위해 사용
    * 기본 키, 외래 키, 고유 제약 조건 등을 정의할 수 있음
  * 인덱스 정의: 데이터베이스의 성능을 향상시키기 위한 목적으로 인덱스를 설정하여 특정 컬럼이나 컬럼의 조합에 대한 빠른 검색을 수행
    *  인덱스를 사용하면 데이터 검색 속도가 향상되지만, 데이터의 추가, 수정, 삭제 작업에는 약간의 오버헤드가 발생할 수 있음

**✏️ 예시 물리적 모델링**
![온라인전자상거래플랫폼_물리적_모델링](https://github.com/user-attachments/assets/503416b3-041b-4fbb-adba-5a60129e174d)

**✏️ 예시 DDL SQL Script**
```sql
-- Forward Engineering

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

-- Create the t_region table
CREATE TABLE t_region(
	region_id varchar(3) not null
	, region_name varchar(30) not null unique
	, primary key(region_id)
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
