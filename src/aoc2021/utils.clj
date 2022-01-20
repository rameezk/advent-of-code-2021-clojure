(ns aoc2021.utils)

(defn get-input
  "Get the puzzle input for the day"
  [day]
  (let [path-to-file (str "resource/input_day_" day ".txt")]
    (slurp path-to-file)))

(defn parse-int
  "Parses the string representation of a number to an integer"
  [s]
  (Integer/parseInt s))
