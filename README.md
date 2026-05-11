📝 Spring Boot 通訊錄管理系統 (CRUD with Auth)
這是一個基於 Spring Boot 3 框架開發的響應式通訊錄管理系統。專案整合了 Spring Security 進行身分驗證與權限控管，並利用 Spring Data JPA 結合邏輯完成動態關鍵字搜尋與無痛分頁處理。前端介面則使用 Thymeleaf 模板引擎與 Tailwind CSS 4.0 進行美化。

🛠️ 技術棧 (Tech Stack)
後端核心：Spring Boot 3.x

資料持久化：Spring Data JPA (Hibernate)

安全框架：Spring Security (BCrypt 密碼加密、自定義 UserDetailsService)

模板引擎：Thymeleaf (整合 Spring Security 標籤屬性)

前端 UI：Tailwind CSS v4.0 (CDN 載入)

欄位校驗：Jakarta Validation (JSR-380)

🌟 核心功能清單
使用者認證與授權 (Authentication & Authorization)

使用者註冊與密碼 BCrypt 強度加密。

自定義登入頁面 (/login) 與登出安全機制（自動清除 Session、Cookies）。

角色權限控管：一般登入用戶可查看、新增、編輯客戶資料；僅限 ADMIN 角色擁有刪除客戶的權限（前端刪除按鈕動態隱藏，後端路徑 /customer/*/delete 安全攔截）。

客戶資料管理 (CRUD)

新增/修改 (C/U)：共用 create.html 表單，透過有無 ID 智慧判斷操作行為。

欄位校驗：姓名、手機、電子郵件格式完整校驗。

唯一值檢查：儲存時後端將自動檢查電子郵件 (Email) 是否已被其他客戶註冊使用，並動態將錯誤注入表單中。

分頁與關鍵字搜尋 (Pagination & Search)

結合 JPA Pageable 實作後端分頁，預設每頁最多顯示 5 筆資料。

複合關鍵字查詢：支援單一輸入框同時模糊比對客戶的「姓氏」、「名稱」與「電子信箱」。

狀態保留：在搜尋結果中進行切換頁碼時，系統會自動保留當前的搜尋關鍵字，不遺失查詢上下文。

💾 資料庫設定
專案採用關聯式資料庫。請在你的 src/main/resources/application.properties 中，確保你已正確設定資料庫連線資訊。

若你使用預設的 MySQL 或 H2 Database，請參閱以下典型配置：

Properties
# JPA 設定
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# 資料庫連線配置 (請根據實際資料庫自行調整)
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
🚀 快速開始
1. 建立資料表與初始權限帳號
   由於系統採用 Spring Security 控制，你需要先在資料庫建立可登入的 User (使用者) 帳號，以及預裝的 Customer (客戶) 資料。

A. 寫入 2 筆測試帳號 (密碼皆為加密後的 123456)
請在你的資料庫工具中執行以下 SQL，手動寫入一個管理者與一個一般使用者：

SQL
-- 密碼為 $2a$10$Ushj9HqYkbe2mE06p/gUieH8r/rTj7I.v9jX1AetqK/T9G9O/tAuy (解密後為 123456)
INSERT INTO users (username, password, role) VALUES
('admin', '$2a$10$Ushj9HqYkbe2mE06p/gUieH8r/rTj7I.v9jX1AetqK/T9G9O/tAuy', 'ADMIN'),
('user', '$2a$10$Ushj9HqYkbe2mE06p/gUieH8r/rTj7I.v9jX1AetqK/T9G9O/tAuy', 'USER');
B. 寫入 20 筆客戶假資料 (測試分頁與搜尋)
SQL
INSERT INTO customer (first_name, last_name, email, phone) VALUES
('明', '王', 'wang.ming@example.com', '0912345678'),
('John', 'Doe', 'john.doe@example.com', '0987654321'),
('美玲', '林', 'lin.ml@example.com', '0922111333'),
('David', 'Smith', 'david.smith@example.com', '0933444555'),
('冠宇', '陳', 'chen.ky@example.com', '0955666777'),
('Emily', 'Brown', 'emily.brown@example.com', '0966777888'),
('家豪', '張', 'chang.ch@example.com', '0977888999'),
('Michael', 'Wilson', 'michael.w@example.com', '0911222333'),
('雅婷', '李', 'lee.yt@example.com', '0988999000'),
('Sarah', 'Miller', 'sarah.m@example.com', '0955123456'),
('建國', '黃', 'huang.ck@example.com', '0922987654'),
('James', 'Davis', 'james.davis@example.com', '0933111222'),
('淑芬', '吳', 'wu.sf@example.com', '0966222333'),
('Jessica', 'Taylor', 'jessica.t@example.com', '0977333444'),
('志明', '蔡', 'tsai.cm@example.com', '0911555666'),
('Robert', 'Anderson', 'robert.a@example.com', '0988444555'),
('麗華', '劉', 'liu.lh@example.com', '0922666777'),
('Linda', 'Thomas', 'linda.t@example.com', '0933777888'),
('俊傑', '楊', 'yang.cc@example.com', '0955888999'),
('William', 'Jackson', 'william.j@example.com', '0966999000');
2. 本機編譯與運行
   使用 IDE (如 IntelliJ IDEA) 直接點擊 CrudApplication 的運行按鈕，或是使用終端機指令啟動：

Bash
# 使用 Maven 啟動
./mvnw spring-boot:run
專案啟動成功後，在瀏覽器打開：
👉 http://localhost:8080/

🔒 測試指引與測試帳號
登入一般用戶（僅能檢視與新增編輯，無刪除權限）：

帳號：user

密碼：123456

登入管理員（擁有完整權限，包括刪除）：

帳號：admin

密碼：123456

📂 專案核心目錄結構
Plaintext
src/main/java/com/leoh/crud/
│
├── config/
│   └── SecurityConfig.java         # Spring Security 安全攔截配置與密碼加密 Bean
│
├── controller/
│   └── HomeController.java         # 首頁(搜尋分頁)、新增/修改/刪除的跳轉控制
│
├── model/
│   ├── Customer.java               # 客戶 Entity 與 JPA 表單驗證規則
│   └── User.java                   # 系統使用者帳號 Entity
│
├── respository/
│   ├── CustomerRepository.java     # 支援自訂關鍵字模糊查詢的分頁 Repository
│   └── UserRepository.java         # 支援 User 登入帳號查詢
│
└── service/
├── CustomerService.java        # 封裝分頁、搜尋、重複 Email 驗證與儲存邏輯
├── UserService.java            # 新增註冊用戶並密碼加密
└── CustomUserDetailsService.java # 自訂 Spring Security 載入帳號邏輯