package main;

import Entities.Condition;
import dao.ItemDao;
import java.time.LocalDateTime;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void testThings() {
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
        
        dao.getCars();
        dao.getCarsByCondition(Condition.MINT);
        dao.getCarsByManufactureYear(0, 3000);
        dao.getCarsByManufacturer("Volvo");
        dao.getCarsByModel("C123");
        dao.getCarsWithAuctions();
        dao.getCarsWithoutAuctions();
    }
}
