package mentorship.roadmap.microservices.service_b.service;

import lombok.AllArgsConstructor;
import mentorship.roadmap.microservices.service_b.model.*;
import mentorship.roadmap.microservices.service_b.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableFeignClients(basePackageClasses = ServiceCClient.class)
public class ProcessService {

    private static final Logger log = LoggerFactory.getLogger(ProcessService.class);
    private UserMapper mapper;
    private final UserRepository userRepository;
    private final ServiceCClient serviceCClient;

    @Transactional
    public void postUser(RequestUserDTO requestUserDTO) {
        log.info("User received from client {}", requestUserDTO);
        Objects.requireNonNull(requestUserDTO, "requestUserDTO is null");
        Optional<ResponseUserDTO> responseUserDTO = requestUserDTO.getImportance() == Type.IMPORTANT ?
                saveToRedis(requestUserDTO) :
                Optional.ofNullable(mapper.toDTO(requestUserDTO));

        log.debug("ResponseUserDTO: {}", responseUserDTO);

        responseUserDTO.ifPresentOrElse(dto -> {
            serviceCClient.saveUserIntoServiceC(dto);
            log.info("User has been sent to service c");
        }, ()->log.warn("User has not been sent to service c"));
    }

    private Optional<ResponseUserDTO> saveToRedis(RequestUserDTO requestUserDTO) {
        log.info("User start save to Redis");
        UserRedisHash userRedisHash = userRepository.save(mapper.fromDTO(requestUserDTO));
        log.info("User has been saved to Redis successfully");
        return Optional.ofNullable(mapper.toDTO(userRedisHash, requestUserDTO.getImportance()));
    }
}
