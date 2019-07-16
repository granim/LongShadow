package com.timezone.www.model;

import lombok.Builder;

import javax.persistence.*;
import java.util.TimeZone;

@Entity
public class Client extends BaseEntity {

    public Client(){}

    @Builder
    public Client(Long id, String companyName, String address, String city, String telephone, TimeZone timeZone) {
        super(id);
        this.companyName = companyName;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.timeZone = timeZone;
    }

    @Column(name = "company_name")
    private String companyName;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "timeZone")
    private TimeZone timeZone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_EMAIL")
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}