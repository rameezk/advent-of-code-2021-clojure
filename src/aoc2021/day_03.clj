(ns aoc2021.day-03
  (:require [clojure.string :as str]
            [aoc2021.utils :refer [get-input]]))

(def day "03")
(def sample-input '("00100"
                    "11110"
                    "10110"
                    "10111"
                    "10101"
                    "01111"
                    "00111"
                    "11100"
                    "10000"
                    "11001"
                    "00010"
                    "01010"))

(def input
  (str/split-lines
   (get-input day)))

(defn- slices
  [bs]
  (apply mapv vector bs))

(defn flip-bit
  "Flips a flipping bit!"
  [b]
  (if
   (= b \0) \1 \0))

(defn- most-frequent-bit
  "Get the most frequent bit in a bit string"
  [bs]
  (->> bs
       frequencies
       (sort-by val)
       last
       first))

(defn bit-str->decimal
  "Convert bit string to decimal"
  [bs]
  (Long/parseLong bs 2))

(defn- calculate-power-consumption
  "Calculate the power consumption"
  [input]
  (let [bits         (slices input)
        gamma-bits   (map most-frequent-bit bits)
        epsilon-bits (map flip-bit gamma-bits)
        gamma-rate   (->> gamma-bits
                          str/join
                          bit-str->decimal)
        epsilon-rate (->> epsilon-bits
                          str/join
                          bit-str->decimal)]
    (* gamma-rate epsilon-rate)))


(defn- filter-by-bit-at-position
  "Filter by bit as a specific position"
  [bit pos xs]
  (filter
   #(= (nth % pos) bit)
   xs))

(+ 1 1)

(defn- slice-at-position
  "Get the vertical slice at a position"
  [pos m]
  (-> (slices m)
      (nth pos)))

(defn- calculate-rating-builder
  "Calculate the rating"
  [bit-selector-fn input]
  (bit-str->decimal
   (first
    (loop [bit-strings input
           pos         0]
      (if (> (count bit-strings) 1)
        (let [slice                (slice-at-position pos bit-strings)
              bit                  (bit-selector-fn slice)
              filtered-bit-strings (filter-by-bit-at-position bit pos bit-strings)]
          (recur filtered-bit-strings (inc pos)))
        bit-strings)))))

(defn- oxygen-rating-bit-selector
  [bs]
  (let [f (frequencies bs)
        count-0s (get f \0)
        count-1s (get f \1)]
    (cond
      (= count-0s count-1s) \1
      (> count-0s count-1s) \0
      (< count-0s count-1s) \1)))

(defn- co2-scrubber-rating-bit-selector
  [bs]
  (let [f (frequencies bs)
        count-0s (get f \0)
        count-1s (get f \1)]
    (cond
      (= count-0s count-1s) \0
      (> count-0s count-1s) \1
      (< count-0s count-1s) \0)))

(defn- calculate-oxygen-rating
  [input]
  (calculate-rating-builder oxygen-rating-bit-selector input))

(defn- calculate-co2-scrubber-rating
  [input]
  (calculate-rating-builder co2-scrubber-rating-bit-selector input))

(defn- calculate-life-support-rating
  [input]
  (let [oxygen-rating (calculate-oxygen-rating input)
        co2-scrubber-rating (calculate-co2-scrubber-rating input)]
    (* oxygen-rating co2-scrubber-rating)))

;; p1
(calculate-power-consumption input)
;; => 3885894

;; p2
(calculate-life-support-rating input)
;; => 4375225




