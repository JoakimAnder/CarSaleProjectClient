package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bidder implements Serializable {
    
    private Long id;
    private String name;
    private String phone;
    private String address;
    private List<Bid> bids = new ArrayList<>();
    
    public Bidder(){}

    public Bidder(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
    
    
    
    
    public void addBid(Bid bid) {
        if (bids == null)
            bids = new ArrayList<>();
        bids.add(bid);
        bid.setBidder(this);
    }
    
    public void removeBid(Bid bid) {
        if (bids == null)
            return;
        bids.remove(bid);
        bid.setBidder(null);
    }
    
    
    
    
    
    
    
    
    
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Bid> getBids() {
        if (bids == null)
            bids = new ArrayList<>();
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    // Misc

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final Bidder other = (Bidder) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bidder{" + "id=" + id + ", name=" + name + ", phone=" + phone + ", address=" + address + '}';
    }
    
    
    
    
    
    
}
