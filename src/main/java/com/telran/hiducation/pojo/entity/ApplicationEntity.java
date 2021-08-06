package com.telran.hiducation.pojo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "applications")
public class ApplicationEntity extends BasicEntity {

    @Column(name = "app_name")
    private String appName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "field")
    private String field;

    @OneToMany(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<AppExerciseEntity> exercise;

    @OneToMany(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<AppExamEntity> exam;

}
