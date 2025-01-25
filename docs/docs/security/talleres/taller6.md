---
sidebar_position: 1
---

# Taller 6

En este taller, implementaremos configuraciones básicas de Spring Security para el protocolo de ___basic authentication___. El objetivo es familiarizarnos con las bases del framework y cómo se gestiona la autenticación básica con Spring Security, de forma que funcione como se aprecia en la Figura 1.

![](../../../static/img/security/Taller%206/auth-basic.jpeg)

Figura 1. Proceso de autenticación básica. __Fuente:__ [Wallarm](https://www.wallarm.com/what/what-is-basic-authentication-all-you-need-to-know).

## 1. Adecuación del proyecto

Iniciaremos con la creación del proyecto en [Spring Initializr](https://start.spring.io) con las siguientes dependencias:

![](../../../static/img/security/Taller%206/adecuacion.png)

Figura 2. Generación del proyecto.

Primero, debemos configurar la conexión a una base de datos. La habilitaremos de la misma forma en como lo hemos hecho en los talleres anteriores. Una vez hecho esto, podremos corroborar el comportamiento base de Spring Security. Corremos el _proyecto vacío_ y abrimos en el navegador la ventana principal de nuestro proyecto (`localhost:8080/`), lo que nos debería generar la siguiente vista.

![](../../../static/img/security/Taller%206/vista-inicial.png)

Figura 3. Vista inicial de autenticación.

Por default, habilitar Spring Security en un proyecto obligará la adopción del protocolo de autenticación básica, aún sin haber configurado usuarios ni contraseñas. Entonces, ¿cómo accedemos al servicio? Si miramos los logs, nos encontraremos con la contraseña de acceso:

![](../../../static/img/security/Taller%206/logs-iniciales.png)

Figura 3. Logs iniciales.

Si copiamos y pegamos las credenciales (`user` y la contraseña de los logs), podremos ver el mensaje de error 404 por default que habíamos esperado ver en un principio. 

En los capítulos siguientes, veremos cómo podemos definir usuarios y contraseñas propias para acceder a las APIs expuestas en nuestro microservicio.

## 2. Controlador base

Empezaremos por habilitar una API básica en nuestro proyecto. Puede ser algo como lo siguiente:

```java
@RestController
@RequestMapping("/api/saludo")
public class SaludarController {

    @GetMapping
    public String saludar() {
        return "Hola!";
    }
    
}
```

## 3. Generación de un usuario y contraseña

Para acceder al contenido de la API de la sección 2, nos ocurrirá el mismo escenario expuesto en la sección 1. Ahora, veremos cómo crear nuestro primer usuario y contraseña básico definido durant el runtime de ejecución. Para ello, habilitaremos una nueva clase de configuración que llamaremos: `SecurityConfiguration` dentro de una carpeta `config`.

```java
@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userService() {
        UserDetails Johana = User.withUsername("johana")
            .password(encoder().encode("123"))
            .authorities("read")
            .build();

        return new InMemoryUserDetailsManager(Johana);
    }

    @Bean
    public PasswordEncoder encoder () {
        return new BCryptPasswordEncoder();
    }

}
```

* __`@Configuration`:__ especifica que se trata de una clase de configuración.
* __`@Bean`:__ crea un nuevo bean que puede ser inyectado en otras capas o secciones del microservicio.
* __`UserDetailsService`:__ como se explicó en el capítulo de [Conceptos Básicos](../auth.md) (sección 1.), este bean de Spring Security es el que define las credenciales del usuario. Con esta configuración, estamos sobreescribiendo su funcionalidad original para definir al usuario `johana` con su contraseña `123`.
* __`PasswordEncoder`:__ de igual forma, también se explicó en el capítulo de _"Conceptos Básicos"_ que este bean es el que codifica y/o encripta las contraseñas. Con esta configuración, estamos redefiniendo su lógica para que emplee el codificador bcrypt.

Al establecer esta configuración, podremos acceder a las APIs con las siguientes credenciales:

* Usuario: __johana__
* Contraseña: __123__

## 4. Usuarios y contraseñas en BD

De forma similar, podemos ajustar nuestro algoritmo para que tome usuarios y contraseñas de una base de datos. En ese sentido, debemos crear un modelo de usuario, un repository de Spring Data JPA y un servicio para que obtenga usuarios existentes y permita la creación de nuevos usuarios.

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

<Tabs>
    <TabItem value="modelo" label="Modelo" default>
```java
@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long usuarioId;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;
}
```
    </TabItem>
    <TabItem value="repository" label="Repository" default>
```java
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByUsername(String username);

}
```
    </TabItem>
    <TabItem value="service" label="Servicio" default>
```java
@Service
public class UsuarioService implements UserDetailsService {

    private UsuarioRepository repository;

    public UsuarioService (UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario crearUsuario(Usuario usuario) {
        return this.repository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByUsername(username).get();
    }
}
```
    </TabItem>
    <TabItem value="config" label="Configuración de seguridad" default>
```java
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder encoder () {
        return new BCryptPasswordEncoder();
    }

}
```
    </TabItem>
</Tabs>

De esta forma, ya tenemos como persistir a nuestros usuarios en una base de datos. 

__NOTA:__ es importante que las contraseñas estén codificadas con `bcrypt` al crear nuevos usuarios. Para ello, podemos utilizar __Apache HTTP Server utilities__, que debes descargar e instalar. Por ejemplo, si queremos que nuestro usuario tenga la contraseña `ejemplo123`, debemos correr el siguiente comando:

```bash
htpasswd -bnBC 10 "" "ejemplo123"
```

Lo que nos dará como respuesta:

```bash
$2y$10$jbdoNkiIYQ73yZP2IUocjOJsn6Ax9Uu.S6regLrexRDohDTSghtye
```

Esta última es la contraseña que debemos almacenar en la base de datos para el usuario que querramos crear.