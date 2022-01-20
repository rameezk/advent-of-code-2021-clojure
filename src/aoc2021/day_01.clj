(ns aoc2021.day-01
  (:require [clojure.string :as str]
            [aoc2021.utils :refer [get-input parse-int]]))

(def day "01")

(def sample-input '(199
                    200
                    208
                    210
                    200
                    207
                    240
                    269
                    260
                    263))

(defn- count-increases [input]
  (->> input
       (partition 2 1)
       (filter (fn [[x y]] (> y x)))
       count))

;; Part 1
(->> (get-input day)
     (str/split-lines)
     (map parse-int)
     count-increases)
;; => 1477

;; Part 2
(->> (get-input day)
     (str/split-lines)
     (map parse-int)
     (partition 3 1)
     (map #(reduce + 0 %))
     count-increases)
;; => 1523

