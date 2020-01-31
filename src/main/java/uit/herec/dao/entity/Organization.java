package uit.herec.dao.entity;
// default package
// Generated Jan 31, 2020 4:45:21 PM by Hibernate Tools 4.3.5.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Organization generated by hbm2java
 */
@Entity
@Table(name = "organization", catalog = "herec_uit")
public class Organization implements java.io.Serializable {

    private Integer id;
    private String name;
    private String hyperledgerName;
    private Set<AppUser> appUsers = new HashSet<AppUser>(0);
    private Set<Appointment> appointments = new HashSet<Appointment>(0);
    private Set<Diagnosis> diagnosises = new HashSet<Diagnosis>(0);

    public Organization() {
    }

    public Organization(String hyperledgerName) {
        this.hyperledgerName = hyperledgerName;
    }

    public Organization(String name, String hyperledgerName, Set<AppUser> appUsers, Set<Appointment> appointments,
            Set<Diagnosis> diagnosises) {
        this.name = name;
        this.hyperledgerName = hyperledgerName;
        this.appUsers = appUsers;
        this.appointments = appointments;
        this.diagnosises = diagnosises;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", length = 65535)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "hyperledger_name", nullable = false, length = 65535)
    public String getHyperledgerName() {
        return this.hyperledgerName;
    }

    public void setHyperledgerName(String hyperledgerName) {
        this.hyperledgerName = hyperledgerName;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "permission", catalog = "herec_uit", joinColumns = {
            @JoinColumn(name = "id_org", nullable = false, updatable = false) }, inverseJoinColumns = {
                    @JoinColumn(name = "id_user", nullable = false, updatable = false) })
    public Set<AppUser> getAppUsers() {
        return this.appUsers;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    public Set<Appointment> getAppointments() {
        return this.appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    public Set<Diagnosis> getDiagnosises() {
        return this.diagnosises;
    }

    public void setDiagnosises(Set<Diagnosis> diagnosises) {
        this.diagnosises = diagnosises;
    }

}
