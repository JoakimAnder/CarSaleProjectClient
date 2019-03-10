package Wrappers;

import Entities.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BidWrapper implements Serializable {
    
    private int amount;
    private String timeOfBid;
    private Long id;
    private Long auction;
    private Long bidder;

    public BidWrapper() {
    }
    
    public static BidWrapper wrap(Bid b) {
        return (b == null ? null : new BidWrapper(b));
    }
    public static List<BidWrapper> wrap(List<Bid> bList) {
        return (bList == null) ? 
                null : 
                bList.stream().map(e -> new BidWrapper(e)).collect(Collectors.toList());
    }

    private BidWrapper(Bid bid) {
        amount = bid.getAmount();
        timeOfBid = bid.getTimeOfBid().toString();
        id = bid.getId();
        Auction a = bid.getAuction();
        Bidder b = bid.getBidder();
        
        auction = (a == null)? null : a.getId();
        bidder = (b == null)? null : b.getId();
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

    public String getTimeOfBid() {
        return timeOfBid;
    }

    public void setTimeOfBid(String timeOfBid) {
        this.timeOfBid = timeOfBid;
    }

    public Long getAuction() {
        return auction;
    }

    public void setAuction(Long auction) {
        this.auction = auction;
    }

    public Long getBidder() {
        return bidder;
    }

    public void setBidder(Long bidder) {
        this.bidder = bidder;
    }
}
