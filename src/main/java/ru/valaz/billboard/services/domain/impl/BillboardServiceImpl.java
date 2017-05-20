package ru.valaz.billboard.services.domain.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.valaz.billboard.domain.Billboard;
import ru.valaz.billboard.services.domain.BillboardService;
import ru.valaz.billboard.services.repositories.BillboardRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillboardServiceImpl implements BillboardService {

    private Logger LOGGER = LoggerFactory.getLogger(BillboardServiceImpl.class);

    private BillboardRepository billboardRepository;

    @Autowired
    public BillboardServiceImpl(BillboardRepository billboardRepository) {
        this.billboardRepository = billboardRepository;
    }

    @Override
    public List<Billboard> listAll() {
        List<Billboard> billboards = new ArrayList<>();
        billboardRepository.findAll().forEach(billboards::add);
        return billboards;
    }

    @Override
    public Billboard getById(Long id) {
        return billboardRepository.findOne(id);
    }

    @Override
    public Billboard saveOrUpdate(Billboard domainObject) {
        return billboardRepository.save(domainObject);
    }

    @Override
    public void delete(Long id) {
        Billboard billboard = billboardRepository.findOne(id);
        billboard.getUser().getBillboards().remove(billboard);
        billboardRepository.delete(id);
        LOGGER.info("Billboard {} deleted by {}", id, billboard.getUser().getUsername());
    }
}
