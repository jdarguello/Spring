---
sidebar_position: 4
---

# Métricas

Para la evaluación continua de métricas de calidad, usaremos __SonarCloud__, herramienta que ejecuta _análisis estático de código_ con base en las métricas de cobertura de pruebas unitarias y de integración. SonarCloud es una de las herramientas más robustas en la industria por su alta cobertura de métricas mediante _Quality Gates_. Entre las métricas más populares están:

* __Cobertura:__ cantidad de líneas de código ejecutadas en los flujos de test unitarios y de integración comparadas con la cantidad total existente en el algoritmo.
* __Code Smells:__ sintaxis inadecuada dentro del código; ya sea por nombres de variables o variables no utilizadas en la lógica, entre otros.
* __Duplication Lines:__ cantidad de líneas idénticas en el código. Mide la cantidad de _copy-paste_ que se haya hecho durante la construcción del algoritmo.
* __Seguridad:__ durante el análisis, Sonar también valida ciertas consideraciones de seguridad que puedan suponer un riesgo, como la presencia de secretos en el código fuente, por ejemplo.

SonarCloud puede ser usado de forma gratuita siempre que los desarrollos utilizados sean liberados en __repositorios públicos__. 

## 1. Habilitación de SonarCloud con GitHub

Una vez se tenga el repositorio público de GitHub donde se alojará el código fuente de nuestro microservicio, lo siguiente será crear una cuenta en SonarCloud, usando GitHub como proveedor de identidad, de acuerdo a lo expuesto en su [documentación oficial](https://docs.sonarsource.com/sonarqube-cloud/getting-started/github/). 

<img src="../../img/testing/metricas/login.webp" width="600px" />

Figura 1. Login en SonarCloud.

A continuación, aparecerá la vista de la Figura 2. Lo que debemos hacer es habilitar el proyecto que deseamos escanear en Sonar. Configurando las organizaciones y los repositorios que deseamos habilitar para ser escaneados.

<img src="../../img/testing/metricas/first.webp" width="600px" />

Figura 2. Configuración del primer proyecto.

Finalmente, Sonar nos pedirá que especifiquemos el plan de suscripción. Escogemos la __capa gratuita__, dado que estamos trabajando con repositorios públicos. 

## 2. GitHub Actions

Con GitHub Actions, podemos automatizar el escaneo de nuestras contribuciones en tiempo real. Cada commit ejecutará automáticamente el escaneo de todo el código fuente de nuestra aplicación. Para eso, sólo debemos generar un workflow en nuestro microservicio, de la siguiente forma.

1. Necesitamos generar los secretos de conexión de Sonar, especificamente el `SONAR_TOKEN`, `SONAR_PROJECT_KEY` y el `SONAR_ORGANIZATION`.
2. Crear los secretos de Sonar como secretos en el repositorio de GitHub. En la sección de _Settings_, _Secrets and Variables_ y en _Actions_. 
3. Debemos crear la carpeta `.github` en el directorio principal del repositorio. Dentro de esta carpeta, tendrás que crear otra con el nombre de `workflows`.
4. Dentro de `.github/workflows` podrás crear un pipeline en formato YAML. Para nuestro caso, podría ser algo como `sonar.yaml`. El contenido del pipeline podría ser algo como:

```yaml
name: Análisis de métricas de calidad.

on:
  push:
    branches:
      - main

jobs:
  code-quality:
    name: SonarCloud Scan
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4
        with:
          fetch-depth: 0

      - name: Set up Java
        uses: actions/setup-java@v3
        with:
          java-version: '17' # Specify the Java version (adjust if needed)
          distribution: 'temurin' # Choose your JDK distribution

      - name: Cache Maven dependencies
        uses: actions/cache@v3
        with:
          path: ~/.m2
          key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
          restore-keys: |
            ${{ runner.os }}-m2

      - name: Build and test
        run: |
          mvn clean install
          mvn test
          mvn jacoco:report # Generate coverage report
        working-directory: ./auth

        working-directory: ./auth

      - name: SonarCloud Scan
        uses: SonarSource/sonarcloud-github-action@master
        env:
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
        with:
          projectBaseDir: auth/
          args: >
            -Dsonar.projectKey=${{ secrets.SONAR_PROJECT_KEY }}
            -Dsonar.organization=${{ secrets.SONAR_ORGANIZATION }}
```