package com.ftn.backend.services;

import java.util.List;

/**
 * CRUD interfejs koji implementiraju servisi za podrsku krud operacija nad domenskim modelima
 *
 * @param <T> modelska klasa za koju se implementira servisni interfejs
 * 
 * @author Srdjan Lulic
 */
public interface CRUDService<T> {
    List<T> listAll();
 
    T getById(Integer id);
 
    T saveOrUpdate(T domainObject);
 
    void delete(Integer id);
}