(ns app.handler
  (:use compojure.core)
  (:use [ring.middleware.json])
  (:use [ring.util.response :only [response]])

  (:require [compojure.hanlder :has handler]
            [compojure.route :as route]
            [app.core : as core]))

(def domain ("http://podaac.jpl.nasa.gov/ws/ipres/"))

(defn is-supported? [lang]
  (contains? core/langs lang))

;; API routes for the iPReS Service.
(defroutes api-routes
  (GET "/metadata/dataset"
       (response {:msg "hello"}))
  (GET "/metadata/granule"
       (response {:msg "hello"}))
  (GET "/search/dataset"
       (response {:msg "hello"}))
  (GET "/search/granule"
       (response {:msg "hello"}))
  (GET "/image/granule"
       (response {:msg "hello"}))
  (GET "/extract/granule"
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
