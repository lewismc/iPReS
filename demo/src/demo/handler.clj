(ns demo.handler
  (:use compojure.core)
  (:use [ring.middleware.json :only [wrap-json-response]])
  (:use [ring.util.response :only [response]])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes api-routes
  (GET "/" []
       (response {:foo "bar"})))

(def app
  (->
   (handler/api api-routes)
   (wrap-json-response)))

;;(defn handler [request]
;;  (response {:foo "bar"}))
;;
;;(def app
;;  (wrap-json-response handler))
