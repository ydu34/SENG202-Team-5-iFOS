package seng202.group5.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.joda.money.Money;
import org.joda.time.DateTime;
import seng202.group5.Finance;
import seng202.group5.Order;
import seng202.group5.exceptions.InsufficientCashException;

import java.io.IOException;
import java.util.ArrayList;

public class AdminController {
    @FXML
    private Button launchOrderScreenButton;

    @FXML
    private Button launchStockScreenButton;

    @FXML
    private Button launchInvoiceButton;

    @FXML
    private Button launchHistoryScreenButton;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Text saleSummaryText;

    private Finance finance = new Finance();


    public void changeScreen(ActionEvent event, String scenePath){
        Parent sampleScene = null;
        try {
            sampleScene = FXMLLoader.load(getClass().getResource(scenePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (sampleScene != null) {
            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.setScene(new Scene(sampleScene, 821, 628));
        }

    }

    public void launchOrderScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/order.fxml");
    }

    public void launchStockScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/stock.fxml");
    }

    public void launchInvoiceScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/invoice.fxml");
    }

    public void launchHistoryScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/history.fxml");
    }

    @FXML
    public void viewHistory() {
        DateTime eDate = DateTime.parse(endDate.getValue().toString());
        DateTime sDate = DateTime.parse(startDate.getValue().toString());
        if (!eDate.isBefore(sDate)) {
            ArrayList<Money> result = finance.totalCalculator(sDate, eDate);
            saleSummaryText.setText("Testresult\nTotal cost of orders: " + result.get(0) + "\nAverage daily cost: " + result.get(1));
        } else {
            saleSummaryText.setText("End date is before start date");
        }


    }

    public void setFinance(Finance newFinance) {
        finance = newFinance;
    }
}
