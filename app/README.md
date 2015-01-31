The iPReS app.

## Routes
All routes for the service follow this pattern:

    <domain>/<lang_code>/<route>?<params>
    
Where `<lang_code>` is a supported language code specified [here](https://github.com/lewismc/iPReS#supported-product-translations), `<route>` is a route which mirrors one of [PO.DAAC's Web Service](http://podaac.jpl.nasa.gov/ws/index.html) routes, and `<params>` follow the same rules as PO.DAAC Web Service route parameters.
    
Example Usage:

    localhost:3000/kr/metadata/dataset?datasetId=PODAAC-GHMG2-2PO01&shortName=OSDPD-L2P-MSG02 

## Deploy Location

[Here](https://github.com/NSF-Polar-Cyberinfrastructure/datavis-hackathon#amazon-instance-and-data-buckets).

## Prerequisites For Local Development

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## How to run (locally)

    lein ring server

Alternatively, to just get all dependencies:

    lein deps

## Libraries Used

* [Leiningen](http://leiningen.org/)
* [Ring](https://github.com/ring-clojure/ring)
* [Compojure](https://github.com/weavejester/compojure)
* [Ring-Json](https://github.com/ring-clojure/ring-json)
