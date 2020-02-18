package uit.herec.service.org.tanphu.form;

import java.io.Serializable;

public class TanPhuAppointmentForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String patientPhoneNumber;
	private TanPhuAppointmentFormDetail data;

	public TanPhuAppointmentForm(String code, String patientPhoneNumber, TanPhuAppointmentFormDetail data) {
		super();
		this.code = code;
		this.patientPhoneNumber = patientPhoneNumber;
		this.data = data;
	}

	public TanPhuAppointmentForm() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPatientPhoneNumber() {
		return patientPhoneNumber;
	}

	public void setPatientPhoneNumber(String patientPhoneNumber) {
		this.patientPhoneNumber = patientPhoneNumber;
	}

	public TanPhuAppointmentFormDetail getData() {
		return data;
	}

	public void setData(TanPhuAppointmentFormDetail data) {
		this.data = data;
	}

}
