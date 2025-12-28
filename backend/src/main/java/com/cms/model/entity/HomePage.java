package com.cms.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("HOME")
@Data
@EqualsAndHashCode(callSuper = true)
public class HomePage extends BasePage {
    // Home page specific fields can be added here if needed
}
