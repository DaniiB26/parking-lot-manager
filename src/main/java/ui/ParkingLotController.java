package ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.ParkingTicket;
import pricing.HourlyPricingStrategy;
import repository.InMemoryParkingTicketRepository;
import service.ParkingLotService;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ParkingLotController {

    @FXML
    private TextField licensePlateField;

    @FXML
    private TextField ticketIdField;

    @FXML
    private TextField exitTimeField;

    @FXML
    private ListView<String> ticketsListView;

    @FXML
    private Label resultLabel;

    private final ParkingLotService service = new ParkingLotService(
            new InMemoryParkingTicketRepository(),
            new HourlyPricingStrategy(10.0)
    );

    @FXML
    public void initialize() {
        refreshTickets();
        resultLabel.setText("Ready");
    }

    @FXML
    private void onEnterParking() {
        try {
            ParkingTicket ticket = service.enterParking(licensePlateField.getText());
            resultLabel.setText("Created ticket: " + ticket.getId());
            ticketIdField.setText(ticket.getId().toString());
            refreshTickets();
        } catch (Exception ex) {
            resultLabel.setText(ex.getMessage());
        }
    }

    @FXML
    private void onCalculatePrice() {
        try {
            Integer ticketId = parseTicketId();
            LocalDateTime exitTime = parseExitTime();
            Double price = service.calculatePrice(ticketId, exitTime);
            resultLabel.setText("Price: " + price);
        } catch (Exception ex) {
            resultLabel.setText(ex.getMessage());
        }
    }

    @FXML
    private void onPayTicket() {
        try {
            Integer ticketId = parseTicketId();
            ParkingTicket ticket = service.payTicket(ticketId);
            resultLabel.setText("Ticket state: " + ticket.getStateName());
            refreshTickets();
        } catch (Exception ex) {
            resultLabel.setText(ex.getMessage());
        }
    }

    @FXML
    private void onCloseTicket() {
        try {
            Integer ticketId = parseTicketId();
            ParkingTicket ticket = service.closeTicket(ticketId);
            resultLabel.setText("Ticket state: " + ticket.getStateName());
            refreshTickets();
        } catch (Exception ex) {
            resultLabel.setText(ex.getMessage());
        }
    }

    @FXML
    private void onRefreshTickets() {
        refreshTickets();
        resultLabel.setText("Ticket list refreshed");
    }

    private Integer parseTicketId() {
        if (ticketIdField.getText() == null || ticketIdField.getText().isBlank()) {
            throw new IllegalArgumentException("Ticket id is required.");
        }

        return Integer.parseInt(ticketIdField.getText().trim());
    }

    private LocalDateTime parseExitTime() {
        if (exitTimeField.getText() == null || exitTimeField.getText().isBlank()) {
            return LocalDateTime.now();
        }

        try {
            return LocalDateTime.parse(exitTimeField.getText().trim());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Exit time format: yyyy-MM-ddTHH:mm");
        }
    }

    private void refreshTickets() {
        List<String> items = service.getAllTickets()
                .stream()
                .map(this::formatTicket)
                .toList();

        ticketsListView.setItems(FXCollections.observableArrayList(items));
    }

    private String formatTicket(ParkingTicket ticket) {
        return ticket.getId() + " | " +
                ticket.getVehicle().getLicensePlate() + " | " +
                ticket.getStateName() + " | " +
                ticket.getEntryTime();
    }
}
