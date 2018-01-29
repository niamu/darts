(ns darts.state)

(defonce app-state
  (atom {:throws []
         :score 501
         :guess ""}))
