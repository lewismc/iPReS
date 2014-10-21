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
