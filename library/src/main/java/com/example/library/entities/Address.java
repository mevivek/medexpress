package com.example.library.entities;

import androidx.annotation.Keep;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

@Keep
public class Address implements Serializable {

    private String addressLine1;
    private String addressLine2;
    private String area;
    private String city;
    private String state;
    private String pinCode;
    private String country;
    private double latitude;
    private double longitude;

    public Address() {
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Exclude
    public String getStringSingleLine() {
        StringBuilder stringBuilder = new StringBuilder();
        if (addressLine1 != null)
            stringBuilder.append(addressLine1).append(", ");
        if (addressLine2 != null)
            stringBuilder.append(addressLine2).append(", ");
        if (area != null)
            stringBuilder.append(area).append(", ");
        if (city != null)
            stringBuilder.append(city).append(", ");
        if (state != null)
            stringBuilder.append(state).append(", ");
        if (pinCode != null)
            stringBuilder.append(pinCode);
        return stringBuilder.toString();
    }

    @Exclude
    public String getStringMultiline() {
        StringBuilder stringBuilder = new StringBuilder();
        if (addressLine1 != null)
            stringBuilder.append(addressLine1).append("\n");
        if (addressLine2 != null)
            stringBuilder.append(addressLine2).append("\n");
        if (area != null)
            stringBuilder.append(area).append("\n");
        if (city != null)
            stringBuilder.append(city).append(", ");
        if (state != null)
            stringBuilder.append(state).append(", ");
        if (pinCode != null)
            stringBuilder.append(pinCode);
        return stringBuilder.toString();
    }

    @Exclude
    public void setAddress(android.location.Address address) {
        addressLine2 = address.getFeatureName();
        area = address.getSubLocality();
        city = address.getSubAdminArea();
        state = address.getAdminArea();
        pinCode = address.getPostalCode();
        latitude = address.getLatitude();
        longitude = address.getLongitude();
        country = address.getCountryName();
    }

    @Exclude
    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }
}
