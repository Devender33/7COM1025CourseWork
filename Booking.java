package main.modal;

import java.time.LocalDate;

public class Booking {
    public static final String BOOKED_STATUS = "Booked";
    public static final String ATTENDED_STATUS = "Attended" ;
    public static final String CANCELLED_STATUS = "Cancelled";

    public static final String CHANGED_STATUS = "Changed";

    private static int bookingCounter = 0;

    private int bookingId;
    private Customer bookingCustomer;
    private Lesson bookingLesson;
    private LocalDate bookingDate;
    private String bookingStatus;

    public Booking(Customer customer, Lesson lesson, LocalDate date) {
        this.bookingId = ++bookingCounter;
        this.bookingCustomer = customer;
        this.bookingLesson = lesson;
        this.bookingDate = date;
        this.bookingStatus = BOOKED_STATUS;
    }

    public Customer getBookingCustomer() {
        return bookingCustomer;
    }

    public Lesson getBookingLesson() {
        return bookingLesson;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void updateBookingLesson(Lesson lesson) {
        this.bookingLesson = lesson;
    }

    public int getBookingId() {
        return this.bookingId;
    }

    public String toString() {
        return this.bookingLesson.getName() + " " + this.bookingDate + " " + this.getBookingStatus();
    }

    public void setBookingStatus(String status) {
        this.bookingStatus = status;
    }

    public String getBookingStatus() {
        return this.bookingStatus;

    }
}
