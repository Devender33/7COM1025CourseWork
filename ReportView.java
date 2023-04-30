package main.views;

import main.controllers.LessonController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportView extends JFrame implements ActionListener {

    private JLabel titleLabel;
    private JComboBox<String> monthComboBox;
    private JButton championButton;
    private JButton lessonReportButton;

    public ReportView() {
        super("Weekend Fitness Club Report");

        // Set up UI components
        this.titleLabel = new JLabel("Weekend Fitness Club Report");
        this.titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        this.titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set up the month drop-down menu
        String[] months = LessonController.getInstance().getMonths();
        this.monthComboBox = new JComboBox<>(months);
        this.monthComboBox.setFont(new Font("Helvetica", Font.PLAIN, 18));
        this.monthComboBox.setPreferredSize(new Dimension(200, 40));
        this.monthComboBox.setMaximumRowCount(5);

        // Set up the two buttons
        this.championButton = new JButton("Champion Fitness Report");
        this.championButton.addActionListener(this);
        this.championButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        this.championButton.setPreferredSize(new Dimension(300, 60));
        this.championButton.setBackground(Color.GREEN);
        this.championButton.setForeground(Color.WHITE);

        this.lessonReportButton = new JButton("Lesson Report");
        this.lessonReportButton.addActionListener(this);
        this.lessonReportButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        this.lessonReportButton.setPreferredSize(new Dimension(300, 60));
        this.lessonReportButton.setBackground(Color.BLUE);
        this.lessonReportButton.setForeground(Color.WHITE);

        // Add components to the frame
        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setLayout(new BorderLayout());
        contentPane.add(this.titleLabel, BorderLayout.NORTH);
        JPanel monthPanel = new JPanel();
        monthPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        monthPanel.add(new JLabel("Select Month:"));
        monthPanel.add(this.monthComboBox);
        contentPane.add(monthPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 0, 20));
        buttonPanel.add(this.championButton);
        buttonPanel.add(this.lessonReportButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        this.setContentPane(contentPane);

        // Set frame properties
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.championButton) {
            ChampionReportView championReportView = new ChampionReportView((String)monthComboBox.getSelectedItem());
            championReportView.setVisible(true);

        } else if (e.getSource() == this.lessonReportButton) {
            LessonReportView lessonReportView = new LessonReportView((String)monthComboBox.getSelectedItem());
            lessonReportView.setVisible(true);
        }
    }
}
