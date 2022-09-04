CREATE SCHEMA db_project_rv;

CREATE TABLE tbl_hotel (
	locate VARCHAR(20),     #지역
    h_name VARCHAR(50),     #호텔이름
	r_num INT,              #객실번호
    room CHAR(10),          #객실종류
    reservable BOOLEAN,     #예약가능여부
    price INT				#가격    
); -- 호텔정보
ALTER TABLE tbl_hotel modify room VARCHAR(50);
TRUNCATE tbl_hotel;

INSERT INTO tbl_hotel VALUES ('기장','베스트루이스해밀턴호텔기장',000,'Caramella',TRUE,250000);
INSERT INTO tbl_hotel VALUES ('기장','베스트루이스해밀턴호텔기장',001,'Carino',TRUE,350000);
INSERT INTO tbl_hotel VALUES ('기장','베스트루이스해밀턴호텔기장',002,'Hinoki',TRUE,450000);
INSERT INTO tbl_hotel VALUES ('기장','브라운도트호텔',001,'Family',TRUE,100000);
INSERT INTO tbl_hotel VALUES ('기장','브라운도트호텔',002,'Premium',TRUE,150000);
INSERT INTO tbl_hotel VALUES ('기장','브라운도트호텔',003,'SweetParty',TRUE,200000);
INSERT INTO tbl_hotel VALUES ('기장','하운드호텔',001,'Deluxe',TRUE,50000);
INSERT INTO tbl_hotel VALUES ('기장','하운드호텔',002,'Twin',TRUE,100000);
INSERT INTO tbl_hotel VALUES ('기장','하운드호텔',003,'Poolvilla',TRUE,300000);
INSERT INTO tbl_hotel VALUES ('해운대','그랜드엘시티레지던스',001,'OceanJuniorSuite',TRUE,1200000);
INSERT INTO tbl_hotel VALUES ('해운대','그랜드엘시티레지던스',002,'OceanLoyal',TRUE,1400000);
INSERT INTO tbl_hotel VALUES ('해운대','그랜드엘시티레지던스',003,'OceanLSuite',TRUE,1600000);
INSERT INTO tbl_hotel VALUES ('해운대','라비드아틀란호텔',001,'HalfOceanDeluxe',TRUE,375000);
INSERT INTO tbl_hotel VALUES ('해운대','라비드아틀란호텔',002,'OceanFamilyTwin',TRUE,400500);
INSERT INTO tbl_hotel VALUES ('해운대','라비드아틀란호텔',003,'Premier',TRUE,554000);
INSERT INTO tbl_hotel VALUES ('해운대','파크하얏트호텔',001,'ExecutiveFamilySuite',TRUE,277000);
INSERT INTO tbl_hotel VALUES ('해운대','파크하얏트호텔',002,'FamilySuite',TRUE,357000);
INSERT INTO tbl_hotel VALUES ('해운대','파크하얏트호텔',003,'OceanViewKing',TRUE,420000);
INSERT INTO tbl_hotel VALUES ('중구','아스티호텔',001,'StandardDoubleOceanView',TRUE,65000);
INSERT INTO tbl_hotel VALUES ('중구','아스티호텔',002,'StandardDoubleRoomwithCityView',TRUE,75000);
INSERT INTO tbl_hotel VALUES ('중구','아스티호텔',003,'StandardTwinCityView',TRUE,85000);
INSERT INTO tbl_hotel VALUES ('중구','코모도르호텔',001,'BusinessTwin',TRUE,200000);
INSERT INTO tbl_hotel VALUES ('중구','코모도르호텔',002,'DeluxeDouble',TRUE,300000);
INSERT INTO tbl_hotel VALUES ('중구','코모도르호텔',003,'StandardDouble',TRUE,400000);
INSERT INTO tbl_hotel VALUES ('중구','크라운하버호텔',001,'DeluxeCityViewDouble',TRUE,20000);
INSERT INTO tbl_hotel VALUES ('중구','크라운하버호텔',002,'DeluxeCiryViewTwin',TRUE,30000);
INSERT INTO tbl_hotel VALUES ('중구','크라운하버호텔',003,'JuniorSuitewithHarborView',TRUE,40000);
INSERT INTO tbl_hotel VALUES ('서구','베스트웨스턴플러스호텔',001,'Business',TRUE,75000);
INSERT INTO tbl_hotel VALUES ('서구','베스트웨스턴플러스호텔',002,'City',TRUE,100000);
INSERT INTO tbl_hotel VALUES ('서구','베스트웨스턴플러스호텔',003,'Ocean',TRUE,123000);
INSERT INTO tbl_hotel VALUES ('서구','페어필드호텔',001,'StandardCity',TRUE,60000);
INSERT INTO tbl_hotel VALUES ('서구','페어필드호텔',002,'StandardOcean',TRUE,70000);
INSERT INTO tbl_hotel VALUES ('서구','페어필드호텔',003,'StandardTwin',TRUE,80000);
INSERT INTO tbl_hotel VALUES ('서구','파인힐호텔',001,'Pdeulxe',TRUE,100000);
INSERT INTO tbl_hotel VALUES ('서구','파인힐호텔',002,'Pstandard',TRUE,120000);
INSERT INTO tbl_hotel VALUES ('서구','파인힐호텔',003,'VIP',TRUE,140000);
INSERT INTO tbl_hotel VALUES ('수영구','베스트루이스해밀턴호텔광안',001,'ChicSquareTwinroom',TRUE,300000);
INSERT INTO tbl_hotel VALUES ('수영구','베스트루이스해밀턴호텔광안',002,'PremiumHalfOceanViewroom',TRUE,400000);
INSERT INTO tbl_hotel VALUES ('수영구','베스트루이스해밀턴호텔광안',003,'SignatureKing',TRUE,500000);
INSERT INTO tbl_hotel VALUES ('수영구','에이치에비뉴호텔',001,'FirstLoopTopFloor',TRUE,100000);
INSERT INTO tbl_hotel VALUES ('수영구','에이치에비뉴호텔',002,'FirstOceanBridge',TRUE,200000);
INSERT INTO tbl_hotel VALUES ('수영구','에이치에비뉴호텔',003,'ThirdOceanBeachView',TRUE,300000);
INSERT INTO tbl_hotel VALUES ('수영구','유토피아투어리스트호텔',001,'StandardDouble',TRUE,30000);
INSERT INTO tbl_hotel VALUES ('수영구','유토피아투어리스트호텔',002,'FamilySuite',TRUE,50000);
INSERT INTO tbl_hotel VALUES ('수영구','유토피아투어리스트호텔',003,'FamilyTwin',TRUE,70000);



SELECT * FROM tbl_hotel;

CREATE TABLE tbl_member (
	m_id VARCHAR(20) PRIMARY KEY, #회원아이디
    m_pw VARCHAR(20) NOT NULL,    #회원비밀번호 
    m_name VARCHAR(20) NOT NULL,  #회원이름
    m_phone CHAR(20) NOT NULL,    #회원연락처
    reserve_num INT 			  #예약번호
); -- 회원정보

CREATE TABLE tbl_non_member (
    nm_name VARCHAR(20) NOT NULL,  #비회원이름
    nm_phone CHAR(20) NOT NULL,    #비회원연락처
    nm_reserve_num INT 		  	   #예약번호
); -- 비회원정보

CREATE TABLE m_reservations (
	m_reserve_num INT PRIMARY KEY,  #예약번호
    m_reserve_hotel VARCHAR(50),    #예약호텔
    m_reserve_room VARCHAR(20), 	  #예약객실 
    m_reserve_date VARCHAR(30),     #예약날짜
	m_id VARCHAR(20),
    m_name VARCHAR(20),
    m_phone CHAR(20)
); -- 회원예약정보

SELECT * FROM nm_reservations;

CREATE TABLE nm_reservations (
	nm_reserve_num INT PRIMARY KEY,  #예약번호
    nm_reserve_hotel VARCHAR(50),    #예약호텔
    nm_reserve_room VARCHAR(20), 	  #예약객실 
    nm_reserve_date VARCHAR(30),     #예약날짜
	nm_name VARCHAR(20),
    nm_phone CHAR(20) 
); -- 비회원예약정보