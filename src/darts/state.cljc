(ns darts.state)

(def initial-state
  {:throws []
   :checkout-throws []
   :score 501
   :guess ""})

(defonce app-state
  (atom (merge {} initial-state)))
