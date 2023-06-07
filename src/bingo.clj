(ns bingo
    (:require [clojure.string :as str]
     [clojure.java.shell :as sh]))

(def thai-chars (into [] (remove #{"ฤ" "ฦ"} (map #(String. (Character/toChars %)) (range 3585 3631)))))

(def eng-chars (mapv #(String. (Character/toChars %)) (range 97 123)))

(defonce chars (atom nil))

(defn thai []
      (reset! chars (shuffle thai-chars))
      nil)

(defn eng []
      (reset! chars (shuffle eng-chars))
      nil)

(defn number []
      (reset! chars (shuffle (range 0 12)))
      nil)

(defn bingo [] (let [c (first @chars)] (swap! chars next) c))

(comment
 (thai)
 (bingo)
 )

(defn bingo2 [] (let [c (first @chars)] (swap! chars next) (sh/sh "say" (str c)) c))

(defn bingo3 [& [t]]
      (doseq [c @chars]
             (println "                               " c)
             (sh/sh "say" (str c))
             (Thread/sleep (or t 5000))))

(defn bingo4 []
      (sh/sh "say" "-r" "10" (str/join " " @chars)))

(defn thai-table []
      (doseq [line (concat (partition-all 3 (take 9 (shuffle thai-chars))) (repeat 10 nil))]
             (println (str/join " " line))))

(defn eng-table []
      (doseq [line (concat (partition-all 3 (take 9 (shuffle eng-chars))) (repeat 10 nil))]
             (println (str/join " " line))))

(defn number-table []
      (doseq [line (concat (partition-all 3 (take 9 (shuffle (range 1 11)))) (repeat 10 nil))]
             (println (str/join " " line))))


(comment
 (thai-table)
 (Character/codePointAt "a" 0)
 )
