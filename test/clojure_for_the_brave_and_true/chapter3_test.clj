(ns chapter3-test
  (:require [clojure.test :refer :all]
            [clojure-for-the-brave-and-true.chapter3 :as ch3]))

;; Test are comparing results of my solutions to results of solutions written in book

(def body-part-left-leg {:name "left-leg" :size 10})
(def body-part-eye {:name "eye" :size 1})
(def symetric-body-part {:name "head" :size 2})

(deftest matching-part
  (is (= (ch3/matching-part body-part-left-leg)
         (ch3/matching-part-bs body-part-left-leg)))
  (is (= (ch3/matching-part symetric-body-part)
         (ch3/matching-part-bs symetric-body-part))))

(deftest symmetrize-body-parts
  (testing "That both functions return the same number of body parts"
    (is (= (count (ch3/symmetrize-body-parts ch3/asym-hobbit-body-parts))
           (count (ch3/symmetrize-body-parts-bs ch3/asym-hobbit-body-parts)))))
  (testing "That return set contains the same values"
    (is (= (set (ch3/symmetrize-body-parts ch3/asym-hobbit-body-parts))
           (set (ch3/symmetrize-body-parts-bs ch3/asym-hobbit-body-parts))))))

;; This exercise is without solution in book

(deftest symmetric-spider
  (testing "that function symmtrize and expands body parts"
    (is (= (count (ch3/symmetric-spider ch3/asym-hobbit-body-parts))
           (+ 12 (count (ch3/symmetrize-body-parts ch3/asym-hobbit-body-parts)))))))
