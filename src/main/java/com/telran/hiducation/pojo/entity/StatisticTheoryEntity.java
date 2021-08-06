package com.telran.hiducation.pojo.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "statistic_theory")
public class StatisticTheoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "user_name")
    String userName;

    @Column(name = "app_id")
    long appId;

    double theory;
}
