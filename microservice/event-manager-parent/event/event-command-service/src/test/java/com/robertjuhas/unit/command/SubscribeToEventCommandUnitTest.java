package com.robertjuhas.unit.command;

import com.robertjuhas.ddd.command.event.SubscribeToEventCommand;
import com.robertjuhas.rest.dto.request.SubscribeToEventRequestDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SubscribeToEventCommandUnitTest extends EventCommandUnitTest {

    @Test
    public void testNullZero() throws Exception {
        var violations = validator.forExecutables().validateConstructorParameters(SubscribeToEventCommand.class.getDeclaredConstructor(SubscribeToEventRequestDTO.class),
                new Object[]{null});
        assertThat(violations).hasSize(1);
    }
}
