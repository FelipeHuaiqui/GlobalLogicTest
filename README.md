API - GlobalLogicTest


📦 Clonar y Ejecutar el Proyecto

    git clone https://github.com/FelipeHuaiqui/GlobalLogicTest.git
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
