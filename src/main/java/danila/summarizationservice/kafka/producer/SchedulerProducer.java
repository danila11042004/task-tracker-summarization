package danila.summarizationservice.kafka.producer;

import danila.summarizationservice.kafka.message.SchedulerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SchedulerProducer {
    private final String topic;
    private final KafkaTemplate<String, SchedulerResponse> kafkaTemplate;

    public SchedulerProducer(@Value("${kafka.topics.scheduler-response}") String topic,
                             KafkaTemplate<String, SchedulerResponse> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(SchedulerResponse response) {
        try {
            kafkaTemplate.send(topic, response).get();
        } catch (Exception e) {
            log.error("Failed to send daily report №{}", response.uuid(), e);
            throw new KafkaException("Failed to send daily report", e);
        }
    }
}

