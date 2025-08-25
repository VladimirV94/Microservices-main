package mentorship.roadmap.microservices.service_c.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import mentorship.roadmap.microservices.service_c.model.RequestUserDTO;
import mentorship.roadmap.microservices.service_c.model.ResponseUserDTO;
import mentorship.roadmap.microservices.service_c.model.UserEntity;
import mentorship.roadmap.microservices.service_c.model.UserMapper;
import mentorship.roadmap.microservices.service_c.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private static final String TOPIC = "out";
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private UserRepository repository;
    private UserMapper mapper;
    private final KafkaTemplate<String, ResponseUserDTO> kafkaTemplate;

	@Transactional
	public Optional<ResponseUserDTO> saveUser(RequestUserDTO requestUserDTO) {
        log.info("User received from client {}", requestUserDTO);
        Objects.requireNonNull(requestUserDTO, "requestUserDTO is null");
        Optional<ResponseUserDTO> responseUserDTO = saveToPostgres(requestUserDTO);
        responseUserDTO.ifPresentOrElse(dto -> {
            kafkaTemplate.send(TOPIC, dto);
            log.info("Message has been sent to Kafka");
        }, () ->log.warn("Message has not been sent to Kafka"));
        return responseUserDTO;
	}

    private Optional<ResponseUserDTO> saveToPostgres(RequestUserDTO requestUserDTO) {
        log.info("User start save to Postgres");
        UserEntity userEntity = repository.save(mapper.fromDTO(requestUserDTO));
        log.info("User has been saved to Postgres successfully");
        return Optional.ofNullable(mapper.toDTO(repository.save(userEntity), requestUserDTO.getImportance()));
    }
}