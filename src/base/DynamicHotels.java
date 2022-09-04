package base;

public class DynamicHotels {
	
	//회원의정보
	private String m_id; 
	private String m_pw;
	private String m_name;
	private String m_phone;
	private String m_reserve_num;
	//회원의 예약 정보
	private String m_reserve_hotel;
	private String m_reserve_room  ;
	private String m_reserve_date ;
		
	
	//비회원의 정보
	private String nm_name;
	private String nm_phone;
	private String nm_reserve_num;
	
	//비회원의 예약 정보
	private String nm_reserve_hotel;
	private String nm_reserve_room  ;
	private String nm_reserve_date ;
	
	public String getM_id() {
		return m_id;
	}

	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	public String getM_pw() {
		return m_pw;
	}

	public void setM_pw(String m_pw) {
		this.m_pw = m_pw;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_phone() {
		return m_phone;
	}

	public void setM_phone(String m_phone) {
		this.m_phone = m_phone;
	}

	public String getM_reserve_num() {
		return m_reserve_num;
	}

	public void setM_reserve_num(String m_reserve_num) {
		this.m_reserve_num = m_reserve_num;
	}

	public String getM_reserve_hotel() {
		return m_reserve_hotel;
	}

	public void setM_reserve_hotel(String m_reserve_hotel) {
		this.m_reserve_hotel = m_reserve_hotel;
	}

	public String getM_reserve_room() {
		return m_reserve_room;
	}

	public void setM_reserve_room(String m_reserve_room) {
		this.m_reserve_room = m_reserve_room;
	}

	public String getM_reserve_date() {
		return m_reserve_date;
	}

	public void setM_reserve_date(String m_reserve_date) {
		this.m_reserve_date = m_reserve_date;
	}

	public String getNm_name() {
		return nm_name;
	}

	public void setNm_name(String nm_name) {
		this.nm_name = nm_name;
	}

	public String getNm_phone() {
		return nm_phone;
	}

	public void setNm_phone(String nm_phone) {
		this.nm_phone = nm_phone;
	}

	public String getNm_reserve_num() {
		return nm_reserve_num;
	}

	public void setNm_reserve_num(String nm_reserve_num) {
		this.nm_reserve_num = nm_reserve_num;
	}

	public String getNm_reserve_hotel() {
		return nm_reserve_hotel;
	}

	public void setNm_reserve_hotel(String nm_reserve_hotel) {
		this.nm_reserve_hotel = nm_reserve_hotel;
	}

	public String getNm_reserve_room() {
		return nm_reserve_room;
	}

	public void setNm_reserve_room(String nm_reserve_room) {
		this.nm_reserve_room = nm_reserve_room;
	}

	public String getNm_reserve_date() {
		return nm_reserve_date;
	}

	public void setNm_reserve_date(String nm_reserve_date) {
		this.nm_reserve_date = nm_reserve_date;
	}

	public DynamicHotels(String m_id, String m_pw, String m_name, String m_phone) {
		this.m_id = m_id;
		this.m_pw = m_pw;
		this.m_name = m_name;
		this.m_phone = m_phone;
		this.m_reserve_num = m_reserve_num;
	}
	
	public DynamicHotels() {};
}


