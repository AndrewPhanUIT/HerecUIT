package uit.herec.service.org.tanphu.form;

import java.io.Serializable;

public class TanPhuAppointmentFormDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private TanPhuClinicianForm clinician;
	private String createdAt;
	private String appointmentDate;

	public TanPhuAppointmentFormDetail(TanPhuClinicianForm clinician, String createdAt, String appointmentDate) {
		super();
		this.clinician = clinician;
		this.createdAt = createdAt;
		this.appointmentDate = appointmentDate;
	}

	public TanPhuClinicianForm getClinician() {
		return clinician;
	}

	public void setClinician(TanPhuClinicianForm clinician) {
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
