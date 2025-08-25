package mentorship.roadmap.microservices.service_c.model;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "importance", ignore = true)
    ResponseUserDTO toDTO(UserEntity userEntity);

    default ResponseUserDTO toDTO(UserEntity userEntity, Type importance){
        ResponseUserDTO dto = toDTO(userEntity);
        dto.setImportance(importance);
        return dto;
    }

    @Mapping(target = "id", ignore = true)  // ignore unmapped id
    UserEntity fromDTO(RequestUserDTO user);
}
