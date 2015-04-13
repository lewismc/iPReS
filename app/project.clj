; Licensed to the Apache Software Foundation (ASF) under one or more
; contributor license agreements. See the NOTICE file distributed with
; this work for additional information regarding copyright ownership.
; The ASF licenses this file to You under the Apache License, Version 2.0
; (the "License"); you may not use this file except in compliance with
; the License. You may obtain a copy of the License at
;
; http://www.apache.org/licenses/LICENSE-2.0
;
; Unless required by applicable law or agreed to in writing, software
; distributed under the License is distributed on an "AS IS" BASIS,
; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
; See the License for the specific language governing permissions and
; limitations under the License.

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
                           [com.github.kyleburton/clj-xpath "1.4.4"]
                           [ring.middleware.logger "0.5.0"]]
            :plugins [[lein-ring "0.8.12"]
                      [codox "0.8.11"]]
            :ring {:handler app.handler/app}
            :profiles
            {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring-mock "0.1.5"]]}})
