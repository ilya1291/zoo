package ru.ilya.zoo.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RestController;
import ru.ilya.zoo.dto.keeper.KeeperCreateDto;
import ru.ilya.zoo.dto.keeper.KeeperResponseDto;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.service.KeeperService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KeeperResourceImpl implements KeeperResource {

    private final KeeperService keeperService;
    private final MapperFacade mapperFacade;

    @Override
    public List<KeeperResponseDto> getAll() {
        log.debug("getAll - start");

        List<Keeper> keepers = keeperService.getAll();
        List<KeeperResponseDto> result = mapperFacade.mapAsList(keepers, KeeperResponseDto.class);

        log.debug("getAll - end: result = {}", result);
        return result;
    }

    @Override
    public KeeperResponseDto create(@Valid KeeperCreateDto dto) {
        log.debug("create - start: dto = {}", dto);

        Keeper keeper = mapperFacade.map(dto, Keeper.class);
        Keeper createdKeeper = keeperService.create(keeper);
        KeeperResponseDto result = mapperFacade.map(createdKeeper, KeeperResponseDto.class);

        log.debug("create - end: result = {}", result);
        return result;
    }

    @Override
    public void deleteById(Long keeperId) {
        log.debug("deleteById - start: keeperId = {}", keeperId);

        keeperService.delete(keeperId);

        log.debug("deleteById - end: keeperId = {}", keeperId);
    }
}
