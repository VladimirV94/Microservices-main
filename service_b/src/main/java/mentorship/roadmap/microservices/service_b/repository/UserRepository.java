package mentorship.roadmap.microservices.service_b.repository;

import mentorship.roadmap.microservices.service_b.model.UserRedisHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserRedisHash, String> {
}
