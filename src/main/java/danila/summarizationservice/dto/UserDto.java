package danila.summarizationservice.dto;

import java.util.List;

public record UserDto(
        Long id,
        String email,
        List<TaskDto> taskList) {
}
