package com.stc.stc.repository;

import com.stc.stc.entities.Files;
import com.stc.stc.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<Files, Integer> {
}
