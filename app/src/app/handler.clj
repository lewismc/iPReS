(ns app.handler
  (:use compojure.core)
  (:use [ring.middleware.json])
  (:use [ring.util.response :only [response]])

  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [app.core :as core]
            [clojure.string :as str]))

(def domain (str "http://podaac.jpl.nasa.gov/ws/ipres/"))

;; Checks if the given language code is supported.
;; str -> bool
(defn is-supported? [lang]
  (->>
    (keyword lang)
    (contains? core/langs)))

;; API routes for the iPReS Service.
(defroutes api-routes
  (GET "/metadata/dataset" [request]
    (response {:msg "hello"}))
  (GET "/metadata/granule" [request]
    (response {:msg "hello"}))
  (GET "/search/dataset" [request]
    (response {:msg "hello"}))
  (GET "/search/granule" [request]
    (response {:msg "hello"}))
  (GET "/image/granule" [request]
    (response {:msg "hello"}))
  (GET "/extract/granule" [request]
    (response {:msg "hello"}))
  (route/not-found
   (response {:msg "Page not found"})))

(def app
  (->
   (handler/api api-routes)

   ;; This will need replacement...
   (wrap-json-response)

   ;; As will this
   (wrap-json-body)))
