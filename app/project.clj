(defproject app "0.1.0-SNAPSHOT"
            :description "iPReS application"
             :license {:name "Apache License v2.0"
                           :url "http://www.apache.org/licenses/LICENSE-2.0"}
            :url "https://github.com/lewismc/iPReS/tree/master/app"
            :min-lein-version "2.0.0"
            :dependencies [[org.clojure/clojure "1.6.0"]
                           [compojure "1.1.9"]
                           [ring/ring-json "0.3.1"]
                           [clojurewerkz/spyglass "1.1.0"]
                           [org.clojure/core.cache "0.6.4"]
                           [org.apache.tika/tika-translate "1.7"]
                           [com.github.kyleburton/clj-xpath "1.4.4"]]
            :plugins [[lein-ring "0.8.12"]
                      [codox "0.8.11"]]
            :ring {:handler app.handler/app}
            :profiles
            {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring-mock "0.1.5"]]}})
