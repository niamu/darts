(ns darts.state)

(def initial-state
  {:throws []
   :checkout-throws []
   :score 501
   :tally-guess ""
   :score-guess ""})

(defonce app-state
  (atom (merge {} initial-state)))
