package mentorship.roadmap.microservices.service_b.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_b.model.*;
import mentorship.roadmap.microservices.service_b.repository.UserRepository;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableFeignClients(basePackageClasses = ServiceCClient.class)
public class ProcessService {

    private final UserMapper mapper;
    private final UserRepository userRepository;
    private final ServiceCClient serviceCClient;

    @Transactional
    public void postUser(@NonNull RequestUserDTO requestUserDTO) {
        ResponseUserDTO responseUserDTO = requestUserDTO.getImportance() == Type.IMPORTANT ?
                saveToRedis(requestUserDTO) :
                mapper.toDTO(requestUserDTO);

        log.debug("ResponseUserDTO: {}", responseUserDTO);

        serviceCClient.saveUserIntoServiceC(responseUserDTO);
        log.info("User has been sent to service c: {}", responseUserDTO);
    }

    private ResponseUserDTO saveToRedis(RequestUserDTO requestUserDTO) {
        log.info("User start save to Redis: {}", requestUserDTO);
        UserRedisHash userRedisHash = userRepository.save(mapper.fromDTO(requestUserDTO));
        log.info("User has been saved to Redis successfully: {}", requestUserDTO);
        return mapper.toDTO(userRedisHash, requestUserDTO.getImportance());
    }
}
