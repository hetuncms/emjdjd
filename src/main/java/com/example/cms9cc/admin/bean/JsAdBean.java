package com.example.cms9cc.admin.bean;

import jakarta.persistence.*;

@Entity
@Table(name = "js_ad")
public class JsAdBean {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String jsLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJsLink() {
        return jsLink;
    }

    public void setJsLink(String jsLink) {
        this.jsLink = jsLink;
    }
}
