package main.views;

import main.controllers.ReportController;
import main.modal.Lesson;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ChampionReportView extends JFrame {

    private static final long serialVersionUID = 1L;
    private final ReportController reportController = ReportController.getInstance();

    public ChampionReportView(String month) {
        setTitle("Fitness Lesson Income Report");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JTable table = new JTable(new Object[][]{}, new String[]{"S.no.", "Lesson Name", "Net Income"});

        List<LessonIncome> lessonIncomes = new ArrayList<>();
        reportController.getBookingsByMonth(month).forEach(booking -> {
            Lesson lesson = booking.getBookingLesson();
            double price = lesson.getPrice();
            int attendees = lesson.getCapacity();
            double income = price * attendees;
            LessonIncome lessonIncome = new LessonIncome(lesson.getName(), income);
            lessonIncomes.add(lessonIncome);
        });

        lessonIncomes.sort(Comparator.comparingDouble(LessonIncome::getNetIncome).reversed());
        DecimalFormat df = new DecimalFormat("#.##");

        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);

// Add columns to the table model
        model.addColumn("Number");
        model.addColumn("Lesson Name");
        model.addColumn("Net Income");

// Add rows to the table model


        int i = 1;
        for (LessonIncome lessonIncome : lessonIncomes) {
            Object[] row = {i, lessonIncome.getLessonName(), df.format(lessonIncome.getNetIncome())};
            ((javax.swing.table.DefaultTableModel) table.getModel()).addRow(row);
            i++;
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        setVisible(true);
    }

    private static class LessonIncome {

        private final String lessonName;
        private final double netIncome;

        public LessonIncome(String lessonName, double netIncome) {
            this.lessonName = lessonName;
            this.netIncome = netIncome;
        }

        public String getLessonName() {
            return lessonName;
        }

        public double getNetIncome() {
            return netIncome;
        }
    }
}
