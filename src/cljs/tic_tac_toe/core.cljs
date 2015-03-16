(ns tic-tac-toe.core
  (:require [reagent.core :as reagent :refer [atom]]
            [clojure.string :as string]
            [tic-tac-toe.board :refer [empty-state x o get-stone make-move legal-move?]]))

(def state (atom (empty-state (rand-nth [x o]))))

;; Accessing / mutating DOM

(defn field-element-id [board-id field-id]
  (string/join "-" ["field" board-id field-id]))

(defn field-element [board-id field-id]
  (.getElementById js/document (field-element-id board-id field-id)))

(defn set-element-value [board-id field-id stone]
  (set! (.-innerHTML (field-element board-id field-id)) stone))

;; Functions accessing state of an application

(defn field-value [board-id field-id]
  (get-stone @state board-id field-id))

(defn on-click-field [board-id field-id]
  (when (legal-move? @state board-id field-id)
    (swap! state make-move board-id field-id)
    (set-element-value board-id field-id (:current-stone @state))))

(defn on-mouse-over-field [board-id field-id]
  (when (legal-move? @state board-id field-id)
    (set-element-value board-id field-id (:current-stone @state))))

(defn on-mouse-out-field [board-id field-id]
  (set-element-value board-id field-id (field-value board-id field-id)))

(defn hiccup-class [board-id field-id] 
  (if (legal-move? @state board-id field-id)
    "legal-move"
    "illegal-move"))

;; Generating HTML

(defn hiccup-field [board-id field-id]
  [:a
   {:id (field-element-id board-id field-id)
    :href "#"
    :class (hiccup-class board-id field-id)
    :on-click #(on-click-field board-id field-id)
    :on-mouse-over #(on-mouse-over-field board-id field-id)
    :on-mouse-out #(on-mouse-out-field board-id field-id)}
   (field-value board-id field-id)])

(defn hiccup-small-board [board-id]
  [:table {:id (str "small-board-" board-id)}  
   [:tbody 
    [:tr  
     [:td (hiccup-field board-id 0)]
     [:td (hiccup-field board-id 1)]  
     [:td (hiccup-field board-id 2)]]  
    [:tr  
     [:td (hiccup-field board-id 3)]  
     [:td (hiccup-field board-id 4)]  
     [:td (hiccup-field board-id 5)]]  
    [:tr  
     [:td (hiccup-field board-id 6)]  
     [:td (hiccup-field board-id 7)]  
     [:td (hiccup-field board-id 8)]]]])

(defn hiccup-board []
  [:table {:id "board"}
   [:tbody
    [:tr
     [:td (hiccup-small-board 0)][:td (hiccup-small-board 1)][:td (hiccup-small-board 2)]]
    [:tr
     [:td (hiccup-small-board 3)][:td (hiccup-small-board 4)][:td (hiccup-small-board 5)]]
    [:tr
     [:td (hiccup-small-board 6)] [:td (hiccup-small-board 7)][:td (hiccup-small-board 8)]]]])
