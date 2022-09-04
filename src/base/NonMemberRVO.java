package base;

public class NonMemberRVO {

	private String nm_reserve_hotel;
	private String nm_reserve_room  ;
	private String nm_reserve_date ;
	private String nm_name;
	private String nm_phone;
	private int nm_reserve_num;
	
	
	
	
	
	public NonMemberRVO(String nm_reserve_date) {
		this.nm_reserve_date = nm_reserve_date;
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
		return "NonMemberRVO [nm_reserve_date=" + nm_reserve_date + "]";
	}
	
	
	
	
	
}
