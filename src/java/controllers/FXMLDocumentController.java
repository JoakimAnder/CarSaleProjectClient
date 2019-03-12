package controllers;

import Entities.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class FXMLDocumentController implements Initializable {

    private Bidder selectedBidder;
    private Auction selectedAuction;

    private ItemController controller;

    @FXML
    private TextField bidderName;
    @FXML
    private ListView<Bidder> bidderList;
    @FXML
    private TextField bidderPhone;
    @FXML
    private TextField bidderAddress;
    @FXML
    private TableView<Bid> bidderBids;
    @FXML
    private CheckBox auctionIsSold;
    @FXML
    private TextField auctionId;
    @FXML
    private ListView<Auction> auctionList;
    @FXML
    private Label currentBid;
    @FXML
    private TextField bidderBid;
    @FXML
    private TableView<Bid> auctionBids;
    @FXML
    private Label itemModel;
    @FXML
    private TableColumn<Bid, String> auctionBidTime;
    @FXML
    private TableColumn<Bid, String> auctionBidBid;
    @FXML
    private TableColumn<Bid, String> bidderBidHighest;
    @FXML
    private TableColumn<Bid, String> bidderBidAuction;
    @FXML
    private TableColumn<Bid, String> bidderBidTime;
    @FXML
    private Label itemManufacturer;
    @FXML
    private Label itemManifactureYear;
    @FXML
    private Label itemCondition;
    @FXML
    private Label auctionTimeLeft;
    @FXML
    private Button loginButton, logoutButton, createBidderButton, submitBidderButton, cancelBidderButton, submitBidButton;
    @FXML
    private Tab bidderBidsTab;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = new ItemController();

        bidderList.getSelectionModel().selectedItemProperty().addListener(e -> {
            Bidder b = bidderList.getSelectionModel().getSelectedItem();
            System.out.println(b);
            selectedBidder = (b == null) ? selectedBidder : b;
            updateBidder();
        });
        auctionList.getSelectionModel().selectedItemProperty().addListener(e -> {
            Auction a = auctionList.getSelectionModel().getSelectedItem();
            selectedAuction = (a == null) ? selectedAuction : a;
            updateAuction();
        });

        auctionBidTime.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getTimeOfBid().toString()));
        auctionBidBid.setCellValueFactory(e -> new ReadOnlyStringWrapper(String.valueOf(e.getValue().getAmount())));
        
        bidderBidAuction.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getAuction().getId().toString()));
        bidderBidHighest.setCellValueFactory(e -> new ReadOnlyStringWrapper((e.getValue().getAuction().getHighestBid() == e.getValue().getAmount() ? "True" : "False")));
        bidderBidTime.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().getTimeOfBid().toString()));

        logout(new ActionEvent());
    }

    @FXML
    public void submitBid(ActionEvent event) {
        if (selectedAuction == null || selectedBidder == null || !isFilledAndTrim(bidderBid)) {
            return;
        }

        try {
            int bid = Integer.parseInt(bidderBid.getText());
            if (!controller.bid(selectedAuction, selectedBidder, bid)) {
                bidderBid.setText("Nope");
            }
        } catch (NumberFormatException e) {
            bidderBid.setText("NaN");
        }
        selectedAuction = controller.getAuction(selectedAuction.getId());
        auctionList.getItems().set(auctionList.getSelectionModel().getSelectedIndex(), selectedAuction);
        currentBid.setText(selectedAuction.getHighestBid() + "");
//        updateAuction();
    }

    @FXML
    private void update(ActionEvent event) {
        searchBidder(event);
        updateBidder();

        updateAuctions(event);
        updateAuction();
    }

    @FXML
    private void updateAuctions(ActionEvent event) {
        if (isFilledAndTrim(auctionId)) {
            searchAuctionByID(event);
        }

        if (auctionIsSold.isSelected()) {
            auctionList.getItems().setAll(controller.getSoldAuctions());
        } else {
            auctionList.getItems().setAll(controller.getAuctions());
        }

        auctionList.getSelectionModel().clearSelection();
        if (auctionList.getItems().contains(selectedAuction)) {
            auctionList.getSelectionModel().select(selectedAuction);
        }
    }

    void updateAuction() {
        if (selectedAuction == null) {
            auctionBids.getItems().clear();
            bidderBid.setDisable(true);
            submitBidButton.setDisable(true);

            for (Label label : Arrays.asList(currentBid, itemManifactureYear, itemManufacturer, itemModel, itemCondition, auctionTimeLeft)) {
                label.setText("");
            }
        } else {
            bidderBid.setDisable(false);
            submitBidButton.setDisable(false);
            currentBid.setText(selectedAuction.getHighestBid() + "");

            auctionTimeLeft.setText(selectedAuction.getTimeOfEnd().until(LocalDateTime.now(), ChronoUnit.DAYS) * (selectedAuction.getTimeOfEnd().isAfter(LocalDateTime.now()) ? 1 : -1) + "");

            auctionBids.getItems().setAll(selectedAuction.getBids());
            
            Car item = selectedAuction.getItem();
            if (item == null) {
                for (Label label : Arrays.asList(currentBid, itemManifactureYear, itemManufacturer, itemModel, itemCondition)) {
                    label.setText("");
                }
            } else {
                itemManifactureYear.setText(item.getManufactureYear() + "");
                itemManufacturer.setText(item.getManufacturer() + "");
                itemModel.setText(item.getModel());
                itemCondition.setText(item.getCurrentCondition().toString());
            }
        }
    }

    void updateBidder() {
        if (selectedBidder == null) {
            bidderPhone.setText("");
            bidderAddress.setText("");
            bidderBids.getItems().clear();
        } else {
            bidderName.setText(selectedBidder.getName());
            bidderPhone.setText(selectedBidder.getPhone());
            bidderAddress.setText(selectedBidder.getAddress());
            bidderBids.getItems().setAll(selectedBidder.getBids());
        }
    }

    @FXML
    private void searchBidder(ActionEvent event) {
        bidderList.getItems().setAll(controller.getBidder(bidderName.getText()));
    }

    @FXML
    private void createBidder(ActionEvent event) {
        if (!isFilledAndTrim(bidderName)) {
            return;
        }

        bidderPhone.setText("");
        bidderAddress.setText("");

        bidderPhone.setEditable(true);
        bidderAddress.setEditable(true);
        cancelBidderButton.setVisible(true);
        submitBidderButton.setVisible(true);
    }

    @FXML
    private void submitBidder(ActionEvent event) {
        boolean fieldsCheckout = true;
        for (TextField tf : Arrays.asList(bidderName, bidderPhone, bidderAddress)) {
            fieldsCheckout = isFilledAndTrim(tf);
        }

        if (!fieldsCheckout) {
            return;
        }

        bidderPhone.setEditable(false);
        bidderAddress.setEditable(false);
        cancelBidderButton.setVisible(false);
        submitBidderButton.setVisible(false);

        controller.addBidder(new Bidder(bidderName.getText(), bidderPhone.getText(), bidderAddress.getText()));
        searchBidder(event);
    }

    @FXML
    public void cancelBidderCreation(ActionEvent event) {
        bidderPhone.setText("");
        bidderAddress.setText("");

        bidderPhone.setEditable(false);
        bidderAddress.setEditable(false);
        cancelBidderButton.setVisible(false);
        submitBidderButton.setVisible(false);
    }

    @FXML
    private void login(ActionEvent event) {
        if (selectedBidder == null) {
            return;
        }

        bidderName.setEditable(false);
        bidderAddress.setEditable(false);
        bidderPhone.setEditable(false);

        bidderBidsTab.setDisable(false);
        logoutButton.setDisable(false);

        bidderBid.setVisible(true);
        submitBidButton.setVisible(true);

        for (Control control : Arrays.asList(bidderList, loginButton, createBidderButton)) {
            control.setDisable(true);
        }

    }

    @FXML
    private void logout(ActionEvent event) {
        selectedBidder = null;
        bidderName.setEditable(true);
        bidderAddress.setEditable(false);
        bidderPhone.setEditable(false);

        bidderBidsTab.setDisable(true);
        logoutButton.setDisable(true);

        bidderBid.setVisible(false);
        submitBidButton.setVisible(false);

        for (Control control : Arrays.asList(bidderList, loginButton, createBidderButton)) {
            control.setDisable(false);
        }
    }

    @FXML
    private void searchAuctionByID(ActionEvent event) {
        if (!isFilledAndTrim(auctionId)) {
            return;
        }

        try {
            Long id = Long.parseLong(auctionId.getText());
            auctionList.getItems().setAll(controller.getAuction(id));
        } catch (NumberFormatException e) {
            auctionList.getItems().clear();
        }
    }

    private boolean isFilledAndTrim(TextField tf) {
        String s = tf.getText();
        if (s == null) {
            return false;
        }

        s = s.trim();
        if (s.isEmpty()) {
            return false;
        }

        tf.setText(s);
        return true;
    }
}
