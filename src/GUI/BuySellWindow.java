package GUI;

import Data.CurrentDate;
import Databases.DataBase;
import Databases.PortfolioDataBase;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BuySellWindow extends Application {
    private String ticker, type;
    private String currentDate;

    public BuySellWindow() {
    }

    public BuySellWindow(String type, String ticker) {
        this.type = type;
        this.ticker = ticker;
        start(new Stage());
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setMinSize(300, 300);
        grid.setHgap(5);
        grid.setVgap(5);

        Label label = new Label(type + " " + ticker + " Stocks");
        Label qty = new Label("Enter Quantity: ");
        Label mkt = new Label("Order Type: Market Order");
        TextField getQuantity = new TextField("100");
        Button buy = new Button(type);


        grid.add(label, 0, 0);
        grid.add(qty, 0, 1);
        grid.add(mkt, 0, 2);
        grid.add(getQuantity, 0, 3);
        grid.add(buy, 0, 4);
        buy.setOnAction(e1 -> {
            TextField confirmation = new TextField("Order Placed Successfully");
            confirmation.setEditable(false);
            Scene scene = new Scene(confirmation, 200, 100);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            scene.getStylesheets().add("GUI/darktheme.css");
            primaryStage.show();
            try {
                Thread.sleep(500);
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            currentDate = CurrentDate.getDate();
            Portfolio.lastInstanceDate = currentDate;
            PortfolioDataBase.addTransactionDate(currentDate, ticker, type, Integer.parseInt(getQuantity.getText()));
        });

        Scene scene = new Scene(grid);
        primaryStage.setTitle(ticker + " Buy Window");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        scene.getStylesheets().add("GUI/darktheme.css");
        primaryStage.show();
    }
}
