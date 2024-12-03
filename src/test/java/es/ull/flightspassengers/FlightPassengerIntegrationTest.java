package test.java.es.ull.flightspassengers;

import main.java.es.ull.flights.Flight;
import main.java.es.ull.passengers.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlightPassengerIntegrationTest {

    private Flight flight;
    private Passenger passenger;

    @BeforeEach
    void setUp() {
        flight = new Flight("AA1234", 1);
        passenger = new Passenger("12345", "John Doe", "US");
    }

    @Test
    void testPassengerAddedToFlight() {
        passenger.joinFlight(flight);
        assertEquals(flight, passenger.getFlight());
        assertEquals(1, flight.getNumberOfPassengers());
    }

    @Test
    void testPassengerRemovedFromFlight() {
        passenger.joinFlight(flight);
        assertEquals(flight, passenger.getFlight());

        passenger.joinFlight(null);
        assertNull(passenger.getFlight());
        assertEquals(0, flight.getNumberOfPassengers());
    }

    @Test
    void testPassengerSwitchFlight() {
        Flight anotherFlight = new Flight("BB5678", 2);

        passenger.joinFlight(flight);
        assertEquals(flight, passenger.getFlight());
        assertEquals(1, flight.getNumberOfPassengers());

        passenger.joinFlight(anotherFlight);
        assertEquals(anotherFlight, passenger.getFlight());
        assertEquals(0, flight.getNumberOfPassengers());
        assertEquals(1, anotherFlight.getNumberOfPassengers());
    }

    @Test
    void testPassengerCannotJoinFullFlight() {
        flight.addPassenger(new Passenger("67890", "Jane Smith", "GB"));

        Exception exception = assertThrows(RuntimeException.class, () -> passenger.joinFlight(flight));
        assertEquals("Not enough seats for flight AA1234", exception.getMessage());
    }

    @Test
    void testIntegrationPassengerToStringAfterJoin() {
        passenger.joinFlight(flight);
        String expected = "Passenger John Doe with identifier: 12345 from US";
        assertEquals(expected, passenger.toString());
    }
}

