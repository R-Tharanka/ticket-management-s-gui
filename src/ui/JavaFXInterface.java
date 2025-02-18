package ui;

import config.Configuration;
import core.TicketPool;
import logging.Logger;
import threads.Customer;
import threads.Vendor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXInterface extends Application {

    private TextField totalTicketsField;
    private TextField ticketReleaseRateField;
    private TextField customerRetrievalRateField;
    private TextField maxTicketCapacityField;
    private Label statusLabel;
    private ListView<String> logListView;
    private ListView<String> ticketPoolView;

    private TicketPool ticketPool;
    private Thread vendorThread;
    private Thread customerThread;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ticket Management System");

        // Load the CSS file
        String css = getClass().getResource("/resources/style.css").toExternalForm();

        // Create input fields section
        TitledPane inputPane = new TitledPane("Ticket Settings", createInputFields());
        inputPane.setCollapsible(false);

        // Create log and pool views section
        TitledPane logPane = new TitledPane("System Logs", logListView = new ListView<>());
        logPane.setCollapsible(false);

        TitledPane poolPane = new TitledPane("Ticket Pool", ticketPoolView = new ListView<>());
        poolPane.setCollapsible(false);

        // Buttons
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        startButton.setOnAction(event -> {
            try {
                startSystem();
            } catch (Exception e) {
                Logger.log("Error starting system: " + e.getMessage());
                updateStatus("Error: Check input values.");
            }
        });
        stopButton.setOnAction(event -> stopSystem());

        // Status Label
        statusLabel = new Label("System Status: Stopped");
        statusLabel.setStyle("-fx-font-weight: bold;");

        // Button container
        VBox buttonBox = new VBox(10, startButton, stopButton);
        buttonBox.setStyle("-fx-alignment: center;");

        // Main Layout
        VBox root = new VBox(15, inputPane, buttonBox, statusLabel, logPane, poolPane);
        root.setPadding(new javafx.geometry.Insets(20));
        root.setSpacing(10);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(css);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private GridPane createInputFields() {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        totalTicketsField = new TextField();
        ticketReleaseRateField = new TextField();
        customerRetrievalRateField = new TextField();
        maxTicketCapacityField = new TextField();

        grid.add(new Label("Total Tickets:"), 0, 0);
        grid.add(totalTicketsField, 1, 0);
        grid.add(new Label("Ticket Release Rate:"), 0, 1);
        grid.add(ticketReleaseRateField, 1, 1);
        grid.add(new Label("Customer Retrieval Rate:"), 0, 2);
        grid.add(customerRetrievalRateField, 1, 2);
        grid.add(new Label("Max Ticket Capacity:"), 0, 3);
        grid.add(maxTicketCapacityField, 1, 3);

        return grid;
    }

    private VBox createLogAndPoolViews() {
        statusLabel = new Label("System Status: Stopped");
        logListView = new ListView<>();
        logListView.setPlaceholder(new Label("Logs will appear here"));
        ticketPoolView = new ListView<>();
        ticketPoolView.setPlaceholder(new Label("Ticket pool will appear here"));

        VBox logBox = new VBox(5, new Label("System Logs:"), logListView);
        VBox poolBox = new VBox(5, new Label("Ticket Pool:"), ticketPoolView);

        return new VBox(10, statusLabel, logBox, poolBox);
    }

    private void startSystem() throws Exception {
        int totalTickets = validateInput(totalTicketsField.getText(), "Total Tickets");
        int ticketReleaseRate = validateInput(ticketReleaseRateField.getText(), "Ticket Release Rate");
        int customerRetrievalRate = validateInput(customerRetrievalRateField.getText(), "Customer Retrieval Rate");
        int maxTicketCapacity = validateInput(maxTicketCapacityField.getText(), "Max Ticket Capacity");

        Configuration config = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
        ticketPool = new TicketPool();

        vendorThread = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate()));
        customerThread = new Thread(new Customer(ticketPool));

        vendorThread.start();
        customerThread.start();

        updateStatus("System Running...");

        monitorTicketPool();
    }

    private void stopSystem() {
        if (vendorThread != null) vendorThread.interrupt();
        if (customerThread != null) customerThread.interrupt();

        updateStatus("System Stopped.");
    }

    private void monitorTicketPool() {
        new Thread(() -> {
            while (vendorThread != null && customerThread != null && !vendorThread.isInterrupted() && !customerThread.isInterrupted()) {
                Platform.runLater(() -> {
                    logListView.setItems(FXCollections.observableArrayList(Logger.getLogs()));
                    ticketPoolView.setItems(FXCollections.observableArrayList(ticketPool.getTickets()));
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();
    }

    private void updateStatus(String status) {
        Platform.runLater(() -> statusLabel.setText("System Status: " + status));
    }

    private int validateInput(String input, String fieldName) throws Exception {
        try {
            int value = Integer.parseInt(input);
            if (value <= 0) {
                throw new Exception(fieldName + " must be positive.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new Exception(fieldName + " must be a valid integer.");
        }
    }
}
