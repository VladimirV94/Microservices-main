package mentorship.roadmap.microservices.service_a.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_a.model.RequestUserDTO;
import mentorship.roadmap.microservices.service_a.model.ResponseUserDTO;
import mentorship.roadmap.microservices.service_a.model.UserDocument;
import mentorship.roadmap.microservices.service_a.model.UserMapper;
import mentorship.roadmap.microservices.service_a.repository.UserRepository;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableFeignClients(basePackageClasses = ServiceBClient.class)
public class ConsumerService {

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

        ResponseUserDTO responseUserDTO = saveToMongo(requestUserDTO);
        serviceBClient.postUserToServiceB(responseUserDTO);
        log.info("User has been sent to service b: {}", responseUserDTO);
    }

    private ResponseUserDTO saveToMongo(RequestUserDTO requestUserDTO) {
        log.info("User start save to Mongo: {}", requestUserDTO);
        UserDocument userDocument = userRepository.save(userMapper.fromDTO(requestUserDTO));
        log.info("User has been saved to Mongo successfully: {}", requestUserDTO);
        ResponseUserDTO responseUserDTO = userMapper.toDTO(userDocument, requestUserDTO.getImportance());
        log.debug("ResponseUserDTO: {}", responseUserDTO);
        return responseUserDTO;
    }
}
