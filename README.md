# RBAC权限管理系统演示

## 项目简介

这是一个基于角色的访问控制（Role-Based Access Control，RBAC）系统演示项目。该项目展示了如何使用Spring Boot实现一个完整的RBAC权限管理系统，包括用户管理、角色分配和权限控制。

## 技术栈

- Java 21
- Spring Boot 3.4.3
- Spring Security
- Spring Data JPA
- MySQL数据库
- Lombok
- Gradle

## 系统功能

- 用户管理：注册、登录、用户信息管理
- 角色管理：创建角色、分配角色、角色查询
- 权限管理：创建权限、分配权限、权限查询
- 访问控制：基于角色和权限的API访问控制

## 数据模型

系统实现了典型的RBAC模型，包含三个核心实体：

1. **用户(User)**
   - 基本属性：ID、用户名、邮箱、密码
   - 与角色是多对多关系

2. **角色(Role)**
   - 基本属性：ID、角色名称
   - 与用户和权限都是多对多关系

3. **权限(Permission)**
   - 基本属性：ID、权限名称
   - 与角色是多对多关系

## 安装指南

### 前置条件

- JDK 21
- MySQL 8.0+
- Gradle 8.0+

### 步骤

1. 克隆项目

   ```bash
   git clone https://github.com/yourusername/rbac-demo.git
   cd rbac-demo
   ```

2. 配置数据库

   在`src/main/resources/application.properties`或`application.yml`中配置数据库连接信息：

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/rbac_demo?useSSL=false&serverTimezone=UTC
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. 构建并运行

   ```bash
   ./gradlew bootRun
   ```

   或

   ```bash
   ./gradlew build
   java -jar build/libs/rbac-demo-0.0.1-SNAPSHOT.jar
   ```

4. 访问应用
   应用将在`http://localhost:8080`运行

## API接口

系统提供以下主要REST API接口：

- **用户相关**
  - `POST /api/auth/register` - 用户注册
  - `POST /api/auth/login` - 用户登录
  - `GET /api/users` - 获取用户列表
  - `GET /api/users/{id}` - 获取指定用户信息
  - `PUT /api/users/{userId}/roles` - 为用户分配角色

- **角色相关**
  - `GET /api/roles` - 获取角色列表
  - `POST /api/roles` - 创建新角色
  - `PUT /api/roles/{roleId}/permissions` - 为角色分配权限

- **权限相关**
  - `GET /api/permissions` - 获取权限列表
  - `POST /api/permissions` - 创建新权限

## 安全配置

系统使用Spring Security实现认证和授权，主要特点：

- 基于表单的登录认证
- BCrypt密码加密
- 记住我功能（有效期24小时）
- 基于角色的访问控制

## 项目结构

```plaintext
src/main/java/com/example/rbacdemo/
├── RbacDemoApplication.java      # 应用程序入口
├── config/                       # 配置文件
├── controller/                   # REST API控制器
├── model/                        # 数据模型
│   ├── User.java                 # 用户实体
│   ├── Role.java                 # 角色实体
│   └── Permission.java           # 权限实体
├── repository/                   # 数据访问层
├── service/                      # 业务逻辑层
└── security/                     # 安全相关配置
```

## 开发说明

1. 如果需要初始化数据，可以在`src/main/resources/data.sql`中添加SQL语句
2. 系统默认创建了一些基本角色和权限，可以在服务层中查看或修改
3. 开发环境中，可以使用H2内存数据库进行快速测试

### 从 maven 迁移 到 gradle

Maven和Gradle是Java生态系统中两个主流的构建工具，以下是从Maven迁移到Gradle时需要注意的主要区别：

#### 1. 构建文件对比

Maven使用XML格式的`pom.xml`，而Gradle使用Groovy或Kotlin DSL的`build.gradle`：

Maven (pom.xml):
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>3.4.3</version>
    </dependency>
</dependencies>
```

Gradle (build.gradle):
```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web:3.4.3'
}
```

#### 2. 依赖管理

Maven的依赖范围与Gradle的配置对应关系：
- compile -> implementation
- runtime -> runtimeOnly
- provided -> compileOnly
- test -> testImplementation

#### 3. 常用命令对比

| Maven命令 | Gradle命令 | 说明 |
|-----------|------------|------|
| mvn clean | gradle clean | 清理构建目录 |
| mvn compile | gradle compileJava | 编译源代码 |
| mvn test | gradle test | 运行测试 |
| mvn package | gradle build | 打包项目 |
| mvn install | gradle publishToMavenLocal | 安装到本地仓库 |

#### 4. 插件使用

Maven插件配置：
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
        <plugin>
            <groupId>io.spring.javaformat</groupId>
            <artifactId>spring-javaformat-maven-plugin</artifactId>
            <version>0.0.43</version>
        </plugin>
    </plugins>
</build>
```

Gradle插件配置：
```groovy
plugins {
    id 'org.springframework.boot' version '3.4.3'
    id 'io.spring.dependency-management' version '1.1.4'
    id 'io.spring.javaformat' version '0.0.43'
    id 'java'
}

// 可选：配置Java格式化任务
tasks.named('check').configure {
    dependsOn 'verifyFormat'
}
```

#### 5. 其他重要区别

- **构建性能**：Gradle通常比Maven更快，因为它支持增量构建和构建缓存
- **灵活性**：Gradle提供了更灵活的构建脚本，可以使用Groovy或Kotlin编写自定义任务
- **依赖解析**：Gradle的依赖解析更智能，可以自动处理依赖冲突
- **多项目构建**：Gradle对多项目构建的支持更好，配置更简洁

#### 6. 常用Gradle任务

```bash
# 列出项目中所有可用的任务
./gradlew tasks

# 构建项目
./gradlew build

# 运行应用（Spring Boot）
./gradlew bootRun

# 清理构建目录
./gradlew clean

# 运行测试
./gradlew test

# 生成项目报告
./gradlew projectReport
```

## 贡献指南

1. Fork本仓库
2. 创建您的功能分支 `git checkout -b feature/amazing-feature`
3. 提交您的更改 `git commit -m 'Add some amazing feature'`
4. 推送到分支 `git push origin feature/amazing-feature`
5. 开启一个Pull Request

## 许可证

[MIT](LICENSE)

## Docker 部署

本项目支持使用 Docker 进行部署。我们使用多阶段构建来优化镜像大小，并提供了 docker-compose 配置以便快速部署。系统包含两个主要服务：

1. 应用服务 (app)
2. MySQL 数据库服务 (mysql)

### 使用 Docker Compose 部署

1. 启动服务

```bash
docker-compose up -d
```

2. 查看日志

```bash
docker-compose logs -f
```

3. 停止服务

```bash
docker-compose down
```

4. 清理数据（慎用）

```bash
docker-compose down -v
```

### 环境变量配置

在 `docker-compose.yml` 中可以配置以下环境变量：

应用服务 (app) 环境变量：
- `SPRING_PROFILES_ACTIVE`: 激活的 Spring profile（默认：prod）
- `SPRING_DATASOURCE_URL`: 数据库连接 URL
- `SPRING_DATASOURCE_USERNAME`: 数据库用户名
- `SPRING_DATASOURCE_PASSWORD`: 数据库密码

MySQL 服务环境变量：
- `MYSQL_DATABASE`: 数据库名称
- `MYSQL_USER`: 数据库用户名
- `MYSQL_PASSWORD`: 数据库密码
- `MYSQL_ROOT_PASSWORD`: Root 用户密码

### 数据持久化

MySQL 数据通过 Docker volume 进行持久化存储：
- 数据文件：`mysql_data` volume
- 初始化脚本：`./docker/init.sql`

### 注意事项

1. 确保 Docker 和 Docker Compose 已正确安装
2. 生产环境部署时建议：
   - 修改默认端口
   - 修改默认数据库密码
   - 根据实际需求调整 JVM 参数和内存限制
   - 配置数据库备份策略
3. 首次启动时，系统会自动执行数据库初始化脚本
4. 可以通过 `docker-compose logs mysql` 查看数据库日志
5. 数据库服务启动可能需要一些时间，应用服务会自动等待数据库就绪
