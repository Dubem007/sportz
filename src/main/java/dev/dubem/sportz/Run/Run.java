package dev.dubem.sportz.Run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

public record Run(
        Integer id,
        @NotEmpty
        String title,
        LocalDateTime startedOn,
        LocalDateTime completedOn,
        @Positive
        Integer miles,
        Location location) {

    public Run{
        if(!completedOn.isAfter(startedOn))
        {
            throw new IllegalArgumentException("Completed on must be after started on");
        }
    }

}
