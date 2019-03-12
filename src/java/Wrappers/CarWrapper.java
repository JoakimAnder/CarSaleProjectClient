package Wrappers;

import Entities.Car;
import Entities.Condition;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class CarWrapper implements Serializable {
    
    private String manufacturer;
    private String model;
    private Integer newPrice;
    private Integer manufactureYear;
    private Condition condition;
    private Long auction;
    private Long id;

    public CarWrapper() {
    }

    public static CarWrapper wrap(Car c) {
        return (c == null ? null : new CarWrapper(c));
    }
    public static List<CarWrapper> wrap(List<Car> cList) {
        return (cList == null) ? 
                null : 
                cList.stream().map(e -> new CarWrapper(e)).collect(Collectors.toList());
    }
    
    private CarWrapper(Car car) {
        id = car.getId();
        this.manufacturer = car.getManufacturer();
        this.model = car.getModel();
        this.newPrice = car.getNewPrice();
        this.manufactureYear = car.getManufactureYear();
        this.condition = car.getCurrentCondition();
        if (car.getAuction() != null)
            this.auction = car.getAuction().getId();
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

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Long getAuction() {
        return auction;
    }

    public void setAuction(Long auction) {
        this.auction = auction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CarWrapper{" + "id=" + id +", manufacturer=" + manufacturer + ", model=" + model + ", newPrice=" + newPrice + ", manufactureYear=" + manufactureYear + ", condition=" + condition + ", auction=" + auction + '}';
    }
}
