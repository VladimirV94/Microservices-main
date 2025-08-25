package mentorship.roadmap.microservices.service_c.controller;

import lombok.AllArgsConstructor;
import mentorship.roadmap.microservices.service_c.model.RequestUserDTO;
import mentorship.roadmap.microservices.service_c.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/save")
@AllArgsConstructor
public class SaveController {

    private UserService userService;

    @PostMapping()
    @Transactional
    public String sendMessage(@RequestBody RequestUserDTO userDTO) {
        userService.saveUser(userDTO);
        return "Message sent: " + userDTO;
    }
}
