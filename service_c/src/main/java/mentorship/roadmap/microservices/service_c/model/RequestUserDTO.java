package mentorship.roadmap.microservices.service_c.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RequestUserDTO {
    private String name;
    private String surname;
    private Integer age;
    private String phoneNumber;
    private Type importance;
}