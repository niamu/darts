(ns darts.darts
  (:require [clojure.string :as string]))

(def board-items
  [20 1 18 4 13 6 10 15 2 17 3 19 7 16 8 11 14 9 12 5])

(def board
  "The total set of all possible locations on a dart board"
  (->> (reduce (fn [accl multiplier]
                 (conj accl (map #(vector multiplier %) board-items)))
               [[[1 25] [2 25]]] ;; Bullseye
               [1 2 3])
       (apply concat)))

(defn throw-dart
  "Throw a dart"
  []
  (rand-nth board))

(defn score-dart
  "Score a single dart"
  [dart]
  (apply * dart))

(defn sum-darts
  "Sum of all darts"
  [darts]
  (->> (map score-dart darts)
       (apply +)))

(defn dart->id
  "Name a single dart"
  [[multiplier n]]
  (if (= n 25)
    (if (= 2 multiplier) "BULL" (str n))
    (str (case multiplier
           2 "D"
           3 "T"
           "")
         n)))

(defn id->dart
  "Return dart vector from name"
  [s]
  (let [s (string/upper-case s)]
    (cond (or (= s "BULL") (= s "D25")) [2 25]
          (= s "25") [1 25]
          :else (let [[multiplier n] (drop 1 (re-find #"^([DT])?([0-9]+)" s))
                      n #?(:clj (Integer/parseInt n)
                           :cljs (js/parseInt n))]
                  (when (<= n 20)
                    [(if multiplier (case (string/upper-case multiplier)
                                      "D" 2
                                      "T" 3)
                         1) n])))))

(defn throw-darts
  "Throw 3 darts and display the sum"
  [& args]
  (let [thrown (repeatedly 3 throw-dart)]
    (map dart->id thrown)))
