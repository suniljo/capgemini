=============================
Case: Externalize the API KEY
=============================

Option #1

Run As > Run Configurations > Environment > Add
Variable: OPENAI_API_KEY
Value   : <api_key>


--- application.yml ----
spring:
  application:
    name: 02.spring-ai-app-externalize-key
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        model: gpt-4o
        max-tokens: 100

-- Testing it ---
String apiKey = System.getenv("OPENAI_API_KEY");
System.out.println("API Key: " + apiKey);


Option #2
  --> package the application
        $mvn clean compile package
  -->   $> java -jar -DOPENAI_API_KEY=<api_key_value> target/<jar file>

Option #3

** Open PowerShell as Administrator

cmd> [System.Environment]::SetEnvironmentVariable("OPENAI_API_KEY", "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", "Machine")

cmd> echo $env:OPENAI_API_KEY



Option #4  --- need to refactor - not working as expected

<dependency>
    <groupId>me.paulschwarz</groupId>
    <artifactId>spring-dotenv</artifactId>
    <version>4.0.0</version>
</dependency>

Create .env in project root:
   API_KEY=<api_key>

application.properties / application.yml
   app.api.key=${API_KEY}
