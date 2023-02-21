Requirement:
Java 17

Struktur directory:
.../erp/pom.xml
.../erp/starter-service
.../erp/user-dto
.../erp/user-service
.../erp/master-dto
.../erp/master-service
.../erp/web-service

compile per module:
1. masuk directory module: erp/master-service
2. jalankan: mvnw.cmd clean install
3. untuk web-service, jalankan: mvnw.cmd clean install -Plocal
4. untuk starter-service. Jika pertama kali compile, jalankan: mvnw.cmd clean install -DskipTests

compile semua module dan jalankan spring boot:
1. masuk directory erp
2. jalankan: mvnw.cmd clean install && mvnw.cmd spring-boot:run -Plocal -pl web-service

jalankan spring boot:
1. masuk ke directory erp/web-service
2. jalankan: mvnw.cmd spring-boot:run -Plocal

account testing:
username: admin-11 or user-12 or user-13 or user-14
password: admin-11 or pass-12 or pass-13 or pass-14

penggabungan ke web-service:
1. copy accounting-dto ke erp/
2. copy accounting-service ke erp/
3. edit pom.xml
   - add dependency: accounting-service
   - edit profile
4. edit resources/META-INF/validation.xml
5. edit pom.xml: erp/pom.xml
   - add module: accounting-dto dan accounting-service

TODO:
--- Run dari intellij / visual studio code ---
