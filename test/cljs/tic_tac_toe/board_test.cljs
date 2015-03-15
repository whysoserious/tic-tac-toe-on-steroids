(ns tic-tac-toe.board-test
  (:require-macros [cemerick.cljs.test :refer (is deftest testing)])
  (:require [cemerick.cljs.test :as t]
            [tic-tac-toe.board :refer (->State o x _ make-move winner empty-state legal-move?)]))

(def test-state (->State [["O" "O" "O" "_" "_" "_" "_" "_" "_"]
                          ["_" "_" "X" "_" "_" "_" "_" "_" "_"]
                          ["_" "O" "O" "_" "_" "_" "_" "_" "_"]
                          ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                          ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                          ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                          ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                          ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                          ["_" "_" "_" "_" "_" "_" "O" "O" "O"]]
                         o
                         #{1}))

(deftest making-moves
  (testing "Making moves"
    (is (= (make-move test-state 1 1)
           (->State [["O" "O" "O" "_" "_" "_" "_" "_" "_"]
                     ["_" "O" "X" "_" "_" "_" "_" "_" "_"]
                     ["_" "O" "O" "_" "_" "_" "_" "_" "_"]
                     ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                     ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                     ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                     ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                     ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                     ["_" "_" "_" "_" "_" "_" "O" "O" "O"]]
                    x
                    #{1}))
        "legal move")
    (is (= (make-move (assoc test-state :small-board-ids #{2}) 2 0)
           (->State [["O" "O" "O" "_" "_" "_" "_" "_" "_"]
                     ["_" "_" "X" "_" "_" "_" "_" "_" "_"]
                     ["O" "O" "O" "_" "_" "_" "_" "_" "_"]
                     ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                     ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                     ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                     ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                     ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                     ["_" "_" "_" "_" "_" "_" "O" "O" "O"]]
                    x
                    #{1 3 4 5 6 7}))
        "move and win on small board")
    (is (= (make-move test-state 8 0)
           nil)
        "move into winning small board")
    (is (= (make-move test-state -1 1)
           nil)
        "invalid board-id")
    (is (= (make-move test-state 1 -1)
           nil)
        "invalid field-id")
    (is (= (make-move test-state 8 8)
           nil)
        "move into already used field")))

(deftest get-winner
  (testing "Getting a winner"
    (is (= (winner (empty-state x))
           nil)
        "no winners on an empty board")
    (is (= (winner (->State [["O" "O" "O" "_" "_" "_" "_" "_" "_"]
                             ["O" "O" "O" "_" "_" "_" "_" "_" "_"]
                             ["O" "O" "O" "_" "_" "_" "_" "_" "_"]
                             ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                             ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                             ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                             ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                             ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                             ["_" "_" "_" "_" "_" "_" "O" "O" "O"]]
                            x
                            #{1 3 4 5 6 7}))
           o)
        "no winners on an empty board")))

(deftest legal-move
  (testing "Check if move is legal"
    (is (false? (legal-move? test-state 0 0)) "move into occupied field")
    (is (false? (legal-move? test-state 3 0)) "move into inactive board")
    (is (false? (legal-move? test-state 1 2)) "move into active board and occupied field")
    (is (true? (legal-move? test-state 1 0)) "make legal move")))
