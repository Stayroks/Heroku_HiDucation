package com.telran.hiducation.pojo.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "app_exercises")
@Entity
public class AppExerciseEntity extends BasicEntity {

    @Column(name = "level_num")
    int levelNum;

    @Column(name = "exercises_order")
    int exercisesOrder;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "app_id", referencedColumnName = "id", nullable = false)
    ApplicationEntity application;

}
