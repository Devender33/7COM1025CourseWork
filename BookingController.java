package main.controllers;

import main.modal.Booking;
import main.modal.Customer;
import main.modal.Lesson;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BookingController {
    private static BookingController instance;
    private List<Lesson> lessons;
    private Map<Booking, LocalDate> bookings;

    private BookingController() {
        bookings = new HashMap<>();
    }

    public static BookingController getInstance() {
        if (instance == null) {
            instance = new BookingController();
        }
        return instance;
    }

    public void addLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public String bookLesson(Customer customer, Lesson lesson, LocalDate date) {
        if (!lessons.contains(lesson)) {
            return "Lesson not found";
        }

        if (lesson.isFullyBooked()) {
            return "No more seats available!";
        }

        if (lesson.getBookedCustomers().contains(customer)) {
            return "Already booked!";
        }

        if (customer.getCustomerWallet() < lesson.getPrice()) {
            return "Insufficient balance";
        }

        Booking booking = new Booking(customer, lesson, date);
        lesson.addCustomer(customer);
        bookings.put(booking, date);
        customer.updateCustomerWallet(-lesson.getPrice());

        return "Booking successful!";
    }

    public List<Booking> getBookingsByCustomer(Customer customer) {
        List<Booking> customerBookings = new ArrayList<>();
        for (Booking booking : bookings.keySet()) {
            if (booking.getBookingCustomer().equals(customer)) {
                customerBookings.add(booking);
            }
        }
        return customerBookings;
    }

    public String cancelBooking(Booking booking, Customer customer) {
        if (!bookings.containsKey(booking)) {
            return "Booking not found";
        }

        Lesson lesson = booking.getBookingLesson();

        if (booking.getBookingStatus() == Booking.ATTENDED_STATUS) {
            return "Cannot cancel, you attended the lesson.";
        }

        if (booking.getBookingStatus() == Booking.CANCELLED_STATUS) {
            return "Already cancelled.";
        }

        LocalDate cancellationDate = bookings.get(booking);
        LocalDate now = LocalDate.now();

        long cancellationPeriod = ChronoUnit.DAYS.between(now, cancellationDate);

        if (cancellationPeriod > lesson.getCancellationPeriod()) {
            return "Cannot be cancelled, cancellation period is over.";
        }

        lesson.removeCustomer(customer);
        customer.updateCustomerWallet(lesson.getPrice());
        bookings.replace(booking, now);
        booking.setBookingStatus(Booking.CANCELLED_STATUS);

        return "Booking cancelled!";
    }

    public String updateBooking(Booking booking, Lesson lesson) {
        if (!bookings.containsKey(booking)) {
            return "Booking not found";
        }

        Customer customer = booking.getBookingCustomer();

        if (!lessons.contains(lesson)) {
            return "Lesson not found";
        }

        if (lesson.isFullyBooked()) {
            return "No more seats available!";
        }

        if (lesson.getBookedCustomers().contains(customer)) {
            return "Already booked!";
        }

        if (customer.getCustomerWallet() < lesson.getPrice()) {
            return "Insufficient balance";
        }

        Lesson oldLesson = booking.getBookingLesson();
        oldLesson.removeCustomer(customer);
        customer.updateCustomerWallet(oldLesson.getPrice());

        booking.updateBookingLesson(lesson);
        lesson.addCustomer(customer);
        customer.updateCustomerWallet(-lesson.getPrice());

        return "Booking updated!";
    }

    public Set<Booking> getBookings() {
        return bookings.keySet();
    }
}
