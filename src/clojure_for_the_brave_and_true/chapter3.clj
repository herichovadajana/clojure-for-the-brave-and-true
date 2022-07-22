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

;; Closing chapter exercises

;; 1. Use the str, vector, list, hash-map and hash-set functions

(str "Stumble" "bum")

(vector 1 2 3)

(list "a" "b" "c")

(hash-map :sunday "funday" :monday "glumday" :tuesday "newsday" :wednesday "friendsday" :thursday "blursday" :friday "highday" :saturday "fatterday")

(hash-set 1 2 2 3 4 4)

;; 2. Write a function that takes a number and adds 100 to it

(defn add100 [x]
  (+ x 100))

;; 3. write a function dec-maker

(defn dec-maker [x] #(- % x))
;; 4. write a function, mapset, that works exactly like a map except the returned value is a set

(defn mapset [fn coll]
  (set (map fn coll)))

;; 5. Function that's similar to symmetrize-body-parts except it has to work with aliens that instead id two eyes arms legs and so on they have five

(defn symmetrize-alien
  "symmetrize all symmetrical bodypart with rotational symmetry order 5"
  [asym-body-parts]
  (let [parts-to-symmetrize? (group-by #(str/includes? (:name %) "left") asym-body-parts)]
    (reduce (fn [acc body-part]
              (apply conj acc (take 5 (repeat (update body-part :name #(str/replace % #"^left-" ""))))))
            (get parts-to-symmetrize? false)
            (get parts-to-symmetrize? true))))


;; 6. Function that generalizes symmetrize-body-parts and symmetrize-alien. Function should take a coll of body parts and the number of matching parts to add

(defn better-symmetrize-body-parts
  "Symmetrize all symmetrical body parts with symmetry order given in argument"
  [asym-body-parts symmetry-order]
  (let [parts-to-symmetrize? (group-by #(str/includes? (:name %) "left") asym-body-parts)]
    (reduce (fn [acc body-part]
              (apply conj acc (if (= symmetry-order 2)
                                [body-part (update body-part :name #(str/replace % #"^left-" "right-"))]
                                (->> (update body-part :name #(str/replace % #"^left-" ""))
                                     repeat
                                     (take symmetry-order)))))
            (get parts-to-symmetrize? false)
            (get parts-to-symmetrize? true))))
