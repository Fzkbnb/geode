package com.blocain.geode.service;

import com.google.common.collect.Lists;
import org.springframework.data.domain.Sort;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class GenericServiceImpl<T, ID> implements GenericService<T, ID>
{
    private GemfireRepository gemfireRepository;
    
    public GenericServiceImpl(GemfireRepository gemfireRepository)
    {
        this.gemfireRepository = gemfireRepository;
    }
    
    @Override
    //@Transactional
    public T save(T entity)
    {
        return (T) gemfireRepository.save(entity);
    }
    
    @Override
    ////@Transactional
    public List<T> saveAll(List<T> entities)
    {
        Iterable<T> result = gemfireRepository.saveAll(entities);
        return Lists.newArrayList(result);
    }
    
    @Override
    //@Transactional
    public List<T> saveAll(List<T> entities, boolean rollback)
    {
        Iterable<T> result = gemfireRepository.saveAll(entities);
        if (rollback) throw new RuntimeException("save failed - should trigger a rollback.");
        return Lists.newArrayList(result);
    }
    
    @Override
    //@Transactional
    public void deleteById(ID id)
    {
        gemfireRepository.deleteById(id);
    }
    
    @Override
    //@Transactional
    public void delete(T entity)
    {
        gemfireRepository.delete(entity);
    }
    
    @Override
    //@Transactional
    public void delete(List<? extends T> entities)
    {
        gemfireRepository.deleteAll(entities);
    }
    
    @Override
    public long count()
    {
        return gemfireRepository.count();
    }
    
    @Override
    public T findById(ID id)
    {
        Optional<T> data = gemfireRepository.findById(id);
        return data.orElse(null);
    }
    
    @Override
    public boolean existsById(ID id)
    {
        return gemfireRepository.existsById(id);
    }
    
    @Override
    public List<T> findAll()
    {
        Iterable<T> result = gemfireRepository.findAll();
        return null != result ? Lists.newArrayList(result) : null;
    }
    
    @Override
    public List<T> findAll(Sort sort)
    {
        Iterable<T> result = gemfireRepository.findAll(sort);
        return null != result ? Lists.newArrayList(result) : null;
    }
    
    @Override
    public List<T> findAllById(List<ID> ids)
    {
        Iterable<T> result = gemfireRepository.findAllById(ids);
        return null != result ? Lists.newArrayList(result) : null;
    }
    
    @Override
    public void clear()
    {
        gemfireRepository.deleteAll();
    }
}
