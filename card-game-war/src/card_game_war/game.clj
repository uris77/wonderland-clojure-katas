(ns card-game-war.game)

;; feel free to use these cards or use your own data structure
(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def cards
  (for [suit suits
        rank ranks]
    [suit rank]))

(defn deal-cards
  [c]
  (let [shuffled (shuffle c)
        player1  (take 26 shuffled)
        player2  (drop 26 shuffled)]
    {:player1 (vec player1)
     :player2 (vec player2)}))

(defn highest-rank
  [card1 card2]
  (let [rank1  (last card1)
        rank2  (last card2)
        index1 (.indexOf ranks rank1)
        index2 (.indexOf ranks rank2)]
    (cond
      (> index1 index2) card1
      (> index2 index1) card2
      :else nil)))

(defn highest-suit
  [card1 card2]
  (let [suit1  (first card1)
        suit2  (first card2)
        index1 (.indexOf suits suit1)
        index2 (.indexOf suits suit2)]
    (cond
      (> index1 index2) card1
      (> index2 index1) card2
      :else nil)))

(defn play-round [player1-card player2-card]
  (or (highest-rank player1-card player2-card)
      (highest-suit player1-card player2-card)))

(defn winner-takes-card
  [winner-cards card]
  (->> (-> winner-cards
           (conj card)
           (conj (first cards)))
       rest
       vec))

(defn play-game [player1-cards player2-cards]
  (loop [cards1 player1-cards
         cards2 player2-cards]
    (let [card1  (first cards1)
          card2  (first cards2)
          winner (play-round card1 card2)]
      (if (= card1 winner)
        (let [c1 (winner-takes-card cards1 card2) 
              c2 (vec (rest cards2))]
          (if (empty? c2)
            {:player1 c1
             :player2 c2}
            (recur c1 c2)))
        (let [c2 (winner-takes-card cards2 card1)
              c1 (vec (rest cards1))]
          (if (empty? c1)
            {:player1 c1
             :player2 c2}
            (recur c1 c2)))))))

(defn start-game []
  (let [dealt-cards (deal-cards cards)]
    (play-game (:player1 dealt-cards)
               (:player2 dealt-cards))))

