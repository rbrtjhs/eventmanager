package com.robertjuhas.unit.command;

import com.robertjuhas.ddd.command.event.UpdateEventCommand;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateEventCommandUnitTest extends EventCommandUnitTest {

    @Test
    public void updateEventCommandNu() throws NoSuchMethodException {
        var violations = validator.forExecutables().validateConstructorParameters(
                UpdateEventCommand.class.getDeclaredConstructor(String.class, ZonedDateTime.class, Long.TYPE, String.class, String.class),
                new Object[]{null, null, null, null, null});
        assertThat(violations).hasSize(1);
    }
}
