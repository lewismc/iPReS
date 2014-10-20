# demo

Quick demonstration of a REST service in Clojure.

## Tech Stack

* [Leiningen](http://leiningen.org/), the de-facto built automation tool for Clojure
* [Ring](https://github.com/ring-clojure/ring), the most mature and actively-used HTTP abstraction library
* [Compojure](https://github.com/weavejester/compojure), the de-facto routing library for Ring
* [Ring-Json](https://github.com/ring-clojure/ring-json), the de-facto ring middleware for handling JSON requests and responses.  Internally, it uses [Ceshire](https://github.com/dakrone/cheshire), a feature-ritch and very fast JSON serializer/deserializer

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## First-time usage:

    lein deps
    lein ring server

## Running

To start a web server for the application, run:

    lein ring server
