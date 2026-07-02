markdown_content = """<div align="center">

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

Code outputFile generated successfully at /mnt/data/E-Commerce-README.md

```text
[Müştəri] ──(Satın Alma)──▶ [Payment MS]
                              │
                              ├─(Sinxron)─▶ [Product MS] (Stoku Yoxla/Azalt)
                              │
                              ├─(Sinxron)─▶ [Azericard] (Ödənişi İcra Et)
                              │
                              └─(Asinxron Kafka Event)──▶ [Notification MS] ──▶ (Email Göndərilir)
🐳 Docker KonfiqurasiyasıLayihə docker-compose.yml vasitəsilə orkestrasiya olunur. Mikroservislər və asılılıqlar:YAMLservices:
  postgres:    # PostgreSQL (Hər MS üçün ayrı schema/db) - Port 5432
  zookeeper:   # Kafka üçün Zookeeper
  kafka:       # Apache Kafka Message Broker
  user-ms:     # Port 8081
  product-ms:  # Port 8082
  payment-ms:  # Port 8083
  notify-ms:   # Port 8084
Tətbiqlər çoxmərhələli (multi-stage) Dockerfile istifadə edir:Stage 1: Gradle ilə JAR faylının qurulması (build).Stage 2: Yüngül JRE mühitində JAR-ın işə salınması (daha kiçik imic üçün).📁 Mühit Dəyişənləri (Environment Variables)Hər mikroservis üçün application.yaml fayllarında konfiqurasiyalar mövcuddur. Docker mühitində işlədərkən adətən aşağıdakı kimi dəyişənlər tələb olunur:DəyişənTəsvirNümunə DəyərSPRING_DATASOURCE_URLBaza bağlantı linkijdbc:postgresql://postgres:5432/product_dbKAFKA_BOOTSTRAP_SERVERSKafka broker adresikafka:9092JWT_SECRET_KEYJWT şifrələmə açarıyour_super_secret_key...⚠️ İstehsal (production) mühitində bu dəyərləri daha təhlükəsiz yollarla (.env və ya Docker secrets) təqdim etmək məsləhətdir.🗃️ Verilənlər Bazası və TexnologiyalarVerilənlər Bazası: PostgreSQLORM və Miqrasiya: Spring Data JPA, LiquibaseMessage Broker: Apache KafkaAPI Sənədləşdirmə: Swagger / OpenAPIQurulma Aləti: Gradle👨‍💻 MüəllifHəmid Quluzadə Junior Level Backend Developer 🚀file_path = "/mnt/data/E-Commerce-README.md"with open(file_path, "w", encoding="utf-8") as f:f.write(markdown_content)print(f"File generated successfully at {file_path}")
