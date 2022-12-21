package com.admolodtsov.spring.springboot.trip_diary.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @NotBlank(message ="'поле обязательное для заполнения'")
    @Size(min= 2, message ="поле должно содержать минимум 2 символа")
    @Column(name="country")
    private String country;
    @NotBlank(message ="'поле обязательное для заполнения'")
    @Size(min= 2, message ="поле должно содержать минимум 2 символа")
    @Size(max = 30, message ="постарайтесь более коротко наименовать место")
    @Column(name="place")
    private String place;
    @NotBlank(message ="'поле обязательное для заполнения'")
    @Column(name="trip_date")
    private String date;

    @Column(name="duration")
    @Min(value = 1, message = "значение должно быть не менее 1")
    @Max(value = 1000, message = "значение должно быть не более 1000")
    private int duration;
    @NotBlank(message ="'поле обязательное для заполнения'")
    @Size(min= 20, message ="слишком короткое описание")
    @Size(max = 5000, message ="слишком длинное описание, постарайтесь более кратко изложить вашу историю")
    @Column(name="story")
    private String story;

    @Column(name = "views")
    private long views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private String username;

    public String getUsername() {
        return user.getUsername();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Trip(){
    }

    public Trip(String country, String place, String date, int duration, String story) {
        this.country = country;
        this.place = place;
        this.date = date;
        this.duration = duration;
        this.story = story;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }
}
