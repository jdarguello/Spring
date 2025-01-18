---
sidebar_position: 1
---

# Introducción

La seguridad informática es amplia y se aplica en diferentes capas. Algunas de las más relevantes son: 

* __Definiciones de negocio:__ especifica las funcionaldiades que debe tener un sistema para evitar problemas de seguridad. Por ejemplo, si hablamos de una aplicación bancaria, la funcionalidad de los _bolsillos de ahorro_ no es sólo para subdividir el dinero de una cuenta de ahorros en múltiples secciones. También sirve para proteger al cliente cuando ocurren escenarios de fraude, como la clonación de tarjetas débito. De esta forma, el delincuente sólo podrá acceder al saldo disponible en la cuenta de ahorros, no la de los bolsillos. Otro ejemplo dentro de este espectro son los topes diarios en las transacciones definidas por el cliente. El delincuente sólo podrá robar la cantidad máxima definida por el cliente, aún si este tiene saldo disponible para realizar más transacciones.
* __Arquitectura de la aplicación:__ la arquitectura de la aplicación conecta las definiciones de negocio y las lleva un paso más allá al especificar la lógica de negocio con la capa de persistencia de datos. Una arquitectura robusta de la aplicación no debería habilitar todas las operaciones CRUD sobre las entidades del negocio, sino las estrictamente necesarias. Por ejemplo, no se deberían habilitar APIs en el backend para modificar el saldo de una cuenta bancaria. De ser así, si un cliente, con conocimiento en desarrollo de software, conociera el endpoint para actualizar el saldo de su cuenta bancaria, podría solicitar agregarse una cantidad indefinida de dinero. Sin ser necesariamente un "ciberdelincuente", ya que no está vulnerando el software bancario.
* __Estándares de Ciberseguridad:__ buscan proteger a los clientes de una aplicación contra diferentes ataques por parte de ciberdelincuentes. Entre ellos se destacan: ataques de suplantación de identidad y robo de credenciales (phishing), indisponibilidad por saturación de los servicios (DDoS), manipulación de los datos de los clientes (SQLInjection) y cibersecuestro (donde los ciberatacantes roban y encriptan la información de un negocio), entre muchos otros. Algunos estándares que brindan lineamientos para combatir estos escenarios son: [OWASP](https://owasp.org) y [CIS](https://www.cisecurity.org).
* __Vulnerabilidades:__ se refiere a la secciones en el código fuente donde es posible realizar ciertos tipos de ciberataques para vulnerar la infraestructura o la funcionalidad base de una aplicación. Se mitigan actualizando frecuentemente las versiones de software, cambiando las dependencias por otras con funcionalidades similares o dejando de utilizar las funcionalidades que presentan dichas vulnerabilidades.  
* __Autenticación y autorización:__ se tratan de protocolos que identifican a los usuarios de un producto y las acciones que tienen permitidas realizar dentro de la aplicación. Brinda seguridad en el manejo de la información de una aplicación.
* __Arquitectura de soluciones:__ en la arquitectura de soluciones se definen los recursos de infraestructura necesarios y los medios de despliegue para el correcto funcionamiento de una aplicación. Una buena definición de arquitectura tiene la capacidad de prevenir múltiples escenarios de ciberataques, limitar la comunicación entre servicios que hayan sido vulnerados y disminuir el riesgo de explotación de vulnerabilidades en el código fuente. 

Como se puede apreciar, la seguridad informática es un área muy diversa. Razón por la existen cientos de fuentes bibliográficas que aplican diferentes consideraciones en cada etapa del ciclo de vida del desarrollo de software. En el presente capítulo, hablaremos únicamente sobre protocolos de _autenticación y autorización_ con Spring Security.


## 1. ¿Qué es Spring Security?

Spring Security se trata de la dependencia base para el manejo de usuarios y permisos dentro del ecosistema de Spring Framework. Es altamente customizable y se puede integrar fácilmente dentro de una aplicación de Spring Boot. En cuanto a _autenticación_, soporta desde protocolos básicos de usuario/contraseña, hasta métodos más robustos como SSO (_"Single Sign-On"_) con OAuth 2.0, SAML y/o MFA. Además, permite la integración con proveedores LDAP y otros servicios externos de autenticación.

En cuanto a la _autorización_, permite emplear desde protocolos sencillos basados en permisos hasta la implementación de metodologías robustas tipo RBAC (_"Role-Based Access Control"_). También, permite la customización a través de anotaciones como `@Preauthorize`, `@PostAuthorize` y `@Secured`.

Adicional, Spring Security previene algunos ataques frecuentes, como el CSRF (_"Cross-Site Request Forgery"_) y el XSS (_"Cross-Site Scripting"_). Permite el manejo de contraseñas mediante protocolos de encriptamiento y codificación, usando algoritmos como bcrypt, Argon2 y PBKDF2, entre otras funcionalidades. 



