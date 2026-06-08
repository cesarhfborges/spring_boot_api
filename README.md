# Spring Boot API

Este é o backend da aplicação desenvolvido em Java com Spring Boot.

## 🛠️ Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:
* **Java JDK 17** ou superior
* **Maven** (opcional, caso prefira usar o `mvn` global em vez do wrapper `.\mvnw.cmd`)
* **MySQL** (caso vá rodar localmente apontando para um banco físico)

---

## 📦 1. Instalação de Dependências

O Maven baixa todas as dependências automaticamente na primeira vez que você compila ou roda o projeto. Para baixar e validar o projeto sem rodar os testes, execute no terminal:

#### Usando o Maven global
```bash
mvn clean install -DskipTests
```
#### Ou usando o Wrapper do projeto (Windows PowerShell)
```bash
.\mvnw.cmd clean install -DskipTests
```

---

## 🚀 2. Como Rodar a Aplicação

### Opção A: Rodar Localmente (Ambiente Padrão / Default)
Se você configurou as propriedades de banco locais diretamente no arquivo principal `application.yaml`, execute o comando abaixo para subir o servidor na porta padrão `8080`:

#### Usando o Maven global
```bash
mvn spring-boot:run
```
#### Ou usando o Wrapper do projeto (Windows PowerShell)
```bash
.\mvnw.cmd spring-boot:run
```

### Opção B: Rodar Localmente especificando o Perfil de Dev
Caso suas configurações de banco local estejam isoladas em um arquivo `application-dev.yaml`, force a inicialização com o perfil ativo:

#### Usando o Maven global
```bash
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
```
#### Ou usando o Wrapper do projeto (Windows PowerShell)
```bash
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

---

## 🧪 3. Como Rodar os Testes Unitários

### Opção A: Rodar Testes no Ambiente Padrão (Default)
Executa a suíte de testes utilizando as configurações principais do arquivo `application.yaml`:

#### Usando o Maven global
```bash
mvn test
```
#### Ou usando o Wrapper do projeto (Windows PowerShell)
```bash
.\mvnw.cmd test
```

### Opção B: Rodar Testes usando o Perfil de Dev
Executa a suíte de testes forçando o Spring a ler o perfil `dev` (necessário usar aspas no Windows PowerShell para evitar falhas de leitura do ponto):

#### Usando o Maven global
```bash
mvn test "-Dspring.profiles.active=dev"
```
#### Ou usando o Wrapper do projeto (Windows PowerShell)
```bash
.\mvnw.cmd test "-Dspring.profiles.active=dev"
```

### Rodar apenas um Teste Específico
Se quiser poupar tempo e rodar apenas uma classe de teste específica:

#### Usando o Maven global
```bash
mvn test "-Dtest=NomeDaSuaClasseTest"
```
#### Ou usando o Wrapper do projeto (Windows PowerShell)
```bash
.\mvnw.cmd test "-Dtest=NomeDaSuaClasseTest"
```

---

## 📊 Relatórios de Testes
Após a execução dos testes, você pode conferir o relatório completo de sucessos e falhas abrindo o arquivo abaixo no seu navegador:
* `target/surefire-reports/index.html`

Por padrão, o Maven gera os relatórios estruturados em formato XML. Para compilar esses dados em uma página web (HTML) legível, execute:

#### Usando o Maven global
```bash
mvn surefire-report:report "-Dspring.profiles.active=dev"
```
#### Ou usando o Wrapper do projeto (Windows PowerShell)
```bash
.\mvnw.cmd surefire-report:report "-Dspring.profiles.active=dev"
```

Após a execução, abra o relatório gerado em seu navegador de preferência:
* `target/reports/surefire.html`