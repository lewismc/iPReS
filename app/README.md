The iPReS app.

## Routes
All routes for the service follow this pattern:

    <domain>/<lang_code>/<route>?<params>
    
Where `<lang_code>` is a supported language code specified [here](https://github.com/lewismc/iPReS#supported-product-translations), `<route>` is a route which mirrors one of [PO.DAAC's Web Service](http://podaac.jpl.nasa.gov/ws/index.html) routes, and `<params>` follow the same rules as PO.DAAC Web Service route parameters.
    
## Example Usage

/metadata/dataset:

    localhost:3000/ko/metadata/dataset?datasetId=PODAAC-GHMG2-2PO01&shortName=OSDPD-L2P-MSG02 
    
/metadata/granule:

    http://localhost:3000/ko/metadata/granule?datasetId=PODAAC-GHMG2-2PO01&shortName=OSDPD-L2P-MSG02&granuleName=20120912-MSG02-OSDPD-L2P-MSG02_0200Z-v01.nc&format=iso
    
...rest are TBD until they appear to work correctly.

## Deploy Location

[Here](https://github.com/NSF-Polar-Cyberinfrastructure/datavis-hackathon#amazon-instance-and-data-buckets).

## Prerequisites For Local Development

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## How to run (locally)

    lein ring server

Alternatively, to just get all dependencies:

    lein deps
    
## How to run tests

To run the whole suite:

    lein test
    
To run a specific namespace test (in this case, the cache test file):

    lein test app.cache-test
    
To run a specific test function (in this case, the basic cache test function):

    lein test :only app.cache-test/basic-cache-test
    
## Testing Style

All tests are (currently) using the standard test library provided with the language.  This may change, as it isn't always the greatest when it comes to reporting.  Example:

    (deftest example-test
       (testing "that this example passes"
          (is (= "bananas" (function-which-always-returns-bananas)))))

When stubbing service calls ("faking" a call to the service), we use the handy [`with-redefs-fn`](http://clojuredocs.org/clojure.core/with-redefs-fn) function, rather than complicating the code with Dependency Injection.  Example:

    with-redefs-fn {#'hit-podaac (fn [route params] "bananas")}
                    #(is (= "bananas" (translate-request "metadata/dataset"
                                                         {:datasetId "PODAAC-GHMG2-2PO01"
                                                          :shortName "OSDPD-L2P-MSG02"}
                                                         "kr"
                                                         "xml"))))
                                                         
The above code is an example of stubbing a call to the `po.daac` service.  It takes two macros, one with the provided temporary redefinition of `hit-podaac` (specified in an anonymous function), and another which represents the function to be tested.  Every instance of `hit-podaac` in the chain of functions called by `translate-request` will then be replaced with our definition here (in this case, it will always return `bananas`).

More examples can be found in any of the test files.

## Libraries Used

* [Leiningen](http://leiningen.org/)
* [Ring](https://github.com/ring-clojure/ring)
* [Compojure](https://github.com/weavejester/compojure)
* [Ring-Json](https://github.com/ring-clojure/ring-json)
