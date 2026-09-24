package danila.summarizationservice.kafka.consumer;

import danila.summarizationservice.kafka.message.SchedulerRequest;
import danila.summarizationservice.kafka.message.SchedulerResponse;
import danila.summarizationservice.kafka.producer.SchedulerProducer;
import danila.summarizationservice.service.SummarizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SchedulerConsumer {
    private final SchedulerProducer schedulerProducer;
    private final SummarizationService summarizationService;

    @KafkaListener(topics = "${kafka.topics.scheduler-request}",
            properties = "spring.json.value.default.type=danila.summarizationservice.kafka.message.SchedulerRequest",
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(SchedulerRequest request) {
        String dailyReport = summarizationService.generateDailyReport(request);
        SchedulerResponse response = new SchedulerResponse(UUID.randomUUID(), request.userDto().taskList(),
                request.userDto().email(), dailyReport);
        schedulerProducer.send(response);
    }
}
