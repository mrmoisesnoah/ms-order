package com.project.ms_order.repository;

import com.project.ms_order.model.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

}
