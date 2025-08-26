package mentorship.roadmap.microservices.service_c.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_c.model.RequestUserDTO;
import mentorship.roadmap.microservices.service_c.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@Slf4j
@RequestMapping("/api/save")
@RequiredArgsConstructor
public class SaveController {

    private final UserService userService;

    @PostMapping()
    @Transactional
    public void sendMessage(@RequestBody RequestUserDTO requestUserDTO) {
        log.info("User received from client {}", requestUserDTO);
        userService.saveUser(requestUserDTO);
    }
}
