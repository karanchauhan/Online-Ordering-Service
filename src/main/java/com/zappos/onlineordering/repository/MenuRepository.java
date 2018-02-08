package com.zappos.onlineordering.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.zappos.onlineordering.model.Menu;

@EnableScan
public interface MenuRepository extends CrudRepository<Menu, String> {

}
