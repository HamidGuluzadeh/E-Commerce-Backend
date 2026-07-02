<div align="center">

# 🛒 E-Commerce Backend (Microservices)

### Spring Boot, Kafka və Docker ilə Mikroservis Arxitekturası

</div>

---

## 📖 Ümumi Baxış

**E-Commerce Backend** müasir mikroservis arxitekturası prinsipləri əsasında qurulmuş e-ticarət sistemidir. Layihə **Java 21** və **Spring Boot** istifadə edilərək yaradılmışdır və xidmətlər arası asinxron əlaqə üçün **Apache Kafka**, məlumatların saxlanılması üçün isə **PostgreSQL** verilənlər bazalarından istifadə edir. Bütün sistem **Docker Compose** vasitəsilə asanlıqla idarə oluna bilər.

Sistem rol əsaslı girişə nəzarət, JWT autentifikasiyası (refresh tokenlərlə), xarici ödəniş inteqrasiyası (Azericard) və event-driven (hadisəyə əsaslanan) bildiriş mexanizmini dəstəkləyir.

---

## ✨ Xüsusiyyətlər (Mikroservislər)

### 🔐 User Management Microservice (İstifadəçi İdarəetməsi)
- **Access və Refresh Tokenləri** ilə JWT əsaslı autentifikasiya.
- İstifadəçi qeydiyyatı və təhlükəsiz giriş (BCrypt ilə şifrələmə).
- Rol əsaslı girişə nəzarət (Məsələn: `ADMIN`, `SELLER`, `CUSTOMER`).
- API təhlükəsizliyi üçün xüsusi `AuthenticationFilter`.

### 📦 Product Microservice (Məhsul İdarəetməsi)
- Məhsulların və kateqoriyaların əlavə edilməsi, yenilənməsi və idarə olunması.
- Satıcı (Seller) paneli və məhsul sahibliyi yoxlanışı.
- Digər servislərlə (Payment) əlaqə üçün `Feign Client` dəstəyi.
- **Liquibase** vasitəsilə baza miqrasiyaları.

### 💳 Payment Microservice (Ödəniş Sistemi)
- **Azericard** xarici ödəniş sistemi inteqrasiyası.
- Uğurlu ödənişdən sonra **Product-ms**-ə sorğu göndərərək məhsul stokunun (count) azaldılması.
- Ödəniş tarixçəsinin (Payment History) izlənilməsi.
- Uğurlu ödənişlər barədə məlumatların **Apache Kafka** vasitəsilə asinxron şəkildə yayımlanması.

### 🔔 Notification Microservice (Bildiriş Sistemi)
- Kafka Topic-lərini dinləyən (Consumer) xidmət.
- Ödəniş və ya digər önəmli hadisələr baş verdikdə istifadəçilərə **Email** bildirişlərinin göndərilməsi.

---

## 🏛️ Arxitektura axını (Nümunə Ödəniş Prosesi)

```text
[Müştəri] ──(Satın Alma)──▶ [Payment MS]
                              │
                              ├─(Sinxron)─▶ [Product MS] (Stoku Yoxla/Azalt)
                              │
                              ├─(Sinxron)─▶ [Azericard] (Ödənişi İcra Et)
                              │
                              └─(Asinxron Kafka Event)──▶ [Notification MS] ──▶ (Email Göndərilir)

---

## 🐳 Docker Konfiqurasiyası

Layihə `docker-compose.yml` vasitəsilə orkestrasiya olunur. Mikroservislər və asılılıqlar:

```yaml
services:
  postgres:    # PostgreSQL (Hər MS üçün ayrı schema/db) - Port 5432
  zookeeper:   # Kafka üçün Zookeeper
  kafka:       # Apache Kafka Message Broker
  user-ms:     # Port 8081
  product-ms:  # Port 8082
  payment-ms:  # Port 8083
  notify-ms:   # Port 8084

---

## 🗃️ Verilənlər Bazası və Texnologiyalar

- **Verilənlər Bazası:** PostgreSQL
- **ORM və Miqrasiya:** Spring Data JPA, Liquibase
- **Message Broker:** Apache Kafka
- **API Sənədləşdirmə:** Swagger / OpenAPI
- **Qurulma Aləti:** Gradle

---

## 👨‍💻 Müəllif

<div align="center">

**Həmid Quluzadə**

</div>
