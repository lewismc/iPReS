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

;; Imported libraries are defined in the namespace.
(ns demo.handler

  ;; compojure.core gives us routing abstractions.
  (:use compojure.core)

  ;; wrap-json-response allows us to wrap up a Clojure Map
  ;; data structure as JSON.
  (:use [ring.middleware.json])

  ;; response is the only piece needed from Ring for now,
  ;; so we only include that.
  (:use [ring.util.response :only [response]])

  ;; These alias certain namespaces to make the code less verbose.
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [demo.core :as core]))

;; This is making use of Compojure and Ring to
;; (a) easily define supported routes for the service, and
;; (b) use Ring to send JSON-encoded Clojure Maps.
(defroutes api-routes
  (GET "/translator*" [message]
    (response {:message (core/convert-message message)}))
  (route/not-found
    (response {:message "Page not found"})))

;; This is the entry point of the application.
;; The use of the "->" macro (Thread-First) simplifies function calls
;; so that nested code is limited.
;;
;; reference: http://clojuredocs.org/clojure.core/->
(def app
  (->
   (handler/api api-routes)
   (wrap-json-response)
   (wrap-json-body)))
