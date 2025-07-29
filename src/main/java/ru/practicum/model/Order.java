package ru.practicum.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public String getMetroStation() { return metroStation; }
    public String getPhone() { return phone; }
    public int getRentTime() { return rentTime; }
    public String getDeliveryDate() { return deliveryDate; }
    public String getComment() { return comment; }
    public List<String> getColor() { return color; }

    public Order setFirstName(String firstName) { this.firstName = firstName; return this; }
    public Order setLastName(String lastName) { this.lastName = lastName; return this; }
    public Order setAddress(String address) { this.address = address; return this; }
    public Order setMetroStation(String metroStation) { this.metroStation = metroStation; return this; }
    public Order setPhone(String phone) { this.phone = phone; return this; }
    public Order setRentTime(int rentTime) { this.rentTime = rentTime; return this; }
    public Order setDeliveryDate(String deliveryDate) { this.deliveryDate = deliveryDate; return this; }
    public Order setComment(String comment) { this.comment = comment; return this; }
    public Order setColor(List<String> color) { this.color = color; return this; }
}
