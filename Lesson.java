package main.modal;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Lesson {
    private static int counter = 0;
    private int id;
    private String name;
    private double price;
    private LocalDate date;
    private final int MAX_CAPACITY = 5;
    private int capacity;
    private ArrayList<Customer> bookedCustomers;
    private ArrayList<Integer> ratings;

    public Lesson(String name, LocalDate date, double price) {
        this.id = ++counter;
        this.name = name;
        this.date = date;
        this.price = price;
        this.capacity = 0;
        this.bookedCustomers = new ArrayList<>();
        this.ratings = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }


    public int getCapacity() {
        return capacity;
    }

    public boolean isFull(){ return capacity>= MAX_CAPACITY; }

    public ArrayList<Customer> getBookedCustomers() {
        return bookedCustomers;
    }

    public ArrayList<Integer> getRatings() {
        return ratings;
    }

    public void addCustomer(Customer customer) {
        bookedCustomers.add(customer);
        capacity++;
    }

    public void removeCustomer(Customer customer) {
        bookedCustomers.remove(customer);
        capacity--;
    }

    public void addRating(int rating) {
        ratings.add(rating);
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0;
        }
        int sum = 0;
        for (int rating : ratings) {
            sum += rating;
        }
        return (double) sum / ratings.size();
    }

    public boolean isFullyBooked() {
        return capacity >= MAX_CAPACITY;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d/%d", name, date, capacity, MAX_CAPACITY);
    }

    public long getCancellationPeriod() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lessonDateTime = date.atTime(0, 0);
        Duration timeDifference = Duration.between(now, lessonDateTime);
        long hoursUntilLesson = timeDifference.toHours();
        long cancellationPeriodInHours = 24; // example cancellation period of 24 hours
        long hoursUntilCancellationPeriodExpires = cancellationPeriodInHours - hoursUntilLesson;
        return hoursUntilCancellationPeriodExpires;
    }

}
