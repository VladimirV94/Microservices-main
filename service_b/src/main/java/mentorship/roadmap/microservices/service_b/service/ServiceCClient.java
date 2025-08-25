package mentorship.roadmap.microservices.service_b.service;


import mentorship.roadmap.microservices.service_b.model.ResponseUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-c", url = "${SERVICE_C_URL}")
public interface ServiceCClient {

    @PostMapping("api/save")
    void saveUserIntoServiceC(ResponseUserDTO userDTO);
}
