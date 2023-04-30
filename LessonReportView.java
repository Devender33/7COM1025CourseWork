package main.views;
import main.controllers.ReportController;
import main.controllers.ReviewController;
import main.modal.Booking;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class LessonReportView extends JFrame {
    private final ReportController reportController = ReportController.getInstance();
    private final ReviewController reviewController = ReviewController.getInstance();
    private final String month;

    public LessonReportView(String month) {
        this.month = month;
        setTitle("Lesson Report");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setContentPane(new JScrollPane(createTable()));
        setVisible(true);
    }

    private JTable createTable() {
        List<Booking> bookings = reportController.getBookingsByMonth(month);
        Map<String, Integer> saturdayAttendees = reportController.getLessonAttendanceByDayOfWeek(bookings, DayOfWeek.SATURDAY);
        Map<String, Integer> sundayAttendees = reportController.getLessonAttendanceByDayOfWeek(bookings, DayOfWeek.SUNDAY);
        Map<String, Double> averageRatings = reviewController.getAverageRatingsByLesson(month);

        String[] columnNames = {"S.no.", "Lesson Name", "Sunday Attendees", "Saturday Attendees", "Avg. Rating"};
        Object[][] rowData = IntStream.range(0, bookings.size())
                .mapToObj(i -> {
                    Booking booking = bookings.get(i);
                    String lessonName = booking.getBookingLesson().getName();
                    int sundayAttendance = sundayAttendees.getOrDefault(lessonName, 0);
                    int saturdayAttendance = saturdayAttendees.getOrDefault(lessonName, 0);
                    double averageRating = averageRatings.getOrDefault(lessonName, 0.0);
                    return new Object[]{i+1, lessonName, sundayAttendance, saturdayAttendance, averageRating};
                })
                .toArray(Object[][]::new);

        return new JTable(new DefaultTableModel(rowData, columnNames));
    }
}
