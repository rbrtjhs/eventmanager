package com.robertjuhas.unit.command;

import com.robertjuhas.ddd.command.event.UnsubscribeFromEventCommand;
import com.robertjuhas.rest.dto.request.UnsubscribeFromEventRequestDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnsubscribeFromEventCommandUnitTest extends EventCommandUnitTest {
    @Test
    public void testNullZero() throws Exception {
        var violations = validator.forExecutables().validateConstructorParameters(UnsubscribeFromEventCommand.class.getDeclaredConstructor(UnsubscribeFromEventRequestDTO.class, Long.TYPE),
                new Object[]{null, 0L});
        assertThat(violations).hasSize(2);

        violations = validator.forExecutables().validateConstructorParameters(UnsubscribeFromEventCommand.class.getDeclaredConstructor(String.class, Long.TYPE),
                new Object[]{null, 0L});
        assertThat(violations).hasSize(2);
    }
}
