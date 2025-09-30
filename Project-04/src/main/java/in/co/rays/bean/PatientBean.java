package in.co.rays.bean;

import java.util.Date;

public class PatientBean extends BaseBean {

	private String name;

	private String mobileNo;

	private Date date_of_visit;

	private String disease;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Date getdate_of_visit() {
		return date_of_visit;
	}

	public void setdate_of_visit(Date date_of_visit) {
		this.date_of_visit = date_of_visit;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	@Override
	public String getKey() {
		return disease;
	}

	@Override
	public String getValue() {

		return disease;
	}

}
