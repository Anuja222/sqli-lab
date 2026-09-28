# SQL Injection Lab

A deliberately small local Spring Boot + MySQL app for learning the difference between unsafe SQL string concatenation and a parameterized query.

## File structure

```text
sqli-lab/
├── database/setup.sql
├── pom.xml
├── README.md
└── src/main/
    ├── java/com/example/sqli/
    │   ├── LoginController.java
    │   ├── LoginService.java
    │   └── SqliLabApplication.java
    └── resources/
        ├── application.properties
        └── templates/index.html
```

## Setup

1. Start your local MySQL server.
2. Edit `src/main/resources/application.properties` and replace `YOUR_MYSQL_PASSWORD`.
3. Create the database and sample users:

```bash
mysql -u root -p < database/setup.sql
```

On Windows PowerShell, the same command works if `mysql` is on your PATH. You can also paste `database/setup.sql` into MySQL Workbench.

4. Start the app:

```bash
mvn spring-boot:run
```

5. Open http://localhost:8080.

## API commands

Unsafe login:

```bash
curl -X POST "http://localhost:8080/login" -d "username=alice&password=alice123"
```

Secure login:

```bash
curl -X POST "http://localhost:8080/login-secure" -d "username=alice&password=alice123"
```

## SQL injection demonstration

For the unsafe endpoint, use this username and any password:

```text
' OR '1'='1' -- 
```

The unsafe code creates SQL like this:

```sql
SELECT id, username FROM users
WHERE username = '' OR '1'='1' -- '
AND password = 'anything'
```

`'` closes the original username string. `OR '1'='1'` is always true. `-- ` starts a MySQL comment, so the password check is ignored. The query can therefore return a user even without a valid password.

The secure endpoint sends this SQL shape:

```sql
SELECT id, username FROM users WHERE username = ? AND password = ?
```

Spring JDBC sends the username and password separately as parameter values. The input is never reinterpreted as SQL syntax, so quotes, `OR`, and comments remain ordinary text. The secure endpoint still returns the SQL shape for learning, but the actual values are bound through a PreparedStatement.

This application intentionally stores plaintext passwords and exposes the unsafe endpoint. Do not deploy it or reuse this authentication code outside this local lab.
