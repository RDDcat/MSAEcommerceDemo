package com.maro.catalogservice.service;

import com.maro.catalogservice.repository.CatalogEntity;
import com.maro.catalogservice.repository.CatalogRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class CatalogServiceImpl implements CatalogService{
    private CatalogRepository repository;

    @Autowired
    public CatalogServiceImpl(CatalogRepository repository) {
        this.repository = repository;
    }

    @Override
    public Iterable<CatalogEntity> getAllCatalogs() {
        return repository.findAll();
    }
}
