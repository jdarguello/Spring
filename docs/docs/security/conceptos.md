---
sidebar_position: 2
---

# Conceptos básicos

En su forma más elemental, Spring Security funciona como un filtro. El microservicio recibe las credenciales de un usuario, valida si son correctas, ya sea contra en una base de datos, un caché o contra un servicio de autenticación externo. Luego, revisa si el usuario tiene los permisos para realizar la acción que solicita y, de tenerlos, permite continuar con el flujo lógico de negocio. Si no los tiene, o las credenciales (usuario/contraseña) no son válidas, deniega el acceso. Lo anterior, se puede apreciar de forma gráfica en la Figura 1.

![](../../static/img/security/intro/authentication.webp)

Figura 1. Lógica de autenticación. __Fuente:__ [Cloudflare](https://www.cloudflare.com/learning/access-management/what-is-authentication/)

En el presente capítulo, aprenderemos sobre cómo se establecen estas configuraciones y la metodología básica de trabajo en Spring Security tomando como ejemplo el protocolo de __basic authentication__.

## 1. Protocolo de Autenticación

El proceso de autenticación se divide en dos secciones principales: manejo de _usuarios_ y de _contraseñas_. A continuación, podremos observar la configuración base definida en un proyecto con Spring Security.

![](../../static/img/security/intro//metodologia-base.png)

Figura 2. Flujo básico funcional con Spring Security. __Fuente:__ Spilca, L. _"Spring Security in Action"_. Second Edition. O'Reilley.

Como se aprecia en la Figura 2, el proceso inicia con el _request_ de un usuario (en donde se envían sus credenciales). Allí, entran en acción diferentes componentes pre-configurados por Spring Security, cada uno descrito a continuación:

1. El `Authentication Filter` delega el proceso de autenticación al `Authentication Manager` y, basado en su respuesta, configura el _security context_.
2. El `Authentication Manager` usa al `Authentication Provider` para realizar la autenticación. Se emplea de esta forma porque el proveedor de autenticación puede ser interno (creado por nosotros) o externo (por ejemplo: Google, GitHub, etc).
3. Por su lado, el `Authentication Provider` ejecuta la lógica de autenticación.
4. El `User Details Service` obtiene las credenciales del usuario, ya sea desde una base de datos, un caché o un proveedor externo.
5. El `Password Encoder` se encarga de la codificación y/o encriptamiento de la contraseña. Se emplea ya sea para almacenar una contraseña o para desencriptarla.
6. Con el usuario y la contraseña (desencriptada), se procede a realizar la comparación y concluir si el usuario es verídico; acorde a la lógica de autenticación.
7. Una vez validada y aprobada, el _security context_ almacena temporalmente las credenciales para la ejecución de los flujos del negocio; es decir, hasta que la aplicación de la respuesta esperada por el usuario.

Para lograr el proceso de autenticación descrito en la Figura 2, Spring Security requiere que el cliente envíe las credenciales del usuario (username/password) a través del `Authorization` header. En el valor del header, el cliente debe añadir el valor `Basic`, seguido del usuario y contraseña, codificados con `Base64` y separados por `:`. 

### 1.1. Manejo de Usuarios

#### 1.2 Entendiendo `UserDetails`

Spring Security trae consigo una interfaz base conocida como `UserDetails`, que consiste en lo siguiente:

```java
public interface UserDetails extends Serializable {
  String getUsername();                                        ①
  String getPassword();
  Collection<? extends GrantedAuthority> getAuthorities();     ②
  boolean isAccountNonExpired();                               ③
  boolean isAccountNonLocked();
  boolean isCredentialsNonExpired();
  boolean isEnabled();
}
```

1.  Contiene las credenciales del usuario.
2.  Define los roles de nuestros usuarios.
3.  Establece posibles configuraciones que bloquean al usuario, para evitar problemas de suplantación de identidad en el futuro.

### 1.2. Manejo de Contraseñas

## 2. Protocolo de Autorización

### 2.1. Permisos

### 2.2. Role-Based Access Control - RBAC

