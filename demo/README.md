# Demo

Quick demonstration of a REST service in Clojure.  When run, this will have the ability to convert a message provided as a parameter in the URL into JSON-encoded pig-latin.  This tech demo makes use of two of the most mature and commonly-used components of a Clojure web services stack.  It also serves to demonstrate the ease of which Clojure web services can be tested.

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

## Running Tests

To run every test in the /test directory:

    lein test

To run every test in a particular namespace:

    lein test demo.test.handler

To run a single test:

    lein test :only demo.test.handler/foo-test-always-fails

## Libraries used in detail

The libraries used here (Ring, Compojure) are two key pieces in the most widely-used web stack for Clojure projects.

Ring provides a thing abstraction layer over the HTTP request-response cycle.  It is analagous to Ruby's Rack and Java's Servlet specification, and exists to simplify writing web services without having to deal with low-level details.  Launching a web service through Ring makes use of [Eclipse's Jetty Servlet](http://www.eclipse.org/jetty/) technology, and performance characteristics are also similar except for extremely high-volume servers.  To deal with the performance dropoff, Ring handlers can be embedded into [Nginx-Clojure](https://github.com/nginx-clojure/nginx-clojure), which allows a Ring-based service to double its ability to service requests.

Compojure is a library which abstracts away some of the bare-bones parts of Ring, particularly when concerned with routing.  It is similar to Flask in Python, or parts of ASP.NET MVC for C#.

## Tests in detail

The tests here demonstrate three important aspects of Test-Driven Development in Clojure.

Firstly, raw unit testing in Clojure is very simple.  Tests are written in a declarative, semantic syntax, and equality is asserted via the (is (= _ _ )) syntax.  This is demonstrated in all tests.

Secondly, unit testing via mocking Ring HTTP requests and responses is simple as well.  Mocking the request-response cycle is very easily-accomplished, so simulating how the app should respond to various requests can be incorporated without much work.  This project will demonstrate mocked unit tests for simple request-response behavior to ensure the code surrounding our service calls behaves properly.

Lastly, testing using Leinengin is what brings it all together.  The command-line tool will output a summary of the test ran and what their results were.
