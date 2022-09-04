package base;

public class MemberRVO {

	private String m_reserve_hotel;
	private String m_reserve_room  ;
	private String m_reserve_date ;
	private String m_id;
	private String m_name;
	private String m_phone;
	private int m_reserve_num;
	
	
	
	
	
	public MemberRVO(String m_reserve_date) {
		this.m_reserve_date = m_reserve_date;
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
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
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
	public int getM_reserve_num() {
		return m_reserve_num;
	}
	public void setM_reserve_num(int m_reserve_num) {
		this.m_reserve_num = m_reserve_num;
	}
	
	@Override
	public String toString() {
		return "MemberRVO [m_reserve_date=" + m_reserve_date + "]";
	}
	
	
	
	
}
