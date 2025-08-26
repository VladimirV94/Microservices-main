package mentorship.roadmap.microservices.service_b.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_b.model.RequestUserDTO;
import mentorship.roadmap.microservices.service_b.service.ProcessService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/process")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessService processService;

    @PostMapping()
    public void postUser(@RequestBody RequestUserDTO requestUserDTO) {
        log.info("User received from client {}", requestUserDTO);
        processService.postUser(requestUserDTO);
    }

}
