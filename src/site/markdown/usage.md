# Usage

The project requires a database and a server. But the included Maven profiles allow running it locally.

## Running the project locally

To run the project locally in Jetty:

```
$ mvn jetty:run-war -P jetty
```

An embedded Tomcat can be used through this other profile:

```
$ mvn tomcat7:run-war -P tomcat7
```

This makes the project be accessible at [http://localhost:8080/](http://localhost:8080/).
