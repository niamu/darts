(ns darts.style
  (:require [garden.core :refer [css]]))

(def highlight
  ;; https://color.adobe.com/Reevoo-Orange-Highlights-color-theme-6420664
  "#FED650")

(def theme
  ;; https://color.adobe.com/DART-CottonBelt-4-color-theme-9174358/
  ["#101E1F" ;; Black-blue
   "#4E5446" ;; Olive
   "#E7E9D3" ;; White-green
   "#462C1B" ;; Brown-red
   "#292323" ;; Dark purple
   ])

(def board
  ;; https://color.adobe.com/Darts-Board-color-theme-1966503
  ["#528C6F" ;; Green
   "#FEE2D0" ;; Light Red
   "#D95753" ;; Red
   "#FFFFFF" ;; White
   "#0A0002" ;; Black
   ])

(def style
  (css [:*
        {:box-sizing :border-box}]
       [:html :body :div#app :div.flex
        {:height "100%"}]
       [:body
        {:margin 0
         :background (theme 2)
         :color (theme 3)
         :font-family :sans-serif}]
       [:h1
        {:text-align :center
         :margin [["1em" 0]]
         :font-size "1.75em"
         :font-weight 300
         :text-shadow "1px 1px 2px rgba(0,0,0,0.2)"}]
       [:div#app
        {:text-align :center}
        [:div.flex
         {:display :flex
          :flex-direction :column}]
        [:div.stretch
         {:flex-grow 1
          :overflow-y :auto}]
        [:p.total
         {:font-size "2em"
          :margin "0.25em"}]
        [:p.darts-list
         [:&:before
          {:content "' '"
           :display :inline-block}]]
        [:svg
         {:width "100%"
          :max-width "400px"
          :height "100%"
          :padding "0 1em"}]
        [:div.fixed-bottom
         {:flex-shrink 0
          :padding "10px"
          :height "145px"}
         [:div.scoreboard
          [:p {:margin [[0 0 "0.5em"]]}]]
         [:form
          {:display :flex
           :flex-direction :row
           :width "100%"}]
         ["input[name=score-guess]:disabled"
          {:border [["1px" :solid :red]]}]
         [:input :span
          {:display :inline-block
           :max-width "4em"
           :text-align :center
           :border :none
           :border-radius "4px"
           :padding "6px"
           :font-size "16px"}]
         [:button
          [:&.inline
           {:display :inline-block
            :margin-left "10px"}]
          {:display :block
           :margin [[0 :auto]]
           :width "100%"
           :max-width "400px"
           :font-size "16px"
           :padding "6px"
           :border :none
           :border-radius "4px"
           :background "#4E5447"
           :color "#E7E9D4"
           :cursor :pointer}]
         ["button:disabled"
          {:cursor :auto}]]]))
