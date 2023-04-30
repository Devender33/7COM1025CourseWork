package main.controllers;

import main.modal.Customer;
import main.modal.Lesson;
import main.modal.Review;

import java.util.HashMap;
import java.util.Map;

public class ReviewController {
    private static ReviewController instance;
    private Map<Review, Customer> reviewCustomerMap;

    private ReviewController() {
        reviewCustomerMap = new HashMap<>();
    }

    public static ReviewController getInstance() {
        if (instance == null) {
            instance = new ReviewController();
        }
        return instance;
    }

    public void addReview(Review review, Customer customer) {
        reviewCustomerMap.put(review,customer);

    }

    public Map<String, Double> getAverageRatingsByLesson(String month) {
        HashMap<String, Double> ratingsMap = new HashMap<>();
        HashMap<String, Integer> countMap = new HashMap<>();

        // Iterate through all the reviews and filter by the given month
        for (Map.Entry<Review, Customer> entry : reviewCustomerMap.entrySet()) {
            Review review = entry.getKey();
            Lesson lesson = review.getLesson();
            if (lesson.getDate().getMonth().toString().equals(month)) {
                String lessonName = lesson.getName();
                int rating = review.getRating();
                if (!ratingsMap.containsKey(lessonName)) {
                    ratingsMap.put(lessonName, (double) rating);
                    countMap.put(lessonName, 1);
                } else {
                    double currentRating = ratingsMap.get(lessonName);
                    int currentCount = countMap.get(lessonName);
                    ratingsMap.put(lessonName, currentRating + rating);
                    countMap.put(lessonName, currentCount + 1);
                }
            }
        }

        // Compute the average rating for each lesson
        HashMap<String, Double> averageRatingsMap = new HashMap<>();
        for (String lessonName : ratingsMap.keySet()) {
            double ratingSum = ratingsMap.get(lessonName);
            int count = countMap.get(lessonName);
            double averageRating = ratingSum / count;
            averageRatingsMap.put(lessonName, averageRating);
        }

        return averageRatingsMap;
    }


}
