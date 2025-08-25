package mentorship.roadmap.microservices.service_b.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import mentorship.roadmap.microservices.service_b.model.RequestUserDTO;
import mentorship.roadmap.microservices.service_b.service.ProcessService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/process")
@AllArgsConstructor
public class ProcessController {

    private final ProcessService processService;

    @PostMapping()
    public void postUser(@RequestBody RequestUserDTO userDTO) {
        processService.postUser(userDTO);
    }

}
