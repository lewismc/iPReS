The iPReS app
=============

# Prerequisites For Local Development

You will need [Leiningen](https://github.com/technomancy/leiningen/wiki/Packaging) >=2.9.1 (current as of March 2019) installed.

# How to run (locally)

    # make sure you are in the $IPRES_HOME/app directory
    $ cd app
    $ lein ring server-headless

Alternatively, to just get all dependencies:

    $ lein deps

# REST API

All routes for the service follow this pattern:

    <domain:port>/<lang_code>/<route>?<params>
    
Where `<lang_code>` is a supported language code specified [here](https://github.com/lewismc/iPReS#supported-product-translations), `<route>` is a route which mirrors one of [PO.DAAC's Web Service](http://podaac.jpl.nasa.gov/ws/index.html) routes, and `<params>` follow the same rules as PO.DAAC Web Service route parameters.
    
# Example Usage

## Dataset Metadata Translation

/metadata/dataset:

    curl "http://localhost:3000/es/metadata/dataset?datasetId=PODAAC-GH19L-2PS01&shortName=NEODAAS-L2P-AVHRR19_L"
    
Mandatory and optional metadata paramters are defined and maintained by PO.DAAC and can be found [here](http://podaac.jpl.nasa.gov/ws/metadata/dataset/index.html#params).

## Granule Metadata Translation

/metadata/granule:

    curl "http://localhost:3000/es/metadata/granule?datasetId=PODAAC-GH19L-2PS01&shortName=NEODAAS-L2P-AVHRR19_L&granuleName=20190329-AVHRR19_L-NEODAAS-L2P-29mar191726_wsst.8bit-v01.nc&format=iso"

Mandatory and optional granule paramters are defined and maintained by PO.DAAC and can be found [here](http://podaac.jpl.nasa.gov/ws/metadata/granule/index.html#params).

## Search Dataset Translation

    curl "http://localhost:3000/es/search/dataset?keyword=AVHRR-3"

Mandatory and optional search/dataset parameters are defined and maintained by PO.DAAC and can be found [here](http://podaac.jpl.nasa.gov/ws/search/dataset/index.html)

## Search Granule Translation

It should be noted that due to the limited metadata available in this response, the results are not particularly useful.

    curl "http://localhost:3000/es/search/granule?datasetId=PODAAC-GH19L-2PS01&startTime=2019-03-29T00:00:00Z&itemsPerPage=1"
    
Mandatory and optional search/granule parameters are defined and maintained by PO.DAAC and can be found [here](http://podaac.jpl.nasa.gov/ws/search/granule/index.html)

## Image Granule Translation

Not supported, as this only returns images.

## Extract Granule Translation

Not supported (yet), due to Netcdf and HDF being unsupported at this time.

# Deploy Location

[Previous deployement location](https://github.com/NSF-Polar-Cyberinfrastructure/datavis-hackathon#amazon-instance-and-data-buckets).
    
# How to run tests

To run the whole suite:

    $ lein test
    
To run a specific namespace test (in this case, the cache test file):

    $ lein test app.cache-test
    
To run a specific test function (in this case, the basic cache test function):

    $ lein test :only app.cache-test/basic-cache-test
    
# Testing Style

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

More examples can be found in any of the [test files](https://github.com/lewismc/iPReS/tree/master/app/test/app).

# Generating Documentation

Pure dead simple...

    $ lein doc

You'll see something like this

    ...
    Retrieving hiccup/hiccup/1.0.5/hiccup-1.0.5.jar from clojars
    Retrieving codox/codox.core/0.8.11/codox.core-0.8.11.jar from clojars
    Generated HTML docs in /usr/local/iPReS/app/doc 

# Libraries Used

Take a look at [project.clj](./project.clj)
