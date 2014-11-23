(ns demo.core)

(require '[clojure.string :as str])

(def vowel? (set "AaEeIiOoUu"))

(defn to-pig-latin [word]
  (if (not (str/blank? word))
    (let [first-letter (first word)]
      (if (vowel? first-letter)
        (str word "ay")
        (str (subs word 1) first-letter "ay")))))

(defn convert-message [message]
  (let [words (str/split message #" ")]
    (apply str (map to-pig-latin words))))
