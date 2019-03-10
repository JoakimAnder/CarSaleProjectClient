package main;

import Entities.Condition;
import dao.ItemDao;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ItemDao dao = new ItemDao();
        
        dao.getAllAvaliableAuctions();
        dao.getAllAuctions();
        dao.getSoldAuctions();
        dao.getAuction(33L);
        dao.getAuctionsByEndTime(LocalDateTime.now().minusMonths(5), LocalDateTime.now().plusMonths(5));
        dao.getAuctionsByHighestBid(0, 90000);
        dao.getSoldAuctionsByHighestBid(0, 90000);
        
        dao.getBidder(42L);
        dao.getBidders();
        dao.getBiddersByName("testName");
//        
        dao.getCars();
        dao.getCarsByCondition(Condition.MINT);
        dao.getCarsByManufactureYear(0, 3000);
        dao.getCarsByManufacturer("Volvo");
        dao.getCarsByModel("C123");
        dao.getCarsWithAuctions();
        dao.getCarsWithoutAuctions();
        
        
        
        
        
        
        
        
        
        
        
        
    }
}
