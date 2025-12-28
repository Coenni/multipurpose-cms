-- Sites (multi-tenant support)
CREATE TABLE sites (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    domain VARCHAR(255) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Themes
CREATE TABLE themes (
    slug VARCHAR(100) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    version VARCHAR(50),
    author VARCHAR(255),
    description TEXT,
    config_json TEXT,
    screenshot_url VARCHAR(500),
    download_url VARCHAR(500),
    is_active BOOLEAN DEFAULT FALSE,
    is_installed BOOLEAN DEFAULT FALSE,
    installed_at TIMESTAMP
);

-- Plugins
CREATE TABLE plugins (
    slug VARCHAR(100) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    version VARCHAR(50),
    author VARCHAR(255),
    category VARCHAR(100),
    description TEXT,
    config_json TEXT,
    is_active BOOLEAN DEFAULT FALSE,
    is_installed BOOLEAN DEFAULT FALSE,
    installed_at TIMESTAMP
);

-- Site Theme Configuration
CREATE TABLE site_theme_configs (
    id BIGSERIAL PRIMARY KEY,
    site_id BIGINT REFERENCES sites(id) ON DELETE CASCADE,
    theme_slug VARCHAR(100) REFERENCES themes(slug) ON DELETE SET NULL,
    customizations TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Base Pages (using inheritance)
CREATE TABLE pages (
    id BIGSERIAL PRIMARY KEY,
    page_type VARCHAR(50) NOT NULL,
    site_id BIGINT REFERENCES sites(id) ON DELETE CASCADE,
    slug VARCHAR(255) NOT NULL,
    title VARCHAR(500) NOT NULL,
    content TEXT,
    layout VARCHAR(100),
    is_published BOOLEAN DEFAULT FALSE,
    
    -- SEO fields (embedded)
    meta_title VARCHAR(255),
    meta_description TEXT,
    meta_keywords VARCHAR(500),
    og_image VARCHAR(500),
    canonical_url VARCHAR(500),
    schema_json TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    UNIQUE(site_id, slug)
);

-- Posts
CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    site_id BIGINT REFERENCES sites(id) ON DELETE CASCADE,
    title VARCHAR(500) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    content TEXT,
    excerpt TEXT,
    featured_image VARCHAR(500),
    author_id BIGINT,
    status VARCHAR(50) DEFAULT 'draft',
    publish_date TIMESTAMP,
    
    -- SEO fields
    meta_title VARCHAR(255),
    meta_description TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    UNIQUE(site_id, slug)
);

-- Tags
CREATE TABLE tags (
    id BIGSERIAL PRIMARY KEY,
    site_id BIGINT REFERENCES sites(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(100) NOT NULL,
    UNIQUE(site_id, slug)
);

-- Post Tags (many-to-many)
CREATE TABLE post_tags (
    post_id BIGINT REFERENCES posts(id) ON DELETE CASCADE,
    tag_id BIGINT REFERENCES tags(id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, tag_id)
);

-- Categories
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    site_id BIGINT REFERENCES sites(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(100) NOT NULL,
    parent_id BIGINT REFERENCES categories(id) ON DELETE SET NULL,
    UNIQUE(site_id, slug)
);

-- Post Categories
CREATE TABLE post_categories (
    post_id BIGINT REFERENCES posts(id) ON DELETE CASCADE,
    category_id BIGINT REFERENCES categories(id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, category_id)
);

-- Products (for e-commerce)
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    site_id BIGINT REFERENCES sites(id) ON DELETE CASCADE,
    name VARCHAR(500) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2),
    sale_price DECIMAL(10, 2),
    sku VARCHAR(100),
    stock_quantity INT DEFAULT 0,
    images TEXT,
    is_published BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(site_id, slug)
);

-- Media Library
CREATE TABLE media (
    id BIGSERIAL PRIMARY KEY,
    site_id BIGINT REFERENCES sites(id) ON DELETE CASCADE,
    filename VARCHAR(500) NOT NULL,
    file_path VARCHAR(1000) NOT NULL,
    file_url VARCHAR(1000) NOT NULL,
    mime_type VARCHAR(100),
    file_size BIGINT,
    alt_text VARCHAR(500),
    uploaded_by BIGINT,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Widget Instances
CREATE TABLE widget_instances (
    id BIGSERIAL PRIMARY KEY,
    site_theme_config_id BIGINT REFERENCES site_theme_configs(id) ON DELETE CASCADE,
    plugin_slug VARCHAR(100) REFERENCES plugins(slug) ON DELETE CASCADE,
    widget_area_id VARCHAR(100),
    position INT DEFAULT 0,
    settings TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Menus
CREATE TABLE menus (
    id BIGSERIAL PRIMARY KEY,
    site_id BIGINT REFERENCES sites(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(100),
    items TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX idx_pages_site_slug ON pages(site_id, slug);
CREATE INDEX idx_posts_site_slug ON posts(site_id, slug);
CREATE INDEX idx_posts_status ON posts(status);
CREATE INDEX idx_products_site_slug ON products(site_id, slug);
CREATE INDEX idx_widget_instances_area ON widget_instances(widget_area_id, position);
CREATE INDEX idx_media_site ON media(site_id);
CREATE INDEX idx_tags_site ON tags(site_id);
CREATE INDEX idx_categories_site ON categories(site_id);

-- Insert a default site
INSERT INTO sites (name, domain) VALUES ('Default Site', 'localhost');
