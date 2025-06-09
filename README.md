## API - GlobalLogicTest

# 📦 Clonar y Ejecutar el Proyecto

    git clone https://github.com/FelipeHuaiqui/GlobalLogicTest.git
    cd al repositorio y abrir con algun IDE con java
    correr el GlobalLogicApplication
    
📚 Requisitos

    Java 8 
    Puerto por defecto: 8081
    Para verificar bd h2 - Url en el navegador: http://localhost:8081/h2-console
     Formulario:
      -  Driver Class: org.h2.Driver
      -  JDBC URL: jdbc:h2:file:./data/test          
      -  User Name: sa
      -  Password:

📚 Caracteristicas especificas de java 8 u 11

    Capa service - (UsersServiceImpl)
    -Optional: linea 40
    -Stream: linea 81, 111
    -Collectors: linea 83, 113
    -LocalDateTime: linea 77, 78

🔐 Endpoints

    - Usa /sign-up para registrar usuario : POST - http://localhost:8081/sign-up
    - Usa /login para obtener un token y la informacion adicional (contraseña viene encriptada): GET - http://localhost:8081/login
        - token en postman, ve al apartado Authorization: 
        -selecciona Bearer Token: agrega el token obtenido del sign-up, o del login nuevo dependiendo del caso
        
    Se encuentra disponible la coleccion postman para llegar e importar   
    
📂 Estructura del Proyecto

    configuration/
    controller/
    repository/
    service/
    exception/
    utils/

## 📦 Diagrama de componentes

![digrama de componentes](https://github.com/user-attachments/assets/c74ab449-566d-4c83-b707-d6702c2d8ed1)

## 📦 Diagramas de secuencia:

## DS - SIGNUP
![ds - signUp](https://github.com/user-attachments/assets/95fb6304-781d-4acd-91af-9c0f30768910)

## DS - LOGIN
![ds - login](https://github.com/user-attachments/assets/25617822-dbf4-42a3-8c08-819460f7765b)
