# 🛡️ La Nostra Famiglia: RESTful Management API (ABP Final)
# 🛡️ La Nostra Famiglia: API REST de Gestión (Final ABP)

## 📋 Project Overview | Resumen del Proyecto

A robust **REST API** designed to manage the internal data of a fictional organization ("La Nostra Famiglia"), integrated with a remote Linux-based MySQL server. This system handles complex administrative tasks such as member tracking, rank hierarchies, armory inventory, and secure service orders.

Una **API REST** robusta diseñada para gestionar los datos internos de una organización ficticia ("La Nostra Famiglia"), integrada con un servidor remoto MySQL basado en Linux. Este sistema maneja tareas administrativas complejas como el seguimiento de miembros, jerarquías de rangos, inventario de armería y pedidos de servicios seguros.

---

## 👥 Meet the Team | Integrantes

- **Miriam Hernández** – [@kuroimichan4](https://github.com/kuroimichan4)
- **Daniel García** – [@DarksAces](https://github.com/DarksAces)
- **Xavier Ortíz** – [@Erpiolo](https://github.com/Erpiolo)
- **Javier Villena** – [@sudoJavi](https://github.com/sudoJavi)

---

## ✨ Key Features | Características Clave

- **Full CRUD Support:** Complete Create, Read, Update, and Delete operations for all business entities. | *Soporte CRUD Completo: Operaciones completas para todas las entidades de negocio.*
- **Complex Relationships:** Advanced data modeling using `ENUM` and many-to-one relationships for order management (Members vs. Contacts). | *Relaciones Complejas: Modelado de datos avanzado usando `ENUM` y relaciones de muchos-a-uno.*
- **Linux Deployment:** Connected to a remote production environment on a dedicated Linux server. | *Despliegue en Linux: Conectado a un entorno de producción remoto en un servidor Linux dedicado.*
- **Web Integration:** Specifically designed as a backend provider for a corporate web portal. | *Integración Web: Diseñada específicamente como proveedor backend para un portal web corporativo.*

---

## 🛠️ Tech Stack | Tecnologías

- **Language:** Java 17
- **Framework:** Spring Boot (Spring Data JPA, Hibernate)
- **Database:** MySQL
- **Developer Tools:** IntelliJ IDEA, Lombok, Maven.

---

## 🌐 API Endpoints | Puntos de Acceso

### 📁 Services | Servicios
- `GET /lafamiglia/servicios`: List all services. | *Listar todos los servicios.*
- `POST /lafamiglia/servicios`: Create new service. | *Crear nuevo servicio.*
- `PUT /lafamiglia/servicios/{id}`: Update service. | *Actualizar servicio.*

### 🔫 Armory | Armas
- `GET /lafamiglia/armas`: Retrieve full armory inventory. | *Obtener inventario completo de armas.*
- `POST /lafamiglia/armas`: Register new weaponry. | *Registrar armamento nuevo.*

### 🧑‍🚀 Members | Miembros
- `GET /api/members`: Manage active organization personnel. | *Gestionar el personal activo de la organización.*
- `PATCH /api/members/{id}`: Update specific member metadata. | *Actualizar metatados específicos de un miembro.*

### 📦 Orders | Pedidos
- `GET /lafamiglia/pedidos`: Tracking and logistics for service orders. | *Seguimiento y logística para pedidos de servicio.*

---

## 🚀 Deployment | Despliegue

1.  Clone the repository. | *Clona el repositorio.*
2.  Configure remote MySQL credentials in `application.properties`. | *Configura las credenciales de MySQL remoto en `application.properties`.*
3.  Run the Spring Boot application: `./mvnw spring-boot:run`. | *Ejecuta la aplicación Spring Boot.*
4.  Access the local documentation at `http://localhost:8081/swagger-ui.html`. | *Accede a la documentación local.*

---

> [!NOTE]
> This project served as the **Final ABP Project** for the 1st year of DAM, achieving high marks in architecture and database integration.
 
