package dao;

import Entities.*;
import Wrappers.AuctionWrapper;
import Wrappers.BidWrapper;
import Wrappers.BidderWrapper;
import Wrappers.CarWrapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;

public class ItemDao {

    private final Client client = ClientBuilder.newClient();
    private final WebTarget target = client.target("http://localhost:8080/CarSalesProject2/rest/");

    private final List<Car> unwrappedCars = new ArrayList<>();
    private final List<Bid> unwrappedBids = new ArrayList<>();
    private final List<Bidder> unwrappedBidders = new ArrayList<>();
    private final List<Auction> unwrappedAuctions = new ArrayList<>();

    private void clearUnwrapped() {
        unwrappedCars.clear();
        unwrappedBids.clear();
        unwrappedBidders.clear();
        unwrappedAuctions.clear();
    }

    private Car unwrap(CarWrapper cw) {
        for (Car uc : unwrappedCars) {
            if (uc.getId().equals(cw.getId())) {
                return uc;
            }
        }

        Car c = new Car();
        c.setId(cw.getId());
        c.setCurrentCondition(cw.getCondition());
        c.setManufactureYear(cw.getManufactureYear());
        c.setManufacturer(cw.getManufacturer());
        c.setModel(cw.getModel());
        c.setNewPrice(cw.getNewPrice());
        unwrappedCars.add(c);

        if(cw.getAuction() != null)
            c.setAuction(unwrap(target.path("auction/id/" + cw.getAuction()).request().get(AuctionWrapper.class)));

        return c;
    }

    private Bidder unwrap(BidderWrapper bw) {
        for (Bidder ub : unwrappedBidders) {
            if (ub.getId().equals(bw.getId())) {
                return ub;
            }
        }

        Bidder b = new Bidder();
        b.setId(bw.getId());
        b.setAddress(bw.getAddress());
        b.setName(bw.getName());
        b.setPhone(bw.getPhone());
        
        b.setBids(bw.getBids().stream()
                .map(bidW -> unwrap(bidW))
                .collect(Collectors.toList()));

        unwrappedBidders.add(b);
        return b;
    }

    private Bid unwrap(BidWrapper bw) {
        for (Bid ub : unwrappedBids) {
            if (ub.getId().equals(bw.getId())) {
                return ub;
            }
        }

        Bid bid = new Bid();
        bid.setAmount(bw.getAmount());
        bid.setId(bw.getId());

        if (!bw.getTimeOfBid().isEmpty()) {
            bid.setTimeOfBid(LocalDateTime.parse(bw.getTimeOfBid()));
        }

        unwrappedBids.add(bid);

        bid.setAuction(unwrap(target.path("auction/id/" + bw.getAuction()).request().get(AuctionWrapper.class)));
        bid.setBidder(unwrap(target.path("bidder/" + bw.getBidder()).request().get(BidderWrapper.class)));

        return bid;
    }

    private Auction unwrap(AuctionWrapper aw) {
        for (Auction ua : unwrappedAuctions) {
            if (ua.getId().equals(aw.getId())) {
                return ua;
            }
        }

        Auction a = new Auction();
        a.setId(aw.getId());
        a.setReservationPrice(aw.getReservationPrice());
        if (!aw.getTimeOfEnd().isEmpty()) {
            a.setTimeOfEnd(LocalDateTime.parse(aw.getTimeOfEnd()));
        }
        a.setValuedPrice(aw.getValuedPrice());
        a.setBids(aw.getBids().stream()
                .map(b -> unwrap(b))
                .collect(Collectors.toList()));
        a.setItem(unwrap(aw.getItem()));

        unwrappedAuctions.add(a);
        return a;
    }

    public boolean createCar(Car car) {
        return target.path("car")
                .request()
                .post(Entity.json(car))
                .getStatus() == 200;
    }

    public boolean createAuction(Auction auction) {
        return target.path("auction")
                .request()
                .post(Entity.json(auction))
                .getStatus() == 200;
    }

    public boolean createBidder(Bidder bidder) {
        return target.path("bidder")
                .request()
                .post(Entity.json(bidder))
                .getStatus() == 200;
    }

    public Auction getAuction(Long id) {
        Auction a = unwrap(target.path("auction/id/" + id).request().get(AuctionWrapper.class));
        
        clearUnwrapped();
        return a;
    }

    public Bidder getBidder(Long id) {
        Bidder b = unwrap(target.path("bidder/id/" + id).request().get(BidderWrapper.class));
        clearUnwrapped();
        return b;
    }

    public List<Car> getCars() {
        List<Car> list = Arrays.asList(target.path("car")
                .request()
                .get(CarWrapper[].class)).stream()
                .map(w -> unwrap(w))
                .collect(Collectors.toList());
        clearUnwrapped();
        return list;
    }

    public List<Bidder> getBidders() {
        List<Bidder> list = Arrays.asList(target.path("bidder").request().get(BidderWrapper[].class)).stream()
                .map(w -> unwrap(w))
                .collect(Collectors.toList());
        clearUnwrapped();
        return list;
    }

    public List<Auction> getAllAuctions() {
        List<Auction> list = Arrays.asList(
                target.path("auction/all")
                .request()
                .get(AuctionWrapper[].class)).stream()
                .map(w -> unwrap(w))
                .collect(Collectors.toList());
        clearUnwrapped();
        return list;
    }

    public List<Auction> getAllAvaliableAuctions() {
        List<Auction> list = Arrays.asList(target.path("auction").request().get(AuctionWrapper[].class)).stream()
                .map(w -> unwrap(w))
                .collect(Collectors.toList());
        clearUnwrapped();
        return list;
    }

    public List<Auction> getSoldAuctions() {
        List<Auction> list = Arrays.asList(target.path("auction/sold").request().get(AuctionWrapper[].class)).stream()
                .map(w -> unwrap(w))
                .collect(Collectors.toList());
        clearUnwrapped();
        return list;
    }

    public List<Auction> getAuctionsByEndTime(LocalDateTime min, LocalDateTime max) {
        List<Auction> list = Arrays.asList(target.path("auction/end/"+min+"/"+max)
                .request()
                .get(AuctionWrapper[].class)).stream()
                .map(w -> unwrap(w))
                .collect(Collectors.toList());
        clearUnwrapped();
        return list; 
    }

    public List<Auction> getAuctionsByHighestBid(int min, int max) {
        List<Auction> list = Arrays.asList(target.path("auction/" + min + "/" + max).request().get(AuctionWrapper[].class)).stream()
                .map(w -> unwrap(w))
                .collect(Collectors.toList());
        clearUnwrapped();
        return list; 
    }

    public List<Auction> getSoldAuctionsByHighestBid(int min, int max) {
        List<Auction> list = Arrays.asList(target.path("auction/sold/" + min + "/" + max).request().get(AuctionWrapper[].class)).stream()
                .map(w -> unwrap(w))
                .collect(Collectors.toList());
        clearUnwrapped();
        return list; 
    }

    public boolean bid(Bidder bidder, Auction auction, int bid) {
        return target.path("auction/bid/" + auction.getId() + "/" + bid)
                .request()
                .put(Entity.json(bidder), Boolean.class);
    }

    public List<Bidder> getBiddersByName(String name) {
        List<Bidder> list = Arrays.asList(target.path("bidder/" + name).request().get(BidderWrapper[].class)).stream()
                .map(w -> unwrap(w))
                .collect(Collectors.toList());
        clearUnwrapped();
        return list; 
    }

    public List<Car> getCarsWithAuctions() {
        List<Car> list = Arrays.asList(target.path("car/claimed").request().get(CarWrapper[].class)).stream()
                .map(w -> unwrap(w))
                .collect(Collectors.toList());
        clearUnwrapped();
        return list; 
    }

    public List<Car> getCarsWithoutAuctions() {
        List<Car> list = Arrays.asList(target.path("car/unclaimed").request().get(CarWrapper[].class)).stream()
                .map(w -> unwrap(w))
                .collect(Collectors.toList());
        clearUnwrapped();
        return list; 
    }

    public List<Car> getCarsByManufactureYear(int min, int max) {
        List<Car> list = Arrays.asList(target.path("car/year/" + min + "/" + max).request().get(CarWrapper[].class)).stream()
                .map(w -> unwrap(w))
                .collect(Collectors.toList());
        clearUnwrapped();
        return list; 
    }

    public List<Car> getCarsByManufacturer(String manufacturer) {
        List<Car> list = Arrays.asList(target.path("car/manufacturer/" + manufacturer).request().get(CarWrapper[].class)).stream()
                .map(w -> unwrap(w))
                .collect(Collectors.toList());
        clearUnwrapped();
        return list; 
    }

    public List<Car> getCarsByModel(String model) {
        List<Car> list = Arrays.asList(target.path("car/" + model).request().get(CarWrapper[].class)).stream()
                .map(w -> unwrap(w))
                .collect(Collectors.toList());
        clearUnwrapped();
        return list; 
    }

    public List<Car> getCarsByCondition(Condition condition) {
        List<Car> list = Arrays.asList(target.path("car/condition/" + condition).request().get(CarWrapper[].class)).stream()
                .map(w -> unwrap(w))
                .collect(Collectors.toList());
        clearUnwrapped();
        return list;
    }

}
