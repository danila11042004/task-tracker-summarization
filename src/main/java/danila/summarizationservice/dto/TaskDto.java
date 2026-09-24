package danila.summarizationservice.dto;

import java.time.LocalDateTime;

public record TaskDto(
        Long id,
        String headline,
        String textContent,
        String status,
        LocalDateTime completedAt) {
}
