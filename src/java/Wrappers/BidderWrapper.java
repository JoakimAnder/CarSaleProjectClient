package Wrappers;

import Entities.Bidder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BidderWrapper implements Serializable {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private List<BidWrapper> bids;

    public BidderWrapper() {
    }
    
    public static BidderWrapper wrap(Bidder b) {
        return (b == null ? null : new BidderWrapper(b));
    }
    public static List<BidderWrapper> wrap(List<Bidder> bList) {
        return (bList == null) ? 
                null : 
                bList.stream().map(e -> new BidderWrapper(e)).collect(Collectors.toList());
    }
    
    private BidderWrapper(Bidder b) {
        id = b.getId();
        name = b.getName();
        phone = b.getPhone();
        address = b.getAddress();
        if (b.getBids() != null)
            bids = BidWrapper.wrap(b.getBids());
    }

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

    public List<BidWrapper> getBids() {
        if (bids == null)
            bids = new ArrayList<>();
        return bids;
    }

    public void setBids(List<BidWrapper> bids) {
        this.bids = bids;
    }
 
    
    
}
