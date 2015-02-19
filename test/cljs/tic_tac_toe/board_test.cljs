(ns tic-tac-toe.board-test
  (:require-macros [cemerick.cljs.test :refer (is deftest testing)])
  (:require [cemerick.cljs.test :as t]
            [tic-tac-toe.board :as b]))

(def test-state (b/->State [["O" "O" "O" "_" "_" "_" "_" "_" "_"]
                            ["_" "_" "X" "_" "_" "_" "_" "_" "_"]
                            ["_" "O" "O" "_" "_" "_" "_" "_" "_"]
                            ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                            ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                            ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                            ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                            ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                            ["_" "_" "_" "_" "_" "_" "O" "O" "O"]]
                           b/o
                           #{1}))

(deftest making-moves
  (testing "Making moves"
    (is (= (b/make-move test-state 1 1)
           (b/->State [["O" "O" "O" "_" "_" "_" "_" "_" "_"]
                       ["_" "O" "X" "_" "_" "_" "_" "_" "_"]
                       ["_" "O" "O" "_" "_" "_" "_" "_" "_"]
                       ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                       ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                       ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                       ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                       ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                       ["_" "_" "_" "_" "_" "_" "O" "O" "O"]]
                      b/x
                      #{1})) "legal move")
    (is (= (b/make-move (assoc test-state :small-board-ids #{2}) 2 0)
           (b/->State [["O" "O" "O" "_" "_" "_" "_" "_" "_"]
                       ["_" "_" "X" "_" "_" "_" "_" "_" "_"]
                       ["O" "O" "O" "_" "_" "_" "_" "_" "_"]
                       ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                       ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                       ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                       ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                       ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                       ["_" "_" "_" "_" "_" "_" "O" "O" "O"]]
                      b/x
                      #{1 3 4 5 6 7})) "move and win on small board")
    (is (= (b/make-move test-state 8 0)
           nil) "move into winning small board")
    (is (= (b/make-move test-state -1 1)
           nil) "invalid board-id")
    (is (= (b/make-move test-state 1 -1)
           nil) "invalid field-id")
    (is (= (b/make-move test-state 8 8)
           nil) "move into already used field")))

(deftest get-winner
  (testing "Getting a winner"
    (is (= (b/winner (b/empty-state b/x))
           nil) "no winners on an empty board")
    (is (= (b/winner (b/->State [["O" "O" "O" "_" "_" "_" "_" "_" "_"]
                                 ["O" "O" "O" "_" "_" "_" "_" "_" "_"]
                                 ["O" "O" "O" "_" "_" "_" "_" "_" "_"]
                                 ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                                 ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                                 ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                                 ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                                 ["_" "_" "_" "_" "_" "_" "_" "_" "_"]
                                 ["_" "_" "_" "_" "_" "_" "O" "O" "O"]]
                                b/x
                                #{1 3 4 5 6 7}))
           b/o) "no winners on an empty board")))
