package Entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Car implements Serializable {
    
    private Long id;
    private String model;
    private String manufacturer;
    private int newPrice;
    private Integer manufactureYear;
    private Condition currentCondition;
    private Auction auction;
    
    public Car() {}

    public Car(String model, int newPrice, String manufacturer, Integer manufactureYear, Condition condition) {
        this.model = model;
        this.newPrice = newPrice;
        this.manufacturer = manufacturer;
        this.manufactureYear = manufactureYear;
        this.currentCondition = condition;
    }

    public double value() {
        double value = newPrice * 0.9;
        value *= Math.pow(0.8, LocalDate.now().getYear() - manufactureYear);
        
        switch (currentCondition) {
            case MINT:
                break;
            case USED:
                value -= 10000;
                break;
            case SCRAP:
                value -= 30000;
        }
        
        return (value <= 0) ? 0 : value;
    }

    
    
    
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Integer newPrice) {
        this.newPrice = newPrice;
    }

    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public Condition getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(Condition currentCondition) {
        this.currentCondition = currentCondition;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    
    // Misc

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Car other = (Car) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + id + ", manufacturer=" + manufacturer + ", model=" + model + ", newPrice=" + newPrice + ", manufactureYear=" + manufactureYear + ", condition=" + currentCondition + '}';
    }
    
    
}
