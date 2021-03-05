# Sales tax exercise 
## 1. Package
```bash
mvn clean package
```
## Option 1 : Run via console

### on windows
```bash
mvnw.cmd exec:java -D"exec.mainClass"="com.teksys.salestax.SalesTaxMain" -q
```
### on linux
```bash
./mvnw exec:java -D"exec.mainClass"="com.teksys.salestax.SalesTaxMain" -q
```

## Option 2 : Run from rest
### on windows
```bash
mvnw.cmd spring-boot:run
```
### on linux
```bash
./mvnw spring-boot:run
```
### Request example :
```bash
curl --location --request POST 'localhost:8080' \
--header 'Content-Type: application/json' \
--data-raw '{
    "items": [
        {
            "price" : 12.49,
            "category" : "BOOKS",
            "quantity" : 1,
            "name" : "book"
        },
        {
            "price" : 14.99,
            "category" :null,
            "quantity" : 1,
            "name" : "music CD"
        },
{
            "price" : 0.85,
            "category" : "FOOD",
            "quantity" : 1,
            "name" : "chocolate bar"
        }
    ]

}'
```