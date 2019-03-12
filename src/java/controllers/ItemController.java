package controllers;

import Entities.Auction;
import Entities.Bidder;
import dao.ItemDao;
import java.util.List;

public class ItemController {
    
    ItemDao dao = new ItemDao();

    public boolean bid(Auction auction, Bidder bidder, int bid) {
        return dao.bid(bidder, auction, bid);
    }

    public List<Auction> getSoldAuctions() {
        return dao.getSoldAuctions();
    }

    public List<Auction> getAuctions() {
        return dao.getAllAuctions();
    }

    public List<Bidder> getBidder(String name) {
        return dao.getBiddersByName(name);
    }

    public void addBidder(Bidder bidder) {
        dao.createBidder(bidder);
    }

    public Auction getAuction(Long id) {
        return dao.getAuction(id);
    }
    
}
