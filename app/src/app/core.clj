(ns app.core
  (:require [clj-http.client :as client]))

;; A map of the languages iPReS supports.
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

(defn hit-podaac
  "Hits the PO.DAAC web service specified by the given route,
  with the parameters specified by the given params."
  [route params]
  (->
    (str podaac-base-url route)
    (client/get {:query-params params})))