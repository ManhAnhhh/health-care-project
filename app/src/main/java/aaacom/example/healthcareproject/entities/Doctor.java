package aaacom.example.healthcareproject.entities;

public class Doctor {
    private int Id;
    private String name;
    private String specialization;
    private String contact;
    private int experience;
    private String hospitalAddress;
    private double fee;

    public Doctor(int Id, String name, String specialization, String contact, int experience, String hospitalAddress, double fee) {
        this.Id = Id;
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
        this.experience = experience;
        this.hospitalAddress = hospitalAddress;
        this.fee = fee;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
