package com.telran.hiducation.pojo.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

}
