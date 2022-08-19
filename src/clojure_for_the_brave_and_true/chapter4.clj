(ns clojure-for-the-brave-and-true.chapter4
  (:require [clojure.java.io :as io]))

;; Implementing map using reduce

(defn my-map
  "maps function on every item in coll"
  [function coll]
  (reduce #(conj %1 (function %2))
          []
          coll))

;; Implementing filter using reduce

(defn my-filter
  "filters coll by function"
  [function coll]
  (reduce #(if (= true (function %2))
             (conj %1 %2)
             %1)
          []
          coll))

;;  Implementing some using reduce

(defn my-some
  "returns first logical true value from coll, else nil"
  [function coll]
  (-> (reduce #(if (= true (function %2))
                 (conj %1 %2)
                 %1)
              []
              coll)
      empty?
      (if nil true)))

;;  FWPD exercise

(def filename (io/resource
               "suspects.csv" ))

(def vamp-keys [:name :glitter-index])

(defn str->int [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter)
          records))


;; Closing chapter exercises

;; 1. Turn the result of your glitter filter into a list of names.

(defn list-of-names [result-of-glitter-filter]
  (map :name result-of-glitter-filter))

;; 2. Write a function, append, which will append a new suspect to your list of suspects.

(defn append [new-suspect]
  (spit filename new-suspect :append true))

;; 3. Write a function, validate, which will check that :name and :glitter-index are present when you append. The validate function should accept two arguments: a map of keywords to validating functions, similar to conversions, and the record to be validated.

(defn validate [validating-fns suspect]
  (let [suspect-data (clojure.string/split suspect #",")]
    (if (= (count suspect-data) (count validating-fns))
        (map #((val %1) %2)  
             validating-fns
             suspect-data))))

;; 4. Write a function that will take your list of maps and convert it back to a CSV string. You’ll need to use the clojure.string/join function.

(defn list-to-csv [list-of-suspects]
  (->> list-of-suspects
       (map #(clojure.string/join "," (vals %)))
       (clojure.string/join ",")))
