package citi;

import yahoofinance.YahooFinance;
import yahoofinance.Stock;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class App extends Application {

    // Queue to store stock records
    static class StockRecord {
        BigDecimal price;
        String timestamp;
        int tick;

        StockRecord(BigDecimal price, String timestamp, int tick) {
            this.price = price;
            this.timestamp = timestamp;
            this.tick = tick;
        }
    }

    private final Queue<StockRecord> stockQueue = new LinkedList<>();
    private XYChart.Series<Number, Number> series;
    private final AtomicInteger tickCounter = new AtomicInteger(0);
    private final DateTimeFormatter formatter = 
        DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void start(Stage stage) {

        // Set up X and Y axes
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time Tick (every 5 seconds)");
        yAxis.setLabel("DJIA Stock Price (USD)");
        xAxis.setForceZeroInRange(false);
        yAxis.setForceZeroInRange(false);

        // Create line chart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Dow Jones Industrial Average — Live Monitor");
        lineChart.setAnimated(false);

        // Create data series
        series = new XYChart.Series<>();
        series.setName("DJIA Price");
        lineChart.getData().add(series);

        // Set up the scene
        Scene scene = new Scene(lineChart, 900, 550);
        stage.setTitle("Citi — Live Stock Dashboard");
        stage.setScene(scene);
        stage.show();

        // Start background thread to query stock every 5 seconds
        ScheduledExecutorService executor = 
            Executors.newSingleThreadScheduledExecutor();

        executor.scheduleAtFixedRate(() -> {
            try {
                Stock stock = YahooFinance.get("^DJI");

                if (stock != null && stock.getQuote() != null) {
                    BigDecimal price = stock.getQuote().getPrice();
                    String timestamp = LocalDateTime.now().format(formatter);
                    int tick = tickCounter.incrementAndGet();

                    StockRecord record = new StockRecord(price, timestamp, tick);
                    stockQueue.add(record);

                    System.out.println("Tick " + tick + " | " + 
                        timestamp + " | DJIA: $" + price);

                    // Update chart on JavaFX thread
                    Platform.runLater(() -> {
                        series.getData().add(
                            new XYChart.Data<>(tick, price)
                        );
                    });

                } else {
                    System.out.println("No data returned. Retrying...");
                }

            } catch (IOException e) {
                System.out.println("Network error: " + e.getMessage());

                // Even on error, show a gap in the chart
                int tick = tickCounter.incrementAndGet();
                Platform.runLater(() -> {
                    System.out.println("Skipping tick " + tick + " due to error.");
                });
            }
        }, 0, 5, TimeUnit.SECONDS);

        // Stop executor when window is closed
        stage.setOnCloseRequest(event -> {
            executor.shutdown();
            Platform.exit();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
