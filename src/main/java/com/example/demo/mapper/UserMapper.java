package com.example.demo.mapper;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDTO userRequestDTO);
}
