(ns demo.handler
  (:use compojure.core)
  (:use [ring.middleware.json :only [wrap-json-response]])
  (:use [ring.util.response :only [response]]))

(defn handler [request]
  (response {:foo "bar"}))

(def app
  (wrap-json-response handler))
