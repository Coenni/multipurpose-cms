package com.cms.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("BLOG")
@Data
@EqualsAndHashCode(callSuper = true)
public class BlogPage extends BasePage {
    // Blog page specific fields can be added here if needed
}
