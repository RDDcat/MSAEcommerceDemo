package com.maro.catalogservice.service;

import com.maro.catalogservice.repository.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
