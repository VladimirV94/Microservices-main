package mentorship.roadmap.microservices.service_a.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDocument {

    @Id
    private String id;
    private String name;
    private String surname;
    private Integer age;
    private String phoneNumber;

}
