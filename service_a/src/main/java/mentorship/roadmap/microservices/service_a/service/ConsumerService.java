package mentorship.roadmap.microservices.service_a.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import mentorship.roadmap.microservices.service_a.model.RequestUserDTO;
import mentorship.roadmap.microservices.service_a.model.ResponseUserDTO;
import mentorship.roadmap.microservices.service_a.model.UserDocument;
import mentorship.roadmap.microservices.service_a.model.UserMapper;
import mentorship.roadmap.microservices.service_a.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@EnableFeignClients(basePackageClasses = ServiceBClient.class)
public class ConsumerService {

    private static final Logger log = LoggerFactory.getLogger(ConsumerService.class);
    private final ObjectMapper kafkaMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ServiceBClient serviceBClient;

    @KafkaListener(topics = "in", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void consume(String userMessage) throws JsonProcessingException {
        log.info("Received user from Kafka: {}", userMessage);
        RequestUserDTO requestUserDTO = kafkaMapper.readValue(userMessage, RequestUserDTO.class);
        log.debug("RequestUserDTO: {}", requestUserDTO);
        saveToMongo(requestUserDTO).ifPresentOrElse(dto -> {
            serviceBClient.postUserToServiceB(dto);
            log.info("User has been sent to service b");
        }, ()->log.warn("User has not been sent to service b"));
    }

    private Optional<ResponseUserDTO> saveToMongo(RequestUserDTO requestUserDTO) {
        log.info("User start save to Mongo");
        UserDocument userDocument = userRepository.save(userMapper.fromDTO(requestUserDTO));
        log.info("User has been saved to Mongo successfully");
        ResponseUserDTO dto = userMapper.toDTO(userDocument, requestUserDTO.getImportance());
        log.debug("ResponseUserDTO: {}", dto);
        return Optional.ofNullable(dto);
    }
}
