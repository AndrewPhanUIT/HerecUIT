package uit.herec.service.org.quan12.form;

import java.io.Serializable;

public class Quan12AppointmentForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String patientPhoneNumber;
	private Quan12AppointmentFormDetail data;

	public Quan12AppointmentForm(String code, String patientPhoneNumber, Quan12AppointmentFormDetail data) {
		super();
		this.code = code;
		this.patientPhoneNumber = patientPhoneNumber;
		this.data = data;
	}

	public Quan12AppointmentForm() {
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

	public Quan12AppointmentFormDetail getData() {
		return data;
	}

	public void setData(Quan12AppointmentFormDetail data) {
		this.data = data;
	}

}
