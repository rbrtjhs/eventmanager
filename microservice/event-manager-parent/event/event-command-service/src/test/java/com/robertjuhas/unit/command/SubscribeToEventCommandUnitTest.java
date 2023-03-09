package com.robertjuhas.unit.command;

import com.robertjuhas.ddd.command.event.SubscribeToEventCommand;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SubscribeToEventCommandUnitTest extends EventCommandUnitTest {

    @Test
    public void testNullZero() throws Exception {
        var violations = validator.forExecutables().validateConstructorParameters(SubscribeToEventCommand.class.getDeclaredConstructor(String.class, Long.TYPE),
                new Object[]{null, 0L});
        assertThat(violations).hasSize(2);
    }
}
