package uit.herec.service.org.quan12.form;

import java.io.Serializable;

public class Quan12AppointmentFormDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private String clinician;
	private String createdAt;
	private String appointmentDate;

	public Quan12AppointmentFormDetail(String clinician, String createdAt, String appointmentDate) {
		super();
		this.clinician = clinician;
		this.createdAt = createdAt;
		this.appointmentDate = appointmentDate;
	}

	public String getClinician() {
		return clinician;
	}

	public void setClinician(String clinician) {
		this.clinician = clinician;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

}
