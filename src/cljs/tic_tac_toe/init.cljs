(ns tic-tac-toe.init
  (:require [reagent.core :as reagent :refer [render-component]]
            [tic-tac-toe.core :refer [hiccup-board]]))

(defn init! []
  (render-component [(fn [] (hiccup-board))] (.getElementById js/document "app")))
