(ns chapter4-test
  (:require [clojure.test :refer :all]
            [clojure-for-the-brave-and-true.chapter4 :as ch4]))

(deftest my-map
  (testing "that function increase the value of the items in vector"
    (is (= (ch4/my-map inc [1 2 3])
           '(2 3 4)))))


(deftest my-filter
  (testing "that function filters items that are bigger than 3"
    (is (= (ch4/my-filter #(> 3 %1) [4 0 1 6 2])
           [0 1 2]))))


(deftest my-some
  (testing "that function returns true value from coll when there is true value"
    (is (= (ch4/my-some #(> 3 %1) [5 8 1])
           true)))
  (testing "that function returns nil, if there is no true value"
    (is (= (ch4/my-some #(> 3 %1) [7 9 3])
           nil))))
