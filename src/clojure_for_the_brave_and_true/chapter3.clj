(ns clojure-for-the-brave-and-true.chapter3
  (:require [clojure.string :as str]))

(def asym-hobbit-body-parts
  [{:name "head" :size 3}
   {:name "left-eye" :size 1}
   {:name "left-ear" :size 1}
   {:name "mouth" :size 1}
   {:name "nose" :size 1}
   {:name "neck" :size 2}
   {:name "left-shoulder" :size 3}
   {:name "left-upper-arm" :size 3}
   {:name "chest" :size 10}
   {:name "back" :size 10}
   {:name "left-forearm" :size 3}
   {:name "abdomen" :size 6}
   {:name "left-kidney" :size 1}
   {:name "left-hand" :size 2}
   {:name "left-knee" :size 2}
   {:name "left-thight" :size 4}
   {:name "left-lower-leg" :size 3}
   {:name "left-achilles" :size 1}
   {:name "left-foot" :size 2}])

(defn matching-part
  "create right body part from left body part"
  [body-part]
  (update body-part :name #(str/replace % #"^left-" "right-")))

(defn symmetrize-body-parts
  "Expects a seq of maps that have :name and :size. If the name contains the left body part, function will add the right body part into seq."
  [asym-body-parts]
  (reduce (fn [acc {:keys [name size] :as body-part}]
            (conj acc (matching-part body-part)))
          asym-body-parts
          (filter #(str/includes? % "left") asym-body-parts)))

;; Solutions from book (bs)

(defn matching-part-bs [part]
  {:name (clojure.string/replace (:name part) #"^left-""right-") :size (:size part)})

(defn symmetrize-body-parts-bs
  "Expects a seq of maps that have :name and :size."
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts (set [part (matching-part part)])))))))


;; Additional exercise from book without book solution: Spider expander that multiply the numbers of eyes and legs

(defn spider-expander
  "Multiplies number of eyes and legs"
  [existing {:keys [name] :as body-part}]
  (if (or (str/includes? name "eye")
          (str/includes? name "leg"))
    (apply conj existing (take 3 (repeat body-part)))
    existing))

(defn symmetric-expander
  "Adds matching right part"
  [existing {:keys [name] :as body-part}]
  (if (str/includes? name "left")
    (conj existing (update body-part :name #(str/replace % #"^left-" "right-")))
    existing))

(defn symmetric-spider
  "Spider and symmetric expander application"
  [asym-body-parts]
  (let [symmetrized-body-parts (reduce symmetric-expander asym-body-parts asym-body-parts)]
    (reduce spider-expander symmetrized-body-parts symmetrized-body-parts)))

