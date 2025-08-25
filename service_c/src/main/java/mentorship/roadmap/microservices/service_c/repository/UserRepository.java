package mentorship.roadmap.microservices.service_c.repository;


import mentorship.roadmap.microservices.service_c.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
