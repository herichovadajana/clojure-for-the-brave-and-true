(ns chapter3-test
  (:require [clojure.test :refer :all]
            [clojure-for-the-brave-and-true.chapter3 :as ch3]))

;; Test are comparing results of my solutions to results of solutions written in book

(def body-part {:name "left-arm" :size 10})

(deftest matching-part
  (is (= (ch3/matching-part body-part)
         (ch3/matching-part-bs body-part))))

(deftest symmetrize-body-parts
  (testing "That both functions return the same number of body parts"
    (is (= (count (ch3/symmetrize-body-parts ch3/asym-hobbit-body-parts))
           (count (ch3/symmetrize-body-parts-bs ch3/asym-hobbit-body-parts)))))
  (testing "That return set contains the same values"
    (is (= (set (ch3/symmetrize-body-parts ch3/asym-hobbit-body-parts))
           (set (ch3/symmetrize-body-parts-bs ch3/asym-hobbit-body-parts))))))
