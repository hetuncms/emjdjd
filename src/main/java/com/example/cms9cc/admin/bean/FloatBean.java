package com.example.cms9cc.admin.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="float_ad")
@Setter
@Getter
public class FloatBean {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String url;
    private String picUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("status_time")
    private Date statusTime;
    private String tel;
    @Transient
    private boolean update;

}
