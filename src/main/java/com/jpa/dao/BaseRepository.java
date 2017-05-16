package com.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.jpa.entity.BaseEntity;

/**
 * 基础Repository
 * @author lvpeng
 * @date 2017年5月9日 下午2:54:26
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> ,JpaSpecificationExecutor<T>{

}
