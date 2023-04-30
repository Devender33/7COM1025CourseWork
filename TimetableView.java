package main.views;

import main.controllers.BookingController;
import main.controllers.LessonController;
import main.modal.Customer;
import main.modal.Lesson;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class TimetableView extends JFrame {
    private JComboBox<String> dayComboBox, activityComboBox;
    private JList<Lesson> lessonList;
    private JButton viewByDayButton, viewByActivityButton, selectButton, backButton;
    private JScrollPane lessonScrollPane;
    private LessonController lessonController;
    private Customer customer;

    public TimetableView(Customer customer, Lesson lesson) {
        setTitle("WFC Fitness Lesson Timetable");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        this.lessonController = LessonController.getInstance();
        this.customer = customer;

        // Create components
        JLabel titleLabel = new JLabel("WFC Fitness Lesson Timetable", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        dayComboBox = new JComboBox<>(new String[]{"SATURDAY", "SUNDAY"});
        activityComboBox = new JComboBox<>(new String[]{"Spin", "Yoga", "Bodysculpt", "Pilates", "Zumba", "Kickboxing", "Crossfit", "Barre"});
        lessonList = new JList<>();
        viewByDayButton = new JButton("View by Day");
        viewByActivityButton = new JButton("View by Activity");
        selectButton = new JButton("Select");
        backButton = new JButton("Back");

        // Add action listeners to buttons
        viewByDayButton.addActionListener(e -> showLessonsByDay((String) dayComboBox.getSelectedItem()));
        viewByActivityButton.addActionListener(e -> showLessonsByActivity((String) activityComboBox.getSelectedItem()));
        selectButton.addActionListener(e -> {
            Lesson selectedLesson = lessonList.getSelectedValue();
            // Create the popup window
            JDialog dialog = new JDialog();
            dialog.setTitle("Lesson Details");
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(null);

            // Create the components for the popup window
            JLabel nameLabel = new JLabel("Name : " + selectedLesson.getName());
            JLabel dayLabel = new JLabel("Day : " + selectedLesson.getDate().getDayOfWeek() );
            JLabel priceLabel = new JLabel("Price : " + selectedLesson.getPrice() + "$");
            JButton confirmButton = new JButton("Confirm Booking");
            JButton cancelButton = new JButton("Cancel");

            // Add the components to the popup window
            JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panel.add(nameLabel);
            panel.add(dayLabel);
            panel.add(priceLabel);
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(confirmButton);
            buttonPanel.add(cancelButton);
            panel.add(buttonPanel);
            dialog.add(panel);

            // Add action listeners to the buttons
            confirmButton.addActionListener(event -> {
                // Create the booking
                String message = BookingController.getInstance().bookLesson(this.customer, selectedLesson, LocalDate.now());
                JOptionPane.showMessageDialog(this, message, "Alert ! ", JOptionPane.INFORMATION_MESSAGE);
                // Close the popup window and return to the timetable view
                dialog.dispose();
                dispose();
            });
            cancelButton.addActionListener(event -> dialog.dispose());

            // Display the popup window
            dialog.setVisible(true);
        });
        backButton.addActionListener(e -> dispose());

        // Create a panel for the title and add it to the frame
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        // Create a panel for the combo boxes and add it to the frame
        JPanel comboBoxPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        comboBoxPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        comboBoxPanel.add(new JLabel("Select a Day:"));
        comboBoxPanel.add(dayComboBox);
        comboBoxPanel.add(new JLabel("Select an Activity:"));
        comboBoxPanel.add(activityComboBox);
        add(comboBoxPanel, BorderLayout.WEST);

        // Create a panel for the buttons and add it to the frame
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 10));
        buttonPanel.add(viewByDayButton);
        buttonPanel.add(viewByActivityButton);
        buttonPanel.add(selectButton);
        add(buttonPanel, BorderLayout.EAST);

        // Create a panel for the lesson list and add it to the frame
        lessonList.setModel(new DefaultListModel<>());
        lessonScrollPane = new JScrollPane(lessonList);
        add(lessonScrollPane, BorderLayout.CENTER);

        // Create a panel for the back button and add it to the frame
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backButtonPanel.setBorder(new EmptyBorder(0, 0, 10, 10));
        backButtonPanel.add(backButton);
        add(backButtonPanel, BorderLayout.SOUTH);
    }

    private void showLessonsByDay(String day) {
        // Get the lessons for the selected day
        List<Lesson> lessonsByDay = lessonController.getLessonsByDay(DayOfWeek.valueOf(day));
        // Update the lesson list with the lessons for the selected day
        DefaultListModel<Lesson> listModel = new DefaultListModel<>();
        for (Lesson lesson : lessonsByDay) {
            listModel.addElement(lesson);
        }
        lessonList.setModel(listModel);
    }
    private void showLessonsByActivity(String activity) {
        // Get the lessons for the selected activity
        List<Lesson> lessonsByActivity = lessonController.getLessonsByActivity(activity);
        // Update the lesson list with the lessons for the selected activity
        DefaultListModel<Lesson> listModel = new DefaultListModel<>();
        for (Lesson lesson : lessonsByActivity) {
            listModel.addElement(lesson);
        }
        lessonList.setModel(listModel);
    }
}
