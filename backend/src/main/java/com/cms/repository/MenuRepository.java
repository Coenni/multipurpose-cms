package com.cms.repository;

import com.cms.model.entity.Menu;
import com.cms.model.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    
    List<Menu> findBySite(Site site);
    
    Optional<Menu> findBySiteAndLocation(Site site, String location);
}
