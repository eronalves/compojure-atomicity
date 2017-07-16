# Atomicity Clojure Compojure

Simple project to test Clojure atomicity with compojure and ring to serve an API blocking duplicate operations.

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server
    
or

    lein ring server-headless
    
    
## Testing

The API has the following endpoints:

    localhost:3000/add/:number
    localhost:3000/has-number/:number

The endpoint add will add the number on the params in a PersistentQueue Atom using swap!.
When call the same endpoint with same number the add will be blocked.

And the endpoint has-number verify if the number was added.

## License

Copyright © 2017 MIT
