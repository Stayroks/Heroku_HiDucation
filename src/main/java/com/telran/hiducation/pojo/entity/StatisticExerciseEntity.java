package com.telran.hiducation.pojo.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "statistic_exercise")
public class StatisticExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "user_name")
    String userName;

    @Column(name = "app_id")
    long appId;

    @Column(name = "level_num")
    int levelNum;

    @Column(name = "exercise_order")
    int order;

    double complete;

}
