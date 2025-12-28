# Multipurpose CMS

A powerful, flexible content management system built with **Spring Boot 3.x** and **SvelteKit**, designed for portfolios, blogs, e-commerce, and various business websites.

## 🚀 Features

- ✅ **Multi-tenant Support** - Host multiple sites from a single instance
- ✅ **Theme System** - Customizable themes with easy installation and activation
- ✅ **Plugin/Widget System** - Extend functionality with modular widgets
- ✅ **SEO Optimization** - Built-in SEO metadata for all content types
- ✅ **Content Management** - Pages, posts, products, and media library
- ✅ **E-commerce Ready** - Product management with pricing and inventory
- ✅ **RESTful API** - Clean, well-documented REST APIs
- ✅ **Responsive Design** - Mobile-first design with TailwindCSS
- ✅ **Database Migrations** - Flyway for version-controlled schema changes

## 📋 Table of Contents

- [Technology Stack](#technology-stack)
- [Quick Start](#quick-start)
- [Project Structure](#project-structure)
- [Backend Setup](#backend-setup)
- [Frontend Setup](#frontend-setup)
- [Docker Setup](#docker-setup)
- [API Documentation](#api-documentation)
- [Creating Themes](#creating-themes)
- [Creating Widgets](#creating-widgets)
- [Contributing](#contributing)

## 🛠️ Technology Stack

### Backend
- **Spring Boot 3.2.0** (Java 17+)
- **Spring Data JPA** with Hibernate
- **Spring Security** for authentication
- **PostgreSQL** as primary database
- **Maven** for build management
- **Flyway** for database migrations

### Frontend
- **SvelteKit** - Modern, reactive framework
- **TailwindCSS** - Utility-first CSS framework
- **Vite** - Fast build tool
- **Axios** - HTTP client

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Node.js 18 or higher
- PostgreSQL 15 or higher
- Docker & Docker Compose (optional)

### Using Docker Compose (Recommended)

The easiest way to get started:

```bash
# Clone the repository
git clone https://github.com/Coenni/multipurpose-cms.git
cd multipurpose-cms

# Start all services with Docker Compose
docker-compose up -d

# Access the application
# Frontend: http://localhost:5173
# Backend API: http://localhost:8080
```

### Manual Setup

#### 1. Setup PostgreSQL Database

```sql
CREATE DATABASE cms_db;
CREATE USER cms_user WITH PASSWORD 'cms_password';
GRANT ALL PRIVILEGES ON DATABASE cms_db TO cms_user;
```

#### 2. Backend Setup

```bash
cd backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

#### 3. Frontend Setup

```bash
cd frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

The frontend will start on `http://localhost:5173`

## 📁 Project Structure

```
multipurpose-cms/
├── backend/                    # Spring Boot backend
│   ├── src/main/
│   │   ├── java/com/cms/
│   │   │   ├── config/        # Configuration classes
│   │   │   ├── controller/    # REST controllers
│   │   │   ├── service/       # Business logic
│   │   │   ├── repository/    # Data access
│   │   │   ├── model/         # Entities and DTOs
│   │   │   ├── security/      # Security components
│   │   │   └── exception/     # Exception handling
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/  # Flyway migrations
│   └── pom.xml
│
├── frontend/                   # SvelteKit frontend
│   ├── src/
│   │   ├── lib/
│   │   │   ├── components/    # Svelte components
│   │   │   ├── services/      # API services
│   │   │   └── stores/        # State management
│   │   └── routes/            # SvelteKit routes
│   ├── package.json
│   └── svelte.config.js
│
├── themes/                     # Theme templates
│   └── default/               # Default theme
│       ├── theme.json
│       ├── assets/
│       └── templates/
│
├── plugins/                    # Widgets and plugins
│   └── core-widgets/          # Core widget collection
│       ├── recent-posts/
│       ├── text-widget/
│       ├── image-widget/
│       ├── social-links/
│       └── search/
│
└── docker-compose.yml
```

## 🔧 Backend Setup

### Configuration

Edit `backend/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/cms_db
    username: cms_user
    password: cms_password
  
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false

storage:
  type: local
  local:
    path: ./storage
```

### Building

```bash
cd backend
mvn clean package
```

### Running Tests

```bash
mvn test
```

## 🎨 Frontend Setup

### Configuration

Create `.env` file in the frontend directory:

```env
VITE_API_URL=http://localhost:8080
```

### Development

```bash
cd frontend
npm run dev
```

### Building for Production

```bash
npm run build
```

## 🐳 Docker Setup

### Build Images

```bash
# Build backend
cd backend
docker build -t cms-backend .

# Build frontend
cd ../frontend
docker build -t cms-frontend .
```

### Run with Docker Compose

```bash
docker-compose up -d
```

### View Logs

```bash
docker-compose logs -f
```

### Stop Services

```bash
docker-compose down
```

## 📚 API Documentation

### Themes API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/themes` | List all themes |
| GET | `/api/themes/{slug}` | Get theme by slug |
| GET | `/api/themes/active` | Get active theme |
| POST | `/api/themes` | Create theme |
| PUT | `/api/themes/{slug}` | Update theme |
| POST | `/api/themes/{slug}/install` | Install theme |
| PUT | `/api/themes/{slug}/activate` | Activate theme |
| DELETE | `/api/themes/{slug}` | Delete theme |

### Plugins API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/plugins` | List all plugins |
| GET | `/api/plugins/{slug}` | Get plugin by slug |
| GET | `/api/plugins/active` | Get active plugins |
| POST | `/api/plugins` | Create plugin |
| POST | `/api/plugins/{slug}/install` | Install plugin |
| PUT | `/api/plugins/{slug}/activate` | Toggle activation |
| DELETE | `/api/plugins/{slug}` | Delete plugin |

### Pages API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/pages?siteId={id}` | List all pages |
| GET | `/api/pages/{id}` | Get page by ID |
| GET | `/api/pages/slug/{slug}?siteId={id}` | Get page by slug |
| POST | `/api/pages` | Create page |
| PUT | `/api/pages/{id}` | Update page |
| DELETE | `/api/pages/{id}` | Delete page |

### Posts API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/posts?siteId={id}&page={n}&size={n}` | List posts (paginated) |
| GET | `/api/posts/{id}` | Get post by ID |
| POST | `/api/posts` | Create post |
| PUT | `/api/posts/{id}` | Update post |
| DELETE | `/api/posts/{id}` | Delete post |

## 🎨 Creating Themes

### Theme Structure

```
themes/my-theme/
├── theme.json              # Theme configuration
├── screenshot.png          # Theme preview (optional)
├── assets/
│   ├── css/
│   │   └── theme.css      # Theme styles
│   └── js/
│       └── theme.js       # Theme scripts
└── templates/
    ├── layouts/
    ├── pages/
    └── partials/
```

### theme.json

```json
{
  "name": "My Theme",
  "slug": "my-theme",
  "version": "1.0.0",
  "author": "Your Name",
  "description": "A beautiful theme",
  "supports": {
    "widgets": true,
    "menus": true,
    "ecommerce": true
  },
  "widgetAreas": [
    {
      "id": "sidebar-primary",
      "name": "Primary Sidebar"
    }
  ],
  "customization": {
    "colors": {
      "primary": {
        "default": "#3B82F6",
        "label": "Primary Color"
      }
    }
  }
}
```

## 🧩 Creating Widgets

### Widget Structure

```
plugins/core-widgets/my-widget/
├── widget.json             # Widget configuration
├── Widget.svelte          # Svelte component
└── widget.css             # Widget styles
```

### widget.json

```json
{
  "name": "My Widget",
  "slug": "my-widget",
  "version": "1.0.0",
  "category": "content",
  "description": "Widget description",
  "settings": {
    "title": {
      "type": "text",
      "label": "Title",
      "default": "My Widget"
    }
  }
}
```

### Widget.svelte

```svelte
<script>
  export let settings = {
    title: 'My Widget'
  };
</script>

<div class="widget my-widget">
  <h3 class="widget-title">{settings.title}</h3>
  <div class="widget-content">
    <!-- Widget content here -->
  </div>
</div>
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License.

## 👥 Authors

- **CMS Team** - Initial work

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- SvelteKit team for the amazing frontend framework
- TailwindCSS for the utility-first CSS framework