package mentorship.roadmap.microservices.service_a.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseUserDTO {
    private String name;
    private String surname;
    private Integer age;
    private String phoneNumber;
    private Type importance;
}
