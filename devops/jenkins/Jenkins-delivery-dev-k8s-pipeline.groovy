pipeline {
  agent any

  tools {
    // Install the Maven version configured as "M3" and add it to the path.
    maven "MAVEN_HOME"
  }

  stages {
    stage('Clone') {
      steps {
        timeout(time: 2, unit: 'MINUTES'){
          git 'https://github.com/jenkins-devops-namh/repasando-programacion.git'
        }
      }
    }
    stage('Build') {
      steps {
        timeout(time: 2, unit: 'MINUTES'){
          sh "mvn -DskipTests clean package -f demo-crud-repaso/pom.xml"
        }
      }
    }
    stage('Test') {
      steps {
        timeout(time: 2, unit: 'MINUTES'){
          // Se cambia <test> por <install> para que se genere el reporte de jacoco
          sh "mvn clean install -f demo-crud-repaso/pom.xml"
        }
      }
    }
    stage('Sonar') {
      steps {
        timeout(time: 2, unit: 'MINUTES'){
          withSonarQubeEnv('sonarqube'){
            sh "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar -Pcoverage -f demo-crud-repaso/pom.xml"
          }
        }
      }
    }
    stage('Quality gate') {
      steps {

        sleep(10) //seconds

        timeout(time: 2, unit: 'MINUTES'){
          waitForQualityGate abortPipeline: true
        }
      }
    }
    stage('Deploy') {
      steps {
        echo "mvn spring-boot:run -f demo-crud-repaso/pom.xml"
      }
    }
  }
}