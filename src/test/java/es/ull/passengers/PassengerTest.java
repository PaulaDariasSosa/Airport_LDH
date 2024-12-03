package test.java.es.ull.passengers;

import main.java.es.ull.passengers.Passenger;
import main.java.es.ull.flights.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassengerTest {

    private Passenger passenger;

    @BeforeEach
    void setUp() {
        passenger = new Passenger("12345", "John Doe", "US");
    }

    @Test
    void testPassengerCreationValid() {
        assertEquals("12345", passenger.getIdentifier());
        assertEquals("John Doe", passenger.getName());
        assertEquals("US", passenger.getCountryCode());
        assertNull(passenger.getFlight());
    }

    @Test
    void testPassengerCreationInvalidCountryCode() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Passenger("67890", "Jane Smith", "XX"));
        assertEquals("Invalid country code", exception.getMessage());
    }

    @Test
    void testSetFlight() {
        Flight flight = new Flight("AA1234", 2);
        passenger.setFlight(flight);
        assertEquals(flight, passenger.getFlight());
    }

    @Test
    void testRemoveFlight() {
        Flight flight = new Flight("AA1234", 2);
        passenger.setFlight(flight);
        assertNotNull(passenger.getFlight());
        passenger.setFlight(null);
        assertNull(passenger.getFlight());
    }

    @Test
    void testJoinFlight() {
        Flight flight = new Flight("AA1234", 2);
        passenger.joinFlight(flight);
        assertEquals(flight, passenger.getFlight());
        assertTrue(flight.getNumberOfPassengers() > 0);
    }

    @Test
    void testJoinFlightRemovesFromPreviousFlight() {
        Flight flight1 = new Flight("AA1234", 2);
        Flight flight2 = new Flight("BB5678", 2);

        passenger.joinFlight(flight1);
        assertEquals(flight1, passenger.getFlight());
        assertEquals(1, flight1.getNumberOfPassengers());

        passenger.joinFlight(flight2);
        assertEquals(flight2, passenger.getFlight());
        assertEquals(0, flight1.getNumberOfPassengers());
        assertEquals(1, flight2.getNumberOfPassengers());
    }

    @Test
    void testJoinFlightCannotRemoveFromPreviousFlight() {
        Flight flight = new Flight("AA1234", 2);
        Flight flight2 = new Flight("BB5678", 2);
        passenger.setFlight(flight);


        assertThrows(RuntimeException.class, () -> {
            //flight.removePassenger(passenger);
            // Forzamos error previo
            passenger.joinFlight(flight2);
        });
    }

    @Test
    void testToString() {
        String expected = "Passenger John Doe with identifier: 12345 from US";
        assertEquals(expected, passenger.toString());
    }
}

