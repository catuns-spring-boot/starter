package xyz.catuns.spring.jwt.config.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import xyz.catuns.spring.jwt.model.UserRole;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<Set<UserRole>, Set<String>> {

    @Override
    public Set<String> convertToDatabaseColumn(Set<UserRole> roles) {
        return roles.stream()
                .map(UserRole::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UserRole> convertToEntityAttribute(Set<String> dbData) {
        if (Objects.isNull(dbData)) return new HashSet<>();
        return dbData.stream()
                .map(UserRole::new)
                .collect(Collectors.toSet());
    }
}
