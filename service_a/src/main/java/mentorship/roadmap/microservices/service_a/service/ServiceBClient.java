package mentorship.roadmap.microservices.service_a.service;


import mentorship.roadmap.microservices.service_a.model.ResponseUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-b", url = "${SERVICE_B_URL}")
public interface ServiceBClient {

    @PostMapping("api/process")
    void postUserToServiceB(ResponseUserDTO responseUserDTO);
}
