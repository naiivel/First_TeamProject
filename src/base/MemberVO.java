package base;

public class MemberVO {

	private String m_id; 
	private String m_pw;
	private String m_name;
	private String m_phone;
	private int m_reserve_num;
	
	
	public MemberVO() {};
	
	
	
	
	
	
	public MemberVO(int m_reserve_num) {
		this.m_reserve_num = m_reserve_num;
	}






	public MemberVO(String m_id, String m_pw) {
		this.m_id = m_id;
		this.m_pw = m_pw;
	}




	public MemberVO(String m_id, String m_pw, String m_name, String m_phone) {
		this.m_id = m_id;
		this.m_pw = m_pw;
		this.m_name = m_name;
		this.m_phone = m_phone;
	}
	
	
	
	
	public MemberVO(String m_id, String m_pw, String m_name, String m_phone, int m_reserve_num) {
		this.m_id = m_id;
		this.m_pw = m_pw;
		this.m_name = m_name;
		this.m_phone = m_phone;
		this.m_reserve_num = m_reserve_num;
	}




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
	public int getM_reserve_num() {
		return m_reserve_num;
	}
	public void setM_reserve_num(int m_reserve_num) {
		this.m_reserve_num = m_reserve_num;
	}




	@Override
	public String toString() {
		return "MemberVO [아이디=" + m_id + ", 비밀번호=" + m_pw + ", 이름=" + m_name + ", 전화번호=" + m_phone
				+ ", 예약번호=" + m_reserve_num + "]";
	}



	
	
	
}
