/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;

/**
 *
 * @author ledin
 */
public abstract class EduSysDao<E, K> {

    abstract public void insert(E entity);

    abstract public void update(E entity);

    abstract public void delete(K id);

    abstract public E selectById(K id);

    abstract public List<E> selectAll();

    abstract protected List<E> selectBySql(String sql, Object... args);
}
