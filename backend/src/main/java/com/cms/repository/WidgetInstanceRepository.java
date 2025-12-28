package com.cms.repository;

import com.cms.model.entity.SiteThemeConfig;
import com.cms.model.entity.WidgetInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WidgetInstanceRepository extends JpaRepository<WidgetInstance, Long> {
    
    List<WidgetInstance> findBySiteThemeConfig(SiteThemeConfig siteThemeConfig);
    
    List<WidgetInstance> findBySiteThemeConfigAndWidgetAreaId(SiteThemeConfig siteThemeConfig, String widgetAreaId);
    
    List<WidgetInstance> findBySiteThemeConfigAndWidgetAreaIdOrderByPosition(SiteThemeConfig siteThemeConfig, String widgetAreaId);
}
