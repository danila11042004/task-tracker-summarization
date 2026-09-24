package danila.summarizationservice.kafka.message;

import danila.summarizationservice.dto.UserDto;

import java.util.UUID;

public record SchedulerRequest(
        UUID uuid,
        UserDto userDto) {
}
