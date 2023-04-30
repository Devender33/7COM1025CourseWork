package main.controllers;

import main.modal.Lesson;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.*;

public class LessonController {
    private static LessonController instance = null;
    private List<Lesson> lessons;

    private LessonController() {
        lessons = new ArrayList<>();
    }

    public static LessonController getInstance() {
        if (instance == null) {
            instance = new LessonController();
        }
        return instance;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public List<Lesson> getLessons() {
        return Collections.unmodifiableList(lessons);
    }

    public Lesson getLessonById(int lessonId) {
        for (Lesson lesson : lessons) {
            if (lesson.getId() == lessonId) {
                return lesson;
            }
        }
        return null;
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }

    public List<Lesson> getLessonsByDay(DayOfWeek day) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getDate().getDayOfWeek() == day) {
                result.add(lesson);
            }
        }
        return Collections.unmodifiableList(result);
    }

    public List<Lesson> getLessonsByActivity(String activity) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getName().equals(activity)) {
                result.add(lesson);
            }
        }
        return Collections.unmodifiableList(result);
    }

    public String[] getMonths() {
        Set<Month> monthSet = new HashSet<>();
        for (Lesson lesson : lessons) {
            monthSet.add(lesson.getDate().getMonth());
        }

        List<Month> monthList = new ArrayList<>(monthSet);
        Collections.sort(monthList);

        String[] result = new String[monthList.size()];
        for (int i = 0; i < monthList.size(); i++) {
            result[i] = monthList.get(i).name();
        }
        return result;
    }
}
