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
5. El `Password Encoder` se encarga de la codificación de la contraseña. Se emplea ya sea para almacenar una contraseña o para desencriptarla.
6. Con el usuario y la contraseña (desencriptada), se procede a realizar la comparación y concluir si el usuario es verídico; acorde a la lógica de autenticación.
7. Una vez validada y aprobada, el _security context_ almacena temporalmente las credenciales para la ejecución de los flujos del negocio; es decir, hasta que la aplicación de la respuesta esperada por el usuario.

Para lograr el proceso de autenticación descrito en la Figura 2, Spring Security requiere que el cliente envíe las credenciales del usuario (username/password) a través del `Authorization` header. En el valor del header, el cliente debe añadir el valor `Basic`, seguido del usuario y contraseña, codificados con `Base64` y separados por `:`. 

### 1.1. Manejo de Usuarios

Iniciaremos nuestro recorrido con el manejo de usuarios. Como se aprecia en la Figura 1, existen diferentes interfaces (contratos) que definen la arquitectura base de autenticación y se pueden apreciar en la Figura 2.

![](../../static/img/security/conceptos/menajo-usuarios.png)

Figura 3. Dependencias base para el manejo de usuarios. __Fuente:__ Spilca, L. _"Spring Security in Action"_. Second Edition. O'Reilley.

A continuación, se explican cada uno de estos contratos.

* __`GrantedAuthority`:__ hace referencia a los roles adoptados por el usuario. Cada uno de ellos puede tener múltiples _permisos_ que definen la forma en cómo interactúa un usuario con la aplicación y qué acciones puede ejecutar. Se explica en detalle en el __capítulo 2__.
* __`UserDetails`:__ define el modelo base de usuarios.
* __`UserDetailsService`:__ especifica la forma en cómo se carga la información de un usuario.
* __`UserDetailsManager`:__ especifica las operaciones de adición, modificación o eliminación de un usuario.


#### 1.1.1 Entendiendo `UserDetails`

Esta interfaz consiste en lo siguiente:

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
3.  Establece posibles configuraciones que restringen o bloquean al usuario, para evitar problemas de suplantación de identidad en el futuro. Son argumentos opcionales y dependen de la lógica de negocio que se requiere implementar. Si alguno de ellos retorna `false`, el usuario no podrá interactuar con la aplicación y responderá con HTTP 401.

#### 1.1.2. ¿Cómo funciona el `UserDetailsService`?

Este contrato únicamente informa la manera de cargar la información de un usuario mediante `username`, como se aprecia a continuación. 

```java
public interface UserDetailsService {
  public UserDetails loadUserByUsername(String username) 
    throws UsernameNotFoundException;
}
```

Se puede implementar en un servicio para especificar la lógica de carga del usuario, que puede variar si proviene de una base de datos relacional, NoSQL, caché o un microservicio externo.

#### 1.1.3. Implementando `UserDetailsManager`

Como se aprecia en la Figura 3, este contrato __extiende__ las funcionalidades definidas por el `UserDetailsService`. Permite especificar la forma en cómo se crean, actualizan o eliminan usuarios, entre otras funcionalidades. 

```java
public interface UserDetailsManager extends UserDetailsService {
  void createUser(UserDetails user);
  void updateUser(UserDetails user);
  void deleteUser(String username);
  void changePassword(String oldPassword, String newPassword);
  boolean userExists(String username);
}
```

Este contrato se puede implementar en otro servicio customizado que especifique las funcionalidades de estos métodos para garantizar la persistencia de los datos.

### 1.2. Manejo de Contraseñas

El manejo de contraseñas es una parte esencial dentro del flujo de autenticación. En la presente sección, hablaremos del contrato de `PasswordEncoder` y las herramientas ofrecidas en el módulo de Spring Security Crypto (SSCM). 

#### 1.2.1 Uso de `PasswordEncoder`

De la misma forma en como observarmos en la sección 1.1., también es posible customizar la forma en cómo se codifica o encripta una contraseña. Paso fundamental, ya que, bajo ninguna circunstancia, se debe almacenar una contraseña en texto plano. Empezaremos por entender las bases del contrato:

```java
public interface PasswordEncoder {

  String encode(CharSequence rawPassword);

  boolean matches(CharSequence rawPassword, String encodedPassword);

  default boolean upgradeEncoding(String encodedPassword) { 
    return false; 
  }
}
```
Las dos primeros métodos corresponden a la funcionalidad base de este contrato. `encode()` se encarga de codificar la contraseña, mientras que `matches()` verifica que una contraseña en texto plano corresponda a la contraseña codificada. 

Si bien es posible definir la funcionalidad base de estos métodos, acorde a las necesidades de seguridad del negocio, también es posible reusar algunas implementaciones pre-definidas del `PasswordEncoder`:

* `NoOpPasswordEncoder`: se trata de una opción deprecada que almacena la contraseña en texto plano. Se usa, normalmente, cuando no se desea corroborar la lógica de encriptamiento. __No se debe usar en ambientes productivos__.
* `StandardPasswordEncoder`: emplea SHA-256 para codificar las contraseñas. Está deprecada en las últimas versiones de Spring Security porque ya no se considera un estándar de codificación robusto en la industria. Se hace mención porque podría seguir estando implementado en algunos software legados con versiones antiguas de Spring Boot. Se recomienda actualizarlo a otro tipo de codificador.
* `Pbkdf2PasswordEncoder`: implementa el algoritmo de codificación [PBKDF2](https://cryptobook.nakov.com/mac-and-key-derivation/pbkdf2). Si bien es un estándar robusto para algunas aplicaciones, es importante tener en cuenta sus [limitaciones con autenticación LDAP](https://www.ibm.com/docs/es/sdse/6.4.0?topic=limitations-pbkdf2-password-encryption-algorithm).
* `BCryptPasswordEncoder`: emplea el robusto estándar [bcrypt](https://www.skysnag.com/es/blog/what-is-bcrypt/) para codificar la contraseña.
* `SCryptPasswordEncoder`: usa una técnica de [scrypt hashing](https://www.cryptominerbros.com/es/blog/what-is-scrypt-algorithm/?srsltid=AfmBOorE5jn8BFxuzjS1xllOCrXncqgl-IPttwbKog0ghu_AXd1y-vak) para codificar la contraseña.

Cada de una de estas implementaciones requiere de diversos secretos y configuraciones para la correcta generación del codificador de contraseñas. Por ejemplo, para implementar el algoritmo PBKDF2,sería algo como:

```java
PasswordEncoder encoder = 
   new Pbkdf2PasswordEncoder("secret", 16, 310000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
```

Los primeros tres parámetros corresponden al valor de la llave usada para codificar el proceso, el número de iteraciones usada para codificar la contraseña y el tamaño del hash. El cuarto parámetro define el ancho del hash y se puede elegir alguna de las siguientes opciones:

* PBKDF2WithHmacSHA1
* PBKDF2WithHmacSHA256
* PBKDF2WithHmacSHA512

Entre mayor sea el hash generado, más segura será la contraseña codificada, pero menor será el _performance_ de la autenticación. De forma similar, en la Figura 4 se pueden apreciar los argumentos usados para el _scrypt hashing_.

![](../../static/img/security/conceptos/scrypt.png)

Figura 4. Argumentos para implementar el _scrypt hashing_. __Fuente:__ Spilca, L. _"Spring Security in Action"_. Second Edition. O'Reilley.

#### 1.2.2 Uso de Spring Security Crypto Module - SSCM

El módulo crypto (SSCM) es la parte de Spring Security que se encarga de toda acción relevante a criptografía, entre ellas: funciones de encriptamiento, decriptamiento y generación de llaves. 

En primer lugar, es importante entender que existen dos contratos para la generación de llaves: `StringKeyGenerator` y el `BytesKeyGenerator`.

```java
public interface StringKeyGenerator {

    String generateKey();

}
```

Los dos cuentan con el método `generateKey()`, que genera una llave de 8 bytes para encriptar o desencriptar un texto.

De igual forma, existen dos encriptadores en SSCM: `TextEncryptor` y el `BytesEncryptor`. Si bien funcionan de manera similar, las llaves que procesan son diferentes; de forma que el `TextEncryptor` sólo puede procesar llaves de tipo `String`, generadas con el `StringKeyGenerator`. Mientras que el `BytesEncryptor` puede procesar `bytes[]` o `String`.

```java
public interface TextEncryptor {

  String encrypt(String text);
  String decrypt(String encryptedText);

}
```

Los dos encriptadores cuentan con métodos para encriptar (`encrypt`) y desencriptar (`decrypt`) la información. Se puede emplear como estrategia adicional de seguridad para encriptación _simétrica_ o _asimétrica_ de contraseñas o información del usuario en general.


## 2. Protocolo de Autorización

### 2.1. Permisos

### 2.2. Role-Based Access Control - RBAC

