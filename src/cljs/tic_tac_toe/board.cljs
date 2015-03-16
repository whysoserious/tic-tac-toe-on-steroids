(ns tic-tac-toe.board
  (:require [clojure.string :as string]))

(def x "X")

(def o "O")

(def _ "_")

(defrecord State [board current-stone small-board-ids])

(defn empty-board []
  (vec (repeat 9 (vec (repeat 9 _)))))

(defn empty-state [current-stone]
  (->State (empty-board) current-stone (set (range 0 9))))

(defn put-stone [state board-id field-id]
  (assoc-in state [:board board-id field-id] (:current-stone state)))

(defn get-stone [state board-id field-id]
  (get-in (:board state) [board-id field-id]))

(defn field-empty? [state board-id field-id]
  (= (get-stone state board-id field-id) _))

;; TODO move to letfn in make-move?
(defn update-next-stone [state]
  (let [{current-stone :current-stone} state
        next-stone (cond (= current-stone x) o (= current-stone o) x)]
    (assoc state :current-stone next-stone)))

(def winning-triplets [[0 1 2] [3 4 5] [6 7 8]
                       [0 3 6] [1 4 7] [2 5 8]
                       [0 4 8] [2 4 6]])

(def small-board-winner
  (memoize
   (fn [board]
     (letfn [(select-stones [board ids] (map #(board %) ids))
             (same [stones stone] (if (every? #(= stone %) stones) stone nil))
             (is-winner? [line] (or (same line x) (same line o)))]
       (->> (map #(select-stones board %) winning-triplets)
            (filter is-winner?)
            flatten
            first)))))

(defn winner [state]
  (->> state :board (map #(small-board-winner %)) vec small-board-winner))

(defn legal-move? [state board-id field-id]
  (and (nil? (winner state))
       (field-empty? state board-id field-id)
       (contains? (:small-board-ids state) board-id)))

(defn playable-boards [board]
  (->> board
       (map #(small-board-winner %))
       (zipmap (range 0 9))
       (filter #(nil? (second %)))
       keys
       set))

(defn available-small-board-ids [board field-id]
  (if (some? (small-board-winner (board field-id)))
    (playable-boards board)
    #{field-id}))

(defn update-small-board-ids [state field-id]
  (let [{board :board} state]
    (assoc state :small-board-ids (available-small-board-ids board field-id))))

(defn make-move [state board-id field-id]
  (when (legal-move? state board-id field-id)
    (-> state
        (put-stone board-id field-id)
        update-next-stone
        (update-small-board-ids field-id))))
