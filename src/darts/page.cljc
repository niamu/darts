(ns darts.page
  (:require [darts.darts :as darts]
            [darts.state :as state]
            [rum.core :as rum]
            #?(:clj [hiccup.page :as hpage])))

(def title "Dart Scoring Trainer")

#?(:clj
   (defn wrap
     "Server-side wrapping of a React Root in HTML markup"
     [react-root]
     (hpage/html5
      [:head
       [:title title]
       [:meta {:name "apple-mobile-web-app-capable" :content "yes"}]
       [:meta {:name "apple-mobile-web-app-status-bar-style"
               :content "black-translucent"}]
       [:meta {:name "apple-mobile-web-app-title" :content title}]
       [:meta {:name "application-name" :content title}]
       [:meta {:name "viewport" :content "width=device-width,initial-scale=1"}]
       #_(hpage/include-css "/css/screen.css")]
      [:body
       [:h1 title]
       [:div#app (rum/render-html react-root)]
       (hpage/include-js "/js/darts.js")])))

(defn slice
  [idx {:keys [n width height darts]}]
  [:g {:id (str "slice" n)
       :key idx
       :transform (str "translate(" (- height (/ width 2)) ", " 0 ")")}
   [:g {:fill (cond (contains? (set darts) (str n)) "blue"
                    (even? idx) "#1E1E1E"
                    (odd? idx) "#D9D9D1")
        :transform (str "rotate(" (* idx (/ 360 (count darts/board-items)))
                        ", " (- (/ width 2) 1.5) ", " height ")")}
    [:g {:id (str "slice_" n)
         :transform (str "translate(" 2 ", " 17 ")")}
     [:path {:d (str "M41.4751,262 L66.7431,105.343 C58.4981,104.032 "
                     "50.0461,103.343 41.4331,103.343 C33.0351,103.343 "
                     "24.7901,103.993 16.7431,105.24 L41.4751,262 Z")}]
     [:path {:d (str "M69.5303,88.0649 C60.3783,86.6089 50.9953,85.8429 "
                     "41.4323,85.8429 C32.1073,85.8429 22.9513,86.5659 "
                     "14.0153,87.9519 L0.7273,3.7329 C14.1403,1.6209 "
                     "27.8863,0.5119 41.8933,0.5119 C55.9253,0.5119 "
                     "69.6953,1.6249 83.1303,3.7449 L69.5303,88.0649 Z")}]]
    [:path {:fill (cond (contains? (set darts) (str "D" n)) "blue"
                        (even? idx) "#00591E"
                        (odd? idx) "#B01800")
            :id (str "slice_D" n)
            :d (str "M43.8936,17.5117 C57.9246,17.5117 71.6956,18.6247 "
                    "85.1296,20.7447 L87.9166,3.4667 C73.5746,1.2017 "
                    "58.8736,0.0117 43.8936,0.0117 C28.9586,0.0117 "
                    "14.3016,1.1937 -0.0004,3.4457 L2.7276,20.7337 "
                    "C16.1406,18.6207 29.8866,17.5117 43.8936,17.5117")}]
    [:path {:fill (cond (contains? (set darts) (str "T" n)) "blue"
                        (even? idx) "#00591E"
                        (odd? idx) "#B01800")
            :id (str "slice_T" n)
            :d (str "M43.4326,120.3428 C52.0456,120.3428 60.4976,121.0318 "
                    "68.7436,122.3428 L71.5306,105.0648 C62.3776,103.6098 "
                    "52.9956,102.8428 43.4326,102.8428 C34.1076,102.8428 "
                    "24.9516,103.5668 16.0156,104.9518 L18.7426,122.2398 "
                    "C26.7906,120.9928 35.0356,120.3428 43.4326,120.3428")}]]])

(defn bullseye
  [{:keys [board-width width darts]}]
  (let [stroke-width 2
        center (- (/ board-width 2) width (/ stroke-width 2))]
    [:g {:stroke "#D9D9D1"
         :stroke-width stroke-width
         :transform (str "translate(" center ", " center ")")}
     [:circle {:id "slice_25"
               :fill (if (contains? (set darts) "25") "blue" "#00591E")
               :cx width
               :cy width
               :r (+ width stroke-width)}]
     [:circle {:id "slice_BULL"
               :fill (if (contains? (set darts) "BULL") "blue" "#B01900")
               :cx width
               :cy width
               :r (/ width 2)}]]))

(defn end-of-set?
  [state]
  (= 3 (-> (partition-all 3 (:throws state))
           last count)))

(defn scoreboard
  [state]
  [:div
   [:p.total (- 501
                (-> (map darts/id->dart
                         (->> (partition-all 3 (:throws state))
                              drop-last
                              (apply concat)))
                    darts/sum-darts))]
   (when (not-empty (:throws state))
     [:ul (map (fn [set]
                 [:li (apply str (interpose ", " set))])
               (-> (partition-all 3 (:throws state))
                   last vector))])])

(defn throw-dart
  []
  (do (swap! state/app-state update-in [:throws]
             #(conj % (-> (darts/throw-dart)
                          darts/dart->id)))
      (swap! state/app-state update-in [:score]
             #(- 501
                 (-> (map darts/id->dart
                          (get @state/app-state
                               :throws))
                     darts/sum-darts)))
      (swap! state/app-state assoc-in [:guess] "")))

(defn tally-guess
  [state]
  [:form
   (merge {:name "tally-guess"}
          #?(:cljs {:onSubmit
                    (fn [e]
                      (.preventDefault e)
                      (when (= (-> (map darts/id->dart
                                        (->> (partition-all 3 (:throws state))
                                             (filter (fn [set]
                                                       (= 3 (count set))))
                                             last))
                                   darts/sum-darts)
                               (js/parseInt (:guess state)))
                        (throw-dart)))}))
   [:input {:type "text"
            :name "guess"
            :placeholder "Total for set..."
            :value (:guess state)
            :onInput #?(:clj nil
                        :cljs (fn [e]
                                (swap! state/app-state assoc-in [:guess]
                                       (->> (re-seq #"\d+"
                                                    (.. e -target
                                                        -value))
                                            (apply str)))))}]
   [:input {:type "submit"
            :value "Check"}]])

(rum/defc board < rum/reactive
  []
  [:div
   (scoreboard (rum/react state/app-state))
   (if (end-of-set? (rum/react state/app-state))
     (tally-guess (rum/react state/app-state))
     [:button {:onClick (fn [_] #?(:cljs (throw-dart)))} "Throw Darts"])
   (let [width 560
         darts (->> (:throws @state/app-state)
                    (partition-all 3)
                    last)]
     [:svg {:width (str (+ width 10) "px")
            :height (str (+ width 10) "px")
            :viewBox (str "0 0 " (+ width 10) " " (+ width 10))
            :style {:padding (str 1 "em")}
            :version "1.1"
            :xmlns "http://www.w3.org/2000/svg"}
      (map-indexed #(slice %1 {:n %2
                               :width 90
                               :height (/ width 2)
                               :darts darts})
                   darts/board-items)
      (bullseye {:board-width width
                 :width 25
                 :darts darts})])])
