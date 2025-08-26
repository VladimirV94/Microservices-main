package mentorship.roadmap.microservices.service_c.service;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_c.model.RequestUserDTO;
import mentorship.roadmap.microservices.service_c.model.ResponseUserDTO;
import mentorship.roadmap.microservices.service_c.model.UserEntity;
import mentorship.roadmap.microservices.service_c.model.UserMapper;
import mentorship.roadmap.microservices.service_c.repository.UserRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private static final String TOPIC = "out";
    private final UserRepository repository;
    private final UserMapper mapper;
    private final KafkaTemplate<String, ResponseUserDTO> kafkaTemplate;

	@Transactional
	public void saveUser(@NonNull RequestUserDTO requestUserDTO) {
        ResponseUserDTO responseUserDTO = saveToPostgres(requestUserDTO);
        sendToKafka(responseUserDTO);
	}

    private ResponseUserDTO saveToPostgres(RequestUserDTO requestUserDTO) {
        log.info("User start save to Postgres {}", requestUserDTO);
        UserEntity userEntity = repository.save(mapper.fromDTO(requestUserDTO));
        log.info("User has been saved to Postgres successfully {}", requestUserDTO);
        return mapper.toDTO(repository.save(userEntity), requestUserDTO.getImportance());
    }

    private void sendToKafka(ResponseUserDTO responseUserDTO) {
        log.info("Message start send to Kafka: {}", responseUserDTO);
        kafkaTemplate.send(TOPIC, responseUserDTO);
        log.info("Message has been sent to Kafka: {}", responseUserDTO);
    }
}