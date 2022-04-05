package GUI;

import Databases.DataBase;
import Databases.PortfolioDataBase;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Portfolio extends Application {
    public static String lastInstanceDate;
    private TableView tableView;
    private ObservableList<Stonks> data;

    public Portfolio() {
        start(new Stage());
    }

    @Override
    public void start(Stage primaryStage) {
        tableView = new TableView<>();
        data = FXCollections.observableArrayList();
        tableView.setItems(data);
        setColumns();
        getTransactionData();

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tableView);

        borderPane.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.S && e.isControlDown()) {
                DataBase.savePortfolio(LoginWindow.username);
            }
        });

        Scene scene = new Scene(borderPane, 1000, 500);
        primaryStage.setTitle("Portfolio");
        primaryStage.setScene(scene);
        scene.getStylesheets().add("GUI/darktheme.css");
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public void getTransactionData() {
        PortfolioDataBase.getUserTransaction(data);
    }

    public void setColumns() {
        TableColumn dateColumn = new TableColumn("Date");
        dateColumn.setMinWidth(1000 / 4);
        dateColumn.setCellValueFactory(new PropertyValueFactory<TradingWindow, String>("date"));

        TableColumn tickerColumn = new TableColumn("Tickers");
        tickerColumn.setMinWidth(1000 / 4);
        tickerColumn.setCellValueFactory(new PropertyValueFactory<TradingWindow, String>("ticker"));

        TableColumn typeColumn = new TableColumn("Type");
        typeColumn.setMinWidth(1000 / 4);
        typeColumn.setCellValueFactory(new PropertyValueFactory<TradingWindow, String>("type"));

        TableColumn qtyColumn = new TableColumn("Quantity");
        qtyColumn.setMinWidth(1000 / 4);
        qtyColumn.setCellValueFactory(new PropertyValueFactory<TradingWindow, Double>("qty"));

        tableView.getColumns().addAll(dateColumn, tickerColumn, typeColumn, qtyColumn);
    }

    public static class Stonks {
        private SimpleStringProperty date;
        private SimpleStringProperty ticker;
        private SimpleStringProperty type;
        private SimpleIntegerProperty qty;


        public Stonks(String date, String ticker, String type, int qty) {
            this.date = new SimpleStringProperty(date);
            this.ticker = new SimpleStringProperty(ticker);
            this.type = new SimpleStringProperty(type);
            this.qty = new SimpleIntegerProperty(qty);
        }

        public String getDate() {
            return date.get();
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public String getTicker() {
            return ticker.get();
        }

        public void setTicker(String ticker) {
            this.ticker.set(ticker);
        }

        public String getType() {
            return type.get();
        }

        public void setType(String type) {
            this.type.set(type);
        }

        public int getQty() {
            return qty.get();
        }

        public void setQty(int qty) {
            this.qty.set(qty);
        }


    }
}
