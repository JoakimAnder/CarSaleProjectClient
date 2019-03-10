package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Bid implements Serializable {
    
    private int amount;
    private LocalDateTime timeOfBid;
    private Long id;
    private Auction auction;
    private Bidder bidder;

    public Bid() {
    }

    public Bid(int amount) {
        this.amount = amount;
        timeOfBid = LocalDateTime.now();
    }

    
    
    // Getters and Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimeOfBid() {
        return timeOfBid;
    }

    public void setTimeOfBid(LocalDateTime timeOfBid) {
        this.timeOfBid = timeOfBid;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
    }
    
    // Misc

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Bid other = (Bid) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bid{" + "amount=" + amount + ", timeOfBid=" + timeOfBid + ", id=" + id + '}';
    }
    
    
}
