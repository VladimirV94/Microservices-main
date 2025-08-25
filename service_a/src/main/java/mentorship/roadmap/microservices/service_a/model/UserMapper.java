package mentorship.roadmap.microservices.service_a.model;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "importance", ignore = true)
    ResponseUserDTO toDTO(UserDocument userDocument);

    default ResponseUserDTO toDTO(UserDocument userDocument, Type importance){
        ResponseUserDTO dto = toDTO(userDocument);
        dto.setImportance(importance);
        return dto;
    }

    @Mapping(target = "id", ignore = true)  // ignore unmapped id
    UserDocument fromDTO(RequestUserDTO user);
}
