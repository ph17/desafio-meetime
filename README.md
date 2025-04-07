# Observações Iniciais

> Observações importantes:

1. Inicialmente, eu desenvolvi tudo com a intenção de rodar localmente, até notar a necessidade de _webhook_s.  Para que o HubSpot possa chamar meu endpoint, é preciso que o aplicativo esteja exposto na internet. Por isso, fiz o deploy no [Render.com](https://render.com) e manterei o app ativo por **1 semana**. O app só funciona com a URL pública cadastrada no HubSpot, então todos os testes devem ser feitos pela URL do Render.
2. A instância roda na **free tier** do Render, devido a isso, o aplicativo entrará em modo “sleep” em caso de inatividade e pode levar **50 segundos ou mais** para responder à primeira requisição.
3. O tópico 4 do desafio não ficou claro. Não entendi muito bem o que queriam dizer com "processar os eventos", por isso, decidi que o endpoint do _webhook_ não faça nada além de logar a requisição recebida.
4. Não fiz testes unitários por falta de tempo e porque o desafio não exigiu.
5. Melhorias nas validações, no tratamento de exceções e em outros aspectos foram deixadas de lado por falta de tempo.
6. O repositório no GitHub e o app no HubSpot ficarão disponíveis por **1 semana**.
---

## Estrutura
No decorrer da implementação, tentei manter a simplicidade, fiz uma estrutura baseada no _MVC_. Pensei na arquitetura hexagonal, porém considerei complexa demais para este caso.

Escolhi utilizar classes anotadas com `@ConfigurationProperties` ao invés de `@Value` para pegar valores fixos do application.properties, pois considero essa uma abordagem mais limpa e escalável.

## Tecnologias Usadas
- Java 17
- Spring Boot
- Maven
- Docker
- OpenFeign
- MapStruct

## Tratamento de exceções
Toda a aplicação trata exceções via `@RestControllerAdvice` na classe `GlobalExceptionHandler`. Devido à falta de tempo, o tratamento das exceções é pobre e carece de melhorias.
As exceções foram tratadas da seguinte forma:

- **Token de acesso ao HubSpot está inválido ou expirado**: retorna 403 Forbidden com a mensagem "Acesso negado ao HubSpot: [mensagem da exceção]"
- **Quando há uma chamada incorreta à API do HubSpot**: retorna 400 Bad Request com a mensagem "Chamada incorreta ao HubSpot: [mensagem da exceção]"
- **Qualquer outro erro vindo do HubSpot**: retorna 500 Internal Server Error com a mensagem "Erro inesperado ao conectar com o HubSpot: [mensagem da exceção]"
- **As demais exceções**: retorna 500 Internal Server Error com a mensagem "Erro inesperado: [mensagem da exceção]"

## Autenticação e Token
Optei por retornar o *access_token* no endpoint `/auth/oauth-callback` com o prefixo `"Bearer"`:

Isso foi feito porque o HubSpot exige o token com esse prefixo no header `Authorization` e nenhuma verificação é feita na nossa aplicação.

O único endpoint que exige o token é o `/crm/criar-contato`, por isso, utilizei `@RequestHeader` e não adicionei nenhuma complexidade desnecessária, como filtros.

Efetivamente, nenhuma validação é feita no token, exceto a verificação de que ele foi enviado. **Todas** as validações são feitas pelo próprio HubSpot.
## Bibliotecas
### `spring-boot-starter-web`
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
Permite criar endpoints REST com Spring Boot.
### `spring-boot-starter-test`
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```
Dependência para testes unitários. Como explicado anteriormente, esta dependência não foi utilizada, já que não foram feitos testes unitários.
### `org.projectlombok`
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```
Reduz código boilerplate com anotações como `@Data`, `@Builder`, etc. Também foi utilizada para logs com `@Slf4j`.
### `spring-cloud-starter-openfeign`
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
    <version>4.2.1</version>
</dependency>
````
Adiciona suporte ao módulo Feign do Spring. Permite realizar chamadas a APIs de maneira simples e eficaz, utiliza apenas interfaces e anotações.
### `mapstruct` e `mapstruct-processor`
```xml
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.6.3</version>
</dependency>
<dependency>
<groupId>org.mapstruct</groupId>
<artifactId>mapstruct-processor</artifactId>
<version>1.6.3</version>
</dependency>
```

Ambas as dependências são utilizadas para fazer mapeamento. Em nosso caso, convertemos a resposta ou requisição das APIs externas para a resposta ou requisição de nosso serviço.
Cria automaticamente a implementação da interface anotada com `@Mapper`.

- `mapstruct`: fornece as anotações e interface
- `mapstruct-processor`: gera o código no build
### `jakarta.validation`
```xml
<dependency>
    <groupId>jakarta.validation</groupId>
    <artifactId>jakarta.validation-api</artifactId>
    <version>3.1.1</version>
</dependency>
```
Disponibiliza vários tipos de validações por meio de anotações. Aqui ela é apenas utilizada para validar o formato do email no endpoint de criação de contato.
# Como usar

## URL base
```text
https://desafio-meetime.onrender.com/
```
## Passos
### 1. Gerar URL de autorização
**GET** `/auth/gerar-url`

```bash
curl --request GET \
  --url https://desafio-meetime.onrender.com/auth/gerar-url \
  --header 'User-Agent: insomnia/10.3.1'
```
**Exemplo:**

```text
https:/app.HubSpot.com/oauth/authorize?client_id=17ca42be-91a0-4ff4-8bc2-09af7a434ec4&redirect_uri=https://desafio-meetime.onrender.com/auth/oauth-callback&scope=oauth%20crm.objects.contacts.read%20crm.objects.contacts.write%20crm.schemas.contacts.read%20crm.schemas.contacts.write&optional_scope
```
### 2. Autorizar app no HubSpot
Acesse a URL gerada, faça o login no HubSpot e autorize a sua conta a conectar-se no app.
O HubSpot automaticamente irá chamar o endpoint `/auth/oauth-callback` e você verá a resposta no navegador:

**Exemplo:**
```json
{
  "refresh_token": "na1-5bd1-9e2a-45e1-927b-cc5c05293d7d",
  "access_token": "Bearer CLCg-5HhMhINAAEAQAAAASAAAAAAARi2xNUXILyY3iUoweL3BDIUmT8qGuCHaAGja5oNWhCbsaFF4e86MAAAAEEAAAAAAAAAAAAAAAAAgAAAAAAAAAAAACAAAAAAAOARAAAAAABAAIABAAAQAkIUu9ApNWqkySlWsGqYsY0ogJ3C_IxKA25hMVIAWgBgAGi8mN4lcAA",
  "expires_in": 1800
}
```
### 3. Criar contato
Agora, resta apenas chamar o endpoint de criação de contato.
De acordo com a documentação do HubSpot, qualquer prioridade poderia ser definida para os contatos — apenas o e-mail é obrigatório. Para manter a simplicidade, disponibilizei apenas nome e sobrenome.

A requisição deve ser feita via POST, com o token passado no header `Authorization`.

```bash
curl --request POST \
  --url https://desafio-meetime.onrender.com/crm/criar-contato \
  --header 'Authorization: Bearer CNGx15DhMhINAAEAQAAAASAAAAAAARi2xNUXILyY3iUoweL3BDIUxdHrk1sAz6r1yVSI4uugSttiS_E6MAAAAEEAAAAAAAAAAAAAAAAAgAAAAAAAAAAAACAAAAAAAOARAAAAAABAAIABAAAQAkIU-vwZHbU89TVdic9UKvOMyZIFoZVKA25hMVIAWgBgAGi8mN4lcAA' \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/10.3.1' \
  --data '{
    "email": "teste@teste.com",
    "nome": "João",
    "sobrenome": "da Silva"
}'
```
**Exemplo de Resposta:**
```json
{
    "createdAt": "2025-04-07T20:51:21.314Z",
    "archived": false,
    "archivedAt": null,
    "propertiesWithHistory": null,
    "id": "112117738673",
    "objectWriteTraceId": null,
    "properties": {
       "createdate": "2025-04-07T20:51:21.314Z",
       "email": "teste@teste.com",
       "firstname": "João",
       "hs_all_contact_vids": "112117738673",
       "hs_associated_target_accounts": "0",
       "hs_currently_enrolled_in_prospecting_agent": "false",
       "hs_email_domain": "teste1005gg.com",
       "hs_full_name_or_email": "João da Silva",
       "hs_is_contact": "true",
       "hs_is_unworked": "true",
       "hs_lifecyclestage_lead_date": "2025-04-07T20:51:21.314Z",
       "hs_membership_has_accessed_private_content": "0",
       "hs_object_id": "112117738673",
       "hs_object_source": "INTEGRATION",
       "hs_object_source_id": "10350913",
       "hs_object_source_label": "INTEGRATION",
       "hs_pipeline": "contacts-lifecycle-pipeline",
       "hs_prospecting_agent_actively_enrolled_count": "0",
       "hs_registered_member": "0",
       "hs_sequences_actively_enrolled_count": "0",
       "lastmodifieddate": "2025-04-07T20:51:21.314Z",
       "lastname": "da Silva",
       "lifecyclestage": "lead",
       "num_notes": "0"
    },
    "updatedAt": "2025-04-07T20:51:21.314Z"
}
```
#### _webhook_
Após a criação do contato, o HubSpot faz um POST para o endpoint `/crm/criar-contato-_webhook_`. Como dito antes, esse endpoint apenas loga o conteúdo recebido. A visualização dos logs depende do acesso ao Render, por isso, não será possível vocês verificarem a chamada, mas deixarei um print como evidência. Como disse antes, esse ponto ficou muito confuso.
![img.png](img.png)