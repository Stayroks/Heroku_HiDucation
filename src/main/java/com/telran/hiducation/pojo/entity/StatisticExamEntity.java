package com.telran.hiducation.pojo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "statistic_exam")
public class StatisticExamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "user_name")
    String userName;

    @Column(name = "app_id")
    long appId;

    @Column(name = "order_exam")
    int order;

    @Column(name = "max_exercise_grade")
    int maxExerciseGrade;

    @Column(name = "student_exercise_grade")
    int studentExerciseGrade;

    @Column(name = "max_time_cap")
    LocalTime maxTimeCap;

    @Column(name = "student_time_elapsed")
    LocalTime studentTimeElapsed;

}
