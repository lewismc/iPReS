(ns app.core
  (:require [clj-http.client :as client]
            [app.cache :refer :all]
            [clojure.string :as str])
  (:import (org.apache.tika.language.translate MosesTranslator)))

(def langs {:en       "english"
            :ar       "arabic"
            :bg       "bulgarian"
            :ca       "catalan"
            :cs       "czech"
            :da       "danish"
            :nl       "dutch"
            :et       "estonian"
            :fi       "finnish"
            :fr       "french"
            :de       "german"
            :el       "greek"
            :he       "hebrew"
            :hi       "hindi"
            :mww      "hmong daw"
            :id       "indonesian"
            :it       "italian"
            :ja       "japanese"
            :hu       "hungarian"
            :ko       "korean"
            :lv       "latvian"
            :lt       "lithuanian"
            :ms       "malay"
            :mt       "maltese"
            :no       "norwegian"
            :fa       "persian"
            :pl       "polish"
            :pt       "portuguese"
            :ro       "romanian"
            :ru       "russian"
            :sk       "slovak"
            :sl       "slovenian"
            :es       "spanish"
            :sv       "swedish"
            :th       "thai"
            :tr       "turkish"
            :uk       "ukrainian"
            :ur       "urdu"
            :vi       "vietnamese"
            :cy       "welsh"
            :zh-CHS   "chinese simplified"
            :zh-CHT   "chinese traditional"
            :ht       "haitian creole"
            :tlh      "klingon"
            :tlh-Qaak "klingon (plqaD)"})

(def podaac-base-url "http://podaac.jpl.nasa.gov/ws/")

(def translator (MosesTranslator.))

(defn hit-podaac
  "Hits the PO.DAAC web service specified by the given route,
  with the parameters specified by the given params."
  [route params]
  (->
    (str podaac-base-url route)
    (client/get {:query-params params})))

(defn translate-with-tika
  "Returns the translated dataset into the specified language
  using Apache Tika.

  TODO: Translate"
  [dataset lang]
  (str dataset))

(defn translate-to-lang
  "Returns PO.DAAC dataset specified by the given language."
  [dataset key lang]
  (cache-add key (translate-with-tika dataset key))
  (cache-lookup key))

(defn convert-to-format
  "Return dataset in specified format

  TODO: Add file conversion"
  [dataset format]

  ;; temporary return statement
  (str dataset))

(defn ^:private route-to-key
  "Returns the concatenation of the given route, split by slashes,
  with the suppled language code."
  [route lang-code]
  (keyword (str (apply str (str/split route #"/")) lang-code)))

(defn translate-request
  "Handles a given iPres request and returns the translated data in the specified format."
  [route params lang-code format]
  (let [cache-key (route-to-key route lang-code)]
    (if (cache-has? cache-key)
      (cache-lookup cache-key)
      (->
        (hit-podaac route params)
        (translate-to-lang cache-key lang-code)
        (convert-to-format format)))))
