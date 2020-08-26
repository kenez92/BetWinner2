package com.kenez92.betwinner.service.users.strategy.factory;

import com.kenez92.betwinner.service.users.strategy.UserStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.AttributeConverter;

@Service
@RequiredArgsConstructor
public class UserStrategyConverter implements AttributeConverter<UserStrategy, String> {
    private final UserStrategyFactory userStrategyFactory;

    @Override
    public String convertToDatabaseColumn(UserStrategy attribute) {
        return attribute.getClass().getSimpleName().toUpperCase();
    }

    @Override
    public UserStrategy convertToEntityAttribute(String dbData) {
        return userStrategyFactory.factory(dbData);
    }
}