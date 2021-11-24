## Requirement
* Java 1.8
* Maven
* [Chrome Driver](https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver)

## Project Build
```mvn clean install```

## JAR Build
```mvn clean compile assembly:single```

Then, JAR file can be found in target directory.

## Run
```java -jar /target/backend-engineer-1.0-SNAPSHOT-jar-with-dependencies.jar [count]```

Parameter `count` is optional. If not specified, it will be set as 100.

CSV file will be provided with a name in the following format: `Product_DATA_<timeInMillis>.csv`

