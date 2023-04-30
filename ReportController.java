package main.controllers;

import main.modal.Booking;
import main.modal.Lesson;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class ReportController {
    private static ReportController instance = new ReportController();
    private Set<Booking> bookingSet;

    private ReportController() {
        bookingSet = BookingController.getInstance().getBookings();
    }

    public static ReportController getInstance() {
        return instance;
    }

    public List<Booking> getBookingsByMonth(String month) {
        Month selectedMonth = Month.valueOf(month.toUpperCase());
        List<Booking> bookingsByMonth = new ArrayList<>();
        for (Booking booking : bookingSet) {
            LocalDate bookingDate = booking.getBookingLesson().getDate();
            if (bookingDate.getMonth() == selectedMonth) {
                bookingsByMonth.add(booking);
            }
        }
        return bookingsByMonth;
    }

    public List<Booking> getAttendedBookings(List<Booking> bookings) {
        List<Booking> attendedBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getBookingStatus().equals("Attended")) {
                attendedBookings.add(booking);
            }
        }
        return attendedBookings;
    }

    public Map<String, Integer> getLessonAttendanceByDayOfWeek(List<Booking> bookings, DayOfWeek dayOfWeek) {
        Map<String, Integer> attendanceMap = new HashMap<>();
        for (Booking booking : bookings) {
            LocalDate lessonDate = booking.getBookingLesson().getDate();
            if (lessonDate.getDayOfWeek() == dayOfWeek && booking.getBookingStatus().equals("Attended")) {
                Lesson lesson = booking.getBookingLesson();
                String lessonName = lesson.getName();
                int attendanceCount = attendanceMap.getOrDefault(lessonName, 0);
                attendanceCount += lesson.getBookedCustomers().size();
                attendanceMap.put(lessonName, attendanceCount);
            }
        }
        return attendanceMap;
    }
}
