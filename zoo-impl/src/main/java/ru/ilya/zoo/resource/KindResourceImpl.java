package ru.ilya.zoo.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RestController;
import ru.ilya.zoo.dto.kind.KindCreateDto;
import ru.ilya.zoo.dto.kind.KindResponseDto;
import ru.ilya.zoo.model.Kind;
import ru.ilya.zoo.service.impl.KindService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KindResourceImpl implements KindResource {

    private final KindService kindService;
    private final MapperFacade mapperFacade;

    @Override
    public List<KindResponseDto> getAll() {
        log.debug("getAll - start");

        List<Kind> kinds = kindService.getAll();
        List<KindResponseDto> result = mapperFacade.mapAsList(kinds, KindResponseDto.class);

        log.debug("getAll - end: result = {}", result);
        return result;
    }

    @Override
    public KindResponseDto getOne(Long kindId) {
        log.debug("getOne - start");

        Kind kind = kindService.getOne(kindId);
        KindResponseDto result = mapperFacade.map(kind, KindResponseDto.class);

        log.debug("getOne - end: result = {}", result);
        return result;
    }

    @Override
    public KindResponseDto create(@Valid KindCreateDto dto) {
        log.debug("create - start: dto = {}", dto);

        Kind kind = mapperFacade.map(dto, Kind.class);
        Kind createdKind = kindService.create(kind);
        KindResponseDto result = mapperFacade.map(createdKind, KindResponseDto.class);

        log.debug("create - end: result = {}", result);
        return result;
    }

    @Override
    public void deleteById(Long kindId) {
        log.debug("deleteById - start: kindId = {}", kindId);

        kindService.delete(kindId);

        log.debug("deleteById - end: kindId = {}", kindId);
    }
}
