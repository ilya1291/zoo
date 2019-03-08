package ru.ilya.zoo.resource;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "Cages", description = "Cages resource")
@RequestMapping("api/cages")
public interface CageResource {


}
