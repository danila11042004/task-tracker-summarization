package danila.summarizationservice.service;

import danila.summarizationservice.dto.GigaChatRequestDto;
import danila.summarizationservice.dto.TaskDto;
import danila.summarizationservice.enums.GigaChatMessageRole;
import danila.summarizationservice.enums.GigaChatModel;
import danila.summarizationservice.enums.GigaChatResponseFormatType;
import danila.summarizationservice.gigachat.GigaChatClient;
import danila.summarizationservice.gigachat.model.GigaChatMessageRequest;
import danila.summarizationservice.gigachat.model.GigaChatResponseFormat;
import danila.summarizationservice.kafka.message.SchedulerRequest;
import danila.summarizationservice.util.GigaChatPrompts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SummarizationService {
    private final GigaChatClient gigaChatClient;

    public String generateDailyReport(SchedulerRequest schedulerRequest) {
        GigaChatMessageRequest systemMessage = new GigaChatMessageRequest(GigaChatMessageRole.SYSTEM,
                GigaChatPrompts.SYSTEM_MESSAGE);
        String tasksText = convertTasksUserFromJsonToString(schedulerRequest);
        GigaChatMessageRequest userMessage = new GigaChatMessageRequest(GigaChatMessageRole.USER, tasksText);
        GigaChatRequestDto gigaChatRequestDto = new GigaChatRequestDto(GigaChatModel.GIGACHAT_2_LITE,
                List.of(systemMessage, userMessage), false,
                new GigaChatResponseFormat(GigaChatResponseFormatType.TEXT));
        return gigaChatClient.sendRequestForDailyReport(gigaChatRequestDto);
    }

    private String convertTasksUserFromJsonToString(SchedulerRequest schedulerRequest) {
        StringBuilder convertedTasks = new StringBuilder();
        List<TaskDto> taskDtoList = schedulerRequest.userDto().taskList();
        for (TaskDto taskDto : taskDtoList) {
            convertedTasks.append("Задача N ").append(taskDto.id()).append(System.lineSeparator());
            convertedTasks.append("Заголовок - ").append(taskDto.headline()).append(System.lineSeparator());
            convertedTasks.append("Подробности - ").append(taskDto.textContent()).append(System.lineSeparator());
            convertedTasks.append("Статус - ").append(taskDto.status()).append(System.lineSeparator());
            if (taskDto.completedAt() != null) {
                convertedTasks.append("Выполнен - ").append(taskDto.completedAt()).append(System.lineSeparator());
            }
            convertedTasks.append(System.lineSeparator());
        }
        return convertedTasks.toString();
    }
}
