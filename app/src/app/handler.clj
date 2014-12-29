(ns app.handler
  (:use compojure.core)
  (:use [ring.middleware.json])
  (:use [ring.util.response :only [response]])

  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [app.core :as core]))

(defn is-supported?
  "Return if a given language code, specified as a string,
  is a key in the supported map of languages."
  [lang-code]
  (->>
    (keyword lang-code)
    (contains? core/langs)))

(defn metadata-dataset-is-valid?
  "Returns true if a request for an iPReS Metadata Dataset
  supplies the required parameters, as detailed by:

  http://podaac.jpl.nasa.gov/ws/metadata/dataset/index.html"
  [request]
  (and (contains? request :datasetId)
       (contains? request :shortName)))

(defn metadata-granule-is-valid?
  "Returns true if a request for an iPRes Metadata Granule
  supplies the required parameters, as detailed by:

  http://podaac.jpl.nasa.gov/ws/metadata/granule/index.html"
  [request]
  (and (metadata-dataset-is-valid? request)
       (contains? request :granuleName)))

(defn api-routes
  "The API routes for the iPReS service.
  Nested route structure under supported languages."
  [lang]
  (routes
    (GET "/metadata/dataset" [& request]
         (if (metadata-dataset-is-valid? request)
           (response {:msg (str "a data set for " lang)})
           (response {:msg "you are a bad man"})))
    (GET "/metadata/granule" [& request]
         (if (metadata-granule-is-valid? request)
           (response {:msg (str "a granule for " lang)})
           (response {:msg "no kitty that's a bad kitty"})))
    (GET "/search/dataset" [request]
         (response {:msg "hello"}))
    (GET "/search/granule" [request]
         (response {:msg "hello"}))
    (GET "/image/granule" [request]
         (response {:msg "hello"}))
    (GET "/extract/granule" [request]
         (response {:msg "hello"}))
    (route/not-found
      (response {:msg "Page not found"}))))

;; iPReS routes - 404's if language unsupported
(defroutes ipres
           (context "/:lang" [lang]
                    (if (is-supported? lang)
                      (api-routes lang))))

(def app
  (->
    (handler/api ipres)

    ;; This will need replacement...
    (wrap-json-response)

    ;; As will this
    (wrap-json-body)))
