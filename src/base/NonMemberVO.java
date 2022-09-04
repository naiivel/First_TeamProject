package base;

public class NonMemberVO {

	private String nm_name;
	private String nm_phone;
	private int nm_reserve_num;
	
	
	
	
	
	
	public NonMemberVO() {
	}
	
	
	
	public NonMemberVO(int nm_reserve_num) {
		this.nm_reserve_num = nm_reserve_num;
	}



	public NonMemberVO(String nm_name, String nm_phone) {
		this.nm_name = nm_name;
		this.nm_phone = nm_phone;
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
	public int getNm_reserve_num() {
		return nm_reserve_num;
	}
	public void setNm_reserve_num(int nm_reserve_num) {
		this.nm_reserve_num = nm_reserve_num;
	}
	@Override
	public String toString() {
		return "NonMemberVO [이름=" + nm_name + ", 전화번호=" + nm_phone + ", 예약번호=" + nm_reserve_num
				+ "]";
	}
	
	
	
}
