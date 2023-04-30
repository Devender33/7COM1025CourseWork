package main.views;

import main.controllers.BookingController;
import main.modal.Booking;
import main.modal.Customer;
import main.modal.Lesson;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ManageBookingsView extends JFrame {
    private final List<Booking> bookings;
    private JPanel contentPane;
    private JLabel lblTitle;
    private JList<Booking> listBookings;
    private JButton btnCancelBooking,btnAttendBooking,btnChangeBooking;
    private Customer customer;

    public ManageBookingsView(Customer customer) {
        this.customer = customer;
        this.bookings = BookingController.getInstance().getBookingsByCustomer(customer);
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Manage Bookings");
        setBounds(100, 100, 900, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 10));

        // Title
        lblTitle = new JLabel("Your Bookings");
        lblTitle.setFont(new Font("Sans Serif", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblTitle, BorderLayout.NORTH);

        // List of Bookings
        listBookings = new JList<>(bookings.toArray(new Booking[bookings.size()]));
        listBookings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listBookings);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Cancel Booking Button
        btnCancelBooking = new JButton("Cancel Booking");
        btnCancelBooking.setFont(new Font("Sans Serif", Font.BOLD, 16));
        btnCancelBooking.setPreferredSize(new Dimension(200, 40));
        btnCancelBooking.setEnabled(false);

        // Attend Booking Button
        btnAttendBooking = new JButton("Attend Booking");
        btnAttendBooking.setFont(new Font("Sans Serif", Font.BOLD, 16));
        btnAttendBooking.setPreferredSize(new Dimension(200, 40));
        btnAttendBooking.setEnabled(false);

        // Change Booking Button
        btnChangeBooking = new JButton("Change Booking");
        btnChangeBooking.setFont(new Font("Sans Serif", Font.BOLD, 16));
        btnChangeBooking.setPreferredSize(new Dimension(200, 40));
        btnChangeBooking.setEnabled(false);


        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.add(btnCancelBooking);
        buttonsPanel.add(btnAttendBooking);
        buttonsPanel.add(btnChangeBooking);
        contentPane.add(buttonsPanel, BorderLayout.SOUTH);

        // add listener for the list of bookings
        listBookings.addListSelectionListener(e -> {
            if (!listBookings.isSelectionEmpty()) {
                btnCancelBooking.setEnabled(true);
                btnAttendBooking.setEnabled(true);
                btnChangeBooking.setEnabled(true);
            } else {
                btnCancelBooking.setEnabled(false);
                btnAttendBooking.setEnabled(false);
                btnChangeBooking.setEnabled(false);
            }
        });

        // add listener for the list of bookings
        listBookings.addListSelectionListener(e -> {
            if (!listBookings.isSelectionEmpty()) {
                btnCancelBooking.setEnabled(true);
            } else {
                btnCancelBooking.setEnabled(false);
            }
        });

        btnChangeBooking.addActionListener(e->
        {
            Booking booking = listBookings.getSelectedValue();
            if(booking.getBookingStatus() == Booking.ATTENDED_STATUS)
            { JOptionPane.showMessageDialog(this, "Booking is attended, cannot change", "INFO", JOptionPane.INFORMATION_MESSAGE);}
            else if (booking.getBookingStatus() == Booking.CANCELLED_STATUS) {
                JOptionPane.showMessageDialog(this, "Booking is cancelled, cannot change", "INFO", JOptionPane.INFORMATION_MESSAGE);
            } else
            {
                booking.getBookingLesson().removeCustomer(customer);
                customer.updateCustomerWallet( booking.getBookingLesson().getPrice());
                booking.setBookingStatus(Booking.CHANGED_STATUS);
                listBookings.setListData(bookings.toArray(new Booking[bookings.size()]));
                TimetableView timetableView  = new TimetableView(customer,null);
                timetableView.setVisible(true);

            }
        });
        btnAttendBooking.addActionListener(e->{
            Booking booking = listBookings.getSelectedValue();
            if(booking.getBookingStatus() == Booking.ATTENDED_STATUS)
            { JOptionPane.showMessageDialog(this, "Booking is attended", "INFO", JOptionPane.INFORMATION_MESSAGE);}
            else if (booking.getBookingStatus() == Booking.CANCELLED_STATUS) {
                JOptionPane.showMessageDialog(this, "Booking is cancelled, cannot attend", "INFO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Lesson l = booking.getBookingLesson();
                ReviewView reviewView = new ReviewView(this.customer, l);
                booking.setBookingStatus(Booking.ATTENDED_STATUS);
            }
        });
        // add listener for the cancel booking button
        btnCancelBooking.addActionListener(e -> {
            Booking selectedBooking = listBookings.getSelectedValue();
            if (selectedBooking != null) {
                // cancel the booking
                Lesson lesson = selectedBooking.getBookingLesson();
                if (selectedBooking.getBookingStatus() == Booking.ATTENDED_STATUS) {
                    JOptionPane.showMessageDialog(contentPane, "Cannot cancel, you attended the lesson.");
                } else if (selectedBooking.getBookingStatus() == Booking.CANCELLED_STATUS) {
                    JOptionPane.showMessageDialog(contentPane, "Booking is already cancelled.");
                } else {
                    LocalDate cancellationDate = lesson.getDate().minusDays(1);
                    if (LocalDate.now().compareTo(cancellationDate) > 0) {
                        JOptionPane.showMessageDialog(contentPane, "Cannot be cancelled, cancellation period is over.");
                    } else {
                        int confirmCancel = JOptionPane.showConfirmDialog(contentPane, "Are you sure you want to cancel this booking?", "Cancel Booking", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (confirmCancel == JOptionPane.YES_OPTION) {
                            lesson.removeCustomer(customer);
                            customer.updateCustomerWallet(lesson.getPrice());
                            selectedBooking.setBookingStatus(Booking.CANCELLED_STATUS);
                            listBookings.setListData(bookings.toArray(new Booking[bookings.size()]));
                            JOptionPane.showMessageDialog(contentPane, "Booking cancelled!");

                            // disable the cancel button
                            btnCancelBooking.setEnabled(false);
                        }
                    }
                }
            }
        });

    }
}
