package test.java.es.ull.flights;

import main.java.es.ull.flights.Flight;
import main.java.es.ull.passengers.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlightTest {

    private Flight flight;

    @BeforeEach
    void setUp() {
        flight = new Flight("AA1234", 2); // Capacidad de 2 asientos.
    }

    @Test
    void testValidFlightCreation() {
        assertDoesNotThrow(() -> new Flight("BA567", 100));
        assertEquals("AA1234", flight.getFlightNumber());
        assertEquals(0, flight.getNumberOfPassengers());
    }

    @Test
    void testInvalidFlightNumberThrowsException() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Flight("INVALID123", 50));
        assertEquals("Invalid flight number", exception.getMessage());
    }

    @Test
    void testAddPassengerSuccess() {
        Passenger passenger = new Passenger("12345", "John Doe", "US");
        assertTrue(flight.addPassenger(passenger));
        assertEquals(1, flight.getNumberOfPassengers());
        assertEquals(flight, passenger.getFlight());
    }

    @Test
    void testAddPassengerExceedsSeats() {
        Passenger passenger1 = new Passenger("12345", "John Doe", "US");
        Passenger passenger2 = new Passenger("67890", "Jane Smith", "GB");
        Passenger passenger3 = new Passenger("11223", "Alice Brown", "FR");

        flight.addPassenger(passenger1);
        flight.addPassenger(passenger2);

        Exception exception = assertThrows(RuntimeException.class, () -> flight.addPassenger(passenger3));
        assertEquals("Not enough seats for flight AA1234", exception.getMessage());
        assertEquals(2, flight.getNumberOfPassengers());
    }

    @Test
    void testRemovePassengerSuccess() {
        Passenger passenger = new Passenger("12345", "John Doe", "US");
        flight.addPassenger(passenger);
        assertTrue(flight.removePassenger(passenger));
        assertEquals(0, flight.getNumberOfPassengers());
        assertNull(passenger.getFlight());
    }

    @Test
    void testRemovePassengerNotInFlight() {
        Passenger passenger = new Passenger("12345", "John Doe", "US");
        assertFalse(flight.removePassenger(passenger));
    }

    @Test
    void testPassengerSwitchFlights() {
        Passenger passenger = new Passenger("12345", "John Doe", "US");
        Flight anotherFlight = new Flight("BB5678", 2);

        flight.addPassenger(passenger);
        assertEquals(flight, passenger.getFlight());

        anotherFlight.addPassenger(passenger);
        assertEquals(anotherFlight, passenger.getFlight());
        assertEquals(1, flight.getNumberOfPassengers());
        assertEquals(1, anotherFlight.getNumberOfPassengers());
    }

    @Test
    void testPassengerListDoesNotContainDuplicates() {
        Passenger passenger = new Passenger("12345", "John Doe", "US");

        assertTrue(flight.addPassenger(passenger));
        assertFalse(flight.addPassenger(passenger)); // Duplicado
        assertEquals(1, flight.getNumberOfPassengers());
    }

    @Test
    void testFlightNumberValidation() {
        assertDoesNotThrow(() -> new Flight("XY789", 50)); // Número válido
        Exception exception = assertThrows(RuntimeException.class, () -> new Flight("123AB", 50));
        assertEquals("Invalid flight number", exception.getMessage());
    }

    @Test
    void testFlightToString() {
        Passenger passenger1 = new Passenger("12345", "John Doe", "US");
        Passenger passenger2 = new Passenger("67890", "Jane Smith", "GB");

        flight.addPassenger(passenger1);
        flight.addPassenger(passenger2);

        String expected = "Flight AA1234 has 2 passengers: John Doe, Jane Smith";
        String actual = "Flight " + flight.getFlightNumber() + " has " + flight.getNumberOfPassengers() +
                " passengers: " + String.join(", ",
                passenger1.getName(),
                passenger2.getName());
        assertEquals(expected, actual);
    }
}
