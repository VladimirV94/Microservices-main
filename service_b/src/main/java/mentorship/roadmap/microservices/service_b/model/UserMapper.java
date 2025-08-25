package mentorship.roadmap.microservices.service_b.model;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "importance", ignore = true)
    ResponseUserDTO toDTO(UserRedisHash userRedisHash);

    default ResponseUserDTO toDTO(UserRedisHash userRedisHash, Type importance){
        ResponseUserDTO dto = toDTO(userRedisHash);
        dto.setImportance(importance);
        return dto;
    }
    ResponseUserDTO toDTO(RequestUserDTO requestUserDTO);

    @Mapping(target = "id", ignore = true)  // ignore unmapped id
    UserRedisHash fromDTO(RequestUserDTO requestUserDTO);
}
