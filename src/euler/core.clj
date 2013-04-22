(ns euler.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

;; 1

(defn mult-of-3? [n]
  (= 0 (mod n 3)))

(defn mult-of-5? [n]
  (= 0 (mod n 5)))

(defn mult-of-3-or-5? [n]
  (or (mult-of-3? n) (mult-of-5? n)))

(->>
  (range 1000)
  (filter mult-of-3-or-5?)
  (apply +))

;; 2

(def fib
  (memoize
    (fn [n]
      (cond (= n 0) 0N
            (= n 1) 1N
            (= n 2) 2N
            :else (+ (fib (- n 1))
                     (fib (- n 2)))))))

(defn fib-seq []
  (map fib (range)))
  
(take 20 (fib-seq))

"foo"

(->>
 (fib-seq)
 (filter #(< % 2000))
 (filter even?))
