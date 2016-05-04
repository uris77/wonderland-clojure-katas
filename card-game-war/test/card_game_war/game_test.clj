(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))


;; fill in  tests for your game
(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (let [highest [:diamond 10]
          lowest  [:heart 6]]
      (is (= highest (play-round lowest highest)))))
  (testing "queens are higher rank than jacks"
    (let [queen [:diamond :queen]
          jack  [:diamond :jack]]
      (is (= queen (play-round queen jack)))))
  (testing "kings are higher rank than queens"
    (let [queen [:diamond :queen]
          king  [:diamond :king]]
      (is (= king (play-round queen king)))))
  (testing "aces are higher rank than kings"
    (let [king [:diamond :king]
          ace  [:diamond :ace]]
      (is (= ace (play-round ace king)))))
  (testing "if the ranks are equal, clubs beat spades"
    (let [clubs  [:club 10]
          spades [:spade 10]]
      (is (= clubs (play-round spades clubs)))))
  (testing "if the ranks are equal, diamonds beat clubs"
    (let [diamonds [:diamond 10]
          clubs    [:club 10]]
      (is (= diamonds (play-round diamonds clubs)))))
  (testing "if the ranks are equal, hearts beat diamonds"
    (let [hearts   [:heart 10]
          diamonds [:diamonds 10]]
      (is (= hearts (play-round hearts diamonds))))))

(deftest test-play-game
  (testing "the player loses when they run out of cards"
    (let [game-result (start-game)]
      (is (or (empty? (:player1 game-result))
              (empty? (:player2 game-result)))))))

