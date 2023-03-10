package com.robertjuhas.unit.command;

import com.robertjuhas.ddd.command.event.CreateEventCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateEventCommandUnitTest extends EventCommandUnitTest {

    @ParameterizedTest
    @MethodSource("nullEmptyNegativeValuesCreateEventCommand")
    public void createEventCommandNullEmptyNegative(ZonedDateTime time, long capacity, String place, String title, long createdBy) throws NoSuchMethodException {
        var violations = validator.forExecutables().validateConstructorParameters(
                CreateEventCommand.class.getDeclaredConstructor(ZonedDateTime.class, Long.TYPE, String.class, String.class, Long.TYPE),
                new Object[]{time, capacity, place, title, createdBy}
        );
        assertThat(violations).hasSize(5);
    }

    private static Stream<Arguments> nullEmptyNegativeValuesCreateEventCommand() {
        return Stream.of(
                Arguments.of(null, -5, null, null, -5),
                Arguments.of(null, 0, " ", "", 0)
        );
    }

    @Test
    public void createEventCommandNullEmptyNegative() throws NoSuchMethodException {
        var violations = validator.forExecutables().validateConstructorParameters(
                CreateEventCommand.class.getDeclaredConstructor(ZonedDateTime.class, Long.TYPE, String.class, String.class, Long.TYPE),
                new Object[]{null, 0L, " ", "", -5L}
        );
        assertThat(violations).hasSize(5);
    }
}
