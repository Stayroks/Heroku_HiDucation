package com.telran.hiducation.pojo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "app_exam")
@Entity
public class AppExamEntity extends BasicEntity {

    @Column(name = "order_exam")
    int order;

    @Column(name = "max_exercise_grade")
    int maxExerciseGrade;

    @Column(name = "max_time_cap")
    LocalTime maxTimeCap;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "app_id", referencedColumnName = "id", nullable = false)
    ApplicationEntity application;

}
