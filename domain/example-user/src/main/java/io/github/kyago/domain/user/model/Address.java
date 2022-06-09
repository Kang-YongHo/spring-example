package io.github.kyago.domain.user.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

    @Column(name = "county", nullable = false)
    private String county;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    protected Address() {
    }

    public Address(final String county, final String state, final String city, final String zipCode) {
        this.county = county;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getFullAddress() {
        return String.format("%s %s %s", this.state, this.city, this.zipCode);
    }

    public String getCounty() {
        return county;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "county='" + county + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}