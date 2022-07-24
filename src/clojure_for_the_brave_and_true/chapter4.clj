(ns clojure-for-the-brave-and-true.chapter4)

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

;; Implementing some using reduce

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


