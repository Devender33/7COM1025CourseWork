package main.views;

import main.controllers.ReviewController;
import main.modal.Customer;
import main.modal.Lesson;
import main.modal.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ReviewView extends JDialog {

    private JLabel nameLabel;
    private JTextArea reviewTextArea;
    private JSlider ratingSlider;
    private JButton cancelButton;
    private JButton submitButton;

    private Customer customer;
    private Lesson lesson;

    public ReviewView(Customer customer, Lesson lesson) {
        this.customer = customer;
        this.lesson = lesson;

        // Set up the name label
        nameLabel = new JLabel("Write a review for " + lesson.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Set up the review text area
        reviewTextArea = new JTextArea(5, 30);
        reviewTextArea.setLineWrap(true);
        reviewTextArea.setWrapStyleWord(true);
        JScrollPane reviewScrollPane = new JScrollPane(reviewTextArea);

        // Set up the rating slider
        ratingSlider = new JSlider(1, 5);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setSnapToTicks(true);

        // Set up the cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        // Set up the submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this::submitReview);

        // Set up the layout
        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.add(nameLabel, BorderLayout.NORTH);
        contentPane.add(reviewScrollPane, BorderLayout.CENTER);
        contentPane.add(ratingSlider, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(contentPane, BorderLayout.CENTER);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Set up the dialog
        this.setModal(true);
        this.setTitle("Write a review");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void submitReview(ActionEvent e) {
        // Get the rating from the slider
        int rating = ratingSlider.getValue();

        // Get the review text
        String reviewText = reviewTextArea.getText().trim();
        if (reviewText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please write a review.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create the Review object and add it to the lesson
        Review review = new Review(reviewText, rating, this.lesson);
        ReviewController.getInstance().addReview(review, this.customer);

        // Show a success message and close the dialog
        JOptionPane.showMessageDialog(this, "Thank you for your review!", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
