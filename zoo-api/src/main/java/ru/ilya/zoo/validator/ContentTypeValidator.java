package ru.ilya.zoo.validator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.ilya.zoo.validator.annotations.ValidContentType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "zoo.import")
public class ContentTypeValidator implements ConstraintValidator<ValidContentType, MultipartFile> {

    private HashSet<String> supportedMimeTypes;

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        String contentType = multipartFile.getContentType();
        return supportedMimeTypes.contains(contentType);
    }
}
