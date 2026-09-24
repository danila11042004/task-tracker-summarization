package danila.summarizationservice.kafka.message;

import danila.summarizationservice.dto.TaskDto;

import java.util.List;
import java.util.UUID;

public record SchedulerResponse(
        UUID uuid,
        List<TaskDto> taskList,
        String email,
        String dailyReport) {
}
