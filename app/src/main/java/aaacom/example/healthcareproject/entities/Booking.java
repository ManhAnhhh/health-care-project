package aaacom.example.healthcareproject.entities;

public class Booking {
    int BookingId;
    int DoctorId;
    int UserId;
    String Date;
    String Time;

    public Booking(){}

    public Booking(int bookingId, int doctorId, int userId, String date, String time) {
        BookingId = bookingId;
        DoctorId = doctorId;
        UserId = userId;
        Date = date;
        Time = time;
    }

    public int getBookingId() {
        return BookingId;
    }

    public void setBookingId(int bookingId) {
        BookingId = bookingId;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
