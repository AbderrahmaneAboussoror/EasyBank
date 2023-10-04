package com.easybank.shared.generic;

import java.util.List;

public interface IData<E, ID> {
    E save(E entity);
    boolean delete(ID id);
    List<E> findAll();
}
