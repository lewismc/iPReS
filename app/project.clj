(defproject app "0.1.0-SNAPSHOT"
  :description "iPReS app"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.9"]
                 [ring/ring-json "0.3.1"]
                 [clojurewerkz/spyglass "1.1.0"]
                 [clj-http "1.0.1"]
                 [org.clojure/core.cache "0.6.4"]
                 [org.apache.tika/tika-translate "1.7"]
                 [org.clojure/data.xml "0.0.8"]]
  :plugins [[lein-ring "0.8.12"]]
  :ring {:handler app.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
