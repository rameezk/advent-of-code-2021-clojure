(ns aoc2021.day-02
  (:require [aoc2021.utils :refer [get-input parse-int]]
            [clojure.string :as str]))

(def day "02")
(def sample-input '("forward 5"
                    "down 5"
                    "forward 8"
                    "up 3"
                    "down 8"
                    "forward 2"))
(def input (str/split-lines (get-input day)))
(def initial-position {:horizontal 0
                       :depth 0
                       :aim 0})

(defn- parse-command
  [line]
  (let [matches (re-matches #"^(.*)\s(\d+)$" line)
        direction (nth matches 1)
        amount (nth matches 2)]
    [(keyword direction) (parse-int amount)]))

(defn- move
  [submarine raw-command]
  (let [[direction amount] (parse-command raw-command)]
    (cond-> submarine
      (= direction :forward) (-> (update :horizontal + amount))
      (= direction :down)    (-> (update :depth + amount))
      (= direction :up)      (-> (update :depth - amount)))))

(defn- move-with-aim
  [submarine raw-command]
  (let [[direction amount] (parse-command raw-command)]
    (cond-> submarine
      (= direction :forward) (-> (update :horizontal + amount)
                                 (update :depth + (* amount (:aim submarine))))
      (= direction :down)    (-> (update :aim + amount))
      (= direction :up)      (-> (update :aim - amount)))))

(defn- calculate-position
  [mover input]
  (->> input
       (reduce mover initial-position)))

(defn- solve
  [mover]
  (let [{:keys [horizontal depth]} (calculate-position mover input)]
    (* horizontal depth)))

;; p1
(solve move)
;; => 1383564

;; p2
(solve move-with-aim)
;; => 1488311643
