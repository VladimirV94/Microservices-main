package mentorship.roadmap.microservices.service_b.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash(value = "users", timeToLive = 300)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRedisHash implements Serializable {

    @Id
    private Long id;

    private String name;

    private String surname;

    private Integer age;

    private String phoneNumber;

}
