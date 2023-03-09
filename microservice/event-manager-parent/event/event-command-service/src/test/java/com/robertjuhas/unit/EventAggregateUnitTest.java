package com.robertjuhas.unit;

import com.robertjuhas.aggregator.EventAggregate;
import com.robertjuhas.ddd.command.event.CreateEventCommand;
import com.robertjuhas.ddd.command.event.SubscribeToEventCommand;
import com.robertjuhas.ddd.command.event.UnsubscribeFromEventCommand;
import com.robertjuhas.ddd.command.event.UpdateEventCommand;
import com.robertjuhas.ddd.event.event.AggregateEventEventCreated;
import com.robertjuhas.exception.EventManagerException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EventAggregateUnitTest {
    private static final ZonedDateTime TIME = ZonedDateTime.now();
    private static final long CAPACITY = 50L;
    private static final String PLACE = "Test place";
    private static final String TITLE = "Test title";
    private static final long USER_ID = 5;

    private EventAggregate eventAggregate;
    private AggregateEventEventCreated eventCreatedEvent;
    private static Validator validator;

    @BeforeAll
    public static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    public void setup() {
        var createEventCommand = new CreateEventCommand(TIME, CAPACITY, PLACE, TITLE, USER_ID);
        eventAggregate = new EventAggregate();
        eventCreatedEvent = eventAggregate.process(createEventCommand);
    }

    @Test
    public void testCreateAggregate() {
        assertThat(eventAggregate.getId()).isNotBlank();
        assertThat(eventCreatedEvent).isNotNull();
        assertThat(eventCreatedEvent.capacity()).isEqualTo(CAPACITY);
        assertThat(eventCreatedEvent.place()).isEqualTo(PLACE);
        assertThat(eventCreatedEvent.title()).isEqualTo(TITLE);
        assertThat(eventCreatedEvent.time()).isEqualTo(TIME);
        assertThat(eventCreatedEvent.userID()).isEqualTo(USER_ID);
    }

    @Test
    public void testUpdateAggregateNull() {
        for (Method method : EventAggregate.class.getDeclaredMethods()) {
            if (method.getName().equals("process")) {
                var constraintViolations = validator.forExecutables().validateParameters(eventAggregate, method, new Object[]{null});
                assertThat(constraintViolations).isNotEmpty();
            }
        }
    }

    @Test
    public void testUpdateEventAggregate() {
        final String aggregateID = "1";
        final ZonedDateTime zonedDateTime = ZonedDateTime.now().minusDays(1);
        final long capacity = 100;
        final String place = "some place";
        final String title = "some title";
        var updateEventCommand = new UpdateEventCommand(aggregateID, zonedDateTime, capacity, place, title);
        var eventUpdated = eventAggregate.process(updateEventCommand);
        assertThat(eventUpdated).isNotNull();
        assertThat(eventUpdated.getCapacity()).isEqualTo(capacity);
        assertThat(eventUpdated.getPlace()).isEqualTo(place);
        assertThat(eventUpdated.getTime()).isEqualTo(zonedDateTime);
        assertThat(eventUpdated.getTitle()).isEqualTo(title);
    }

    @ParameterizedTest
    @MethodSource("wrongTimes")
    public void testWrongTimeUpdate(ZonedDateTime time) {
        var updateEventCommand = new UpdateEventCommand(eventAggregate.getId(), time, 0, null, null);
        assertThatThrownBy(() -> eventAggregate.process(updateEventCommand)).isInstanceOf(EventManagerException.class);
    }

    private static Stream<Arguments> wrongTimes() {
        return Stream.of(
                Arguments.of(TIME.minusHours(5)),
                Arguments.of(TIME.plusHours(1))
        );
    }

    @Test
    public void subscribeToEventAggregate() {
        String aggregateID = "1";
        long userID = 1;
        var command = new SubscribeToEventCommand(aggregateID, userID);
        var event = eventAggregate.process(command);
        assertThat(event).isNotNull();
        assertThat(event.userID()).isEqualTo(userID);
        assertThat(event.subscribed().subscribed()).isNotNull();
    }

    @Test
    public void subscrtibeToEvenMoreThanOnce() {
        String aggregateID = "1";
        long userID = 1;
        var command = new SubscribeToEventCommand(aggregateID, userID);
        eventAggregate.process(command);
        assertThatThrownBy(() -> eventAggregate.process(command)).isInstanceOf(EventManagerException.class);
    }

    @Test
    public void unsubscribeFromEventAggregate() {
        String aggregateID = "1";
        long userID = 1;
        eventAggregate.process(new SubscribeToEventCommand(aggregateID, userID));
        var unsubscribedEvent = eventAggregate.process(new UnsubscribeFromEventCommand(aggregateID, userID));
        assertThat(unsubscribedEvent).isNotNull();
        assertThat(unsubscribedEvent.userID()).isEqualTo(userID);
    }

    @Test
    public void unsubscribeFromEventNotExisting() {
        String aggregateID = "1";
        long userID = 1;
        assertThatThrownBy(() -> eventAggregate.process(new UnsubscribeFromEventCommand(aggregateID, userID))).isInstanceOf(EventManagerException.class);
    }
}
