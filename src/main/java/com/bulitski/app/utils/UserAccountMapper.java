package com.bulitski.app.utils;

import com.bulitski.app.dto.UserAccountDto;
import com.bulitski.app.model.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Mapper( componentModel = "spring")
public interface UserAccountMapper {

    UserAccountMapper INSTANCE= Mappers.getMapper(UserAccountMapper.class);
@Mappings({
        @Mapping(target = "id",source = "userAccount.id"),
        @Mapping(target = "userName",source = "userAccount.userName"),
        @Mapping(target = "password",source = "userAccount.password" , ignore = true),
        @Mapping(target = "firstName",source = "userAccount.firstName"),
        @Mapping(target = "lastName",source = "userAccount.lastName"),
        @Mapping(target = "status",source = "userAccount.status"),
        @Mapping(target = "role",source = "userAccount.role"),
        @Mapping(target = "createdAt",source = "userAccount.createdAt")

})
    UserAccountDto userAccountToUserAccountDto(UserAccount userAccount);

    @Mappings({
            @Mapping(target = "userName",source = "userAccountDto.userName"),
            @Mapping(target = "password",expression = "java(passwordEncoder.encode(userAccountDto.getPassword()))"),
            @Mapping(target = "firstName",source = "userAccountDto.firstName"),
            @Mapping(target = "lastName",source = "userAccountDto.lastName"),
            @Mapping(target = "status",source = "userAccountDto.status"),
            @Mapping(target = "role",source = "userAccountDto.role"),
            @Mapping(target = "createdAt",expression = "java(localDate)")

    })
    UserAccount userAccountDtoToUserAccount(UserAccountDto userAccountDto, PasswordEncoder passwordEncoder, LocalDate localDate);
}
