(ns euler.core)

;; 1

; If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.

; Find the sum of all the multiples of 3 or 5 below 1000.


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
; Each new term in the Fibonacci sequence is generated by adding the previous two terms. By starting with 1 and 2, the first 10 terms will be:

; 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...

; By considering the terms in the Fibonacci sequence whose values do not exceed four million, find the sum of the even-valued terms.

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

(comment
  (->>
    (fib-seq)
    (filter #(< % 2000))
    (filter even?)))

(->>
  (iterate (fn [[a b]] [b (+ a b)]) [0 1])
  (map first)
  (filter even?)
  (take-while #(< % 4000000))
  (reduce +)
  )

;; 3

; The prime factors of 13195 are 5, 7, 13 and 29.

; What is the largest prime factor of the number 600851475143 ?

(defn prime-factors [n]
  (for [f1 (range 2 n)
        f2 (range 2 n)
        :when (= (* f1 f2) n)]
    [f1 f2]))

(defn prime? [n]
  (empty? (prime-factors n)))

(prime-factors 100)
(prime? 10)
(prime? 7)

; (max (apply concat (prime-factors 600851475143)))

;; 4
;; A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.
;;
;; Find the largest palindrome made from the product of two 3-digit numbers.

(defn two-digit-pairs []
  (for [a (range 10 99)
        b (range 10 99)]
    [a b]))

(defn three-digit-pairs []
  (for [a (range 100 999)
        b (range 100 999)]
    [a b]))

(comment
  (->> (three-digit-pairs)
       (map (partial apply str))
       (filter (fn [s] (= (seq s) (reverse s))))
       last)
)

; Smallest multiple
; Problem 5

; 2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.

; What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?
(defn is-divisible [start end n]
  ; (= #{0} (set (map rem (repeat n) (range start (inc end)))))
  (if (some pos? (map rem (repeat n) (range start (inc end))))
    false
    true))

(comment
(is-divisible 1 10 2520)
(is-divisible 1 10 2521)

(is-divisible 1 20 2520)
(some identity (map #(if (is-divisible 1 17 %) % nil) (map inc (range))))

  )

;; Sum square difference
;; Problem 6
;;
;; The sum of the squares of the first ten natural numbers is,
;; 1^2 + 2^2 + ... + 10^2 = 385
;;
;; The square of the sum of the first ten natural numbers is,
;; (1 + 2 + ... + 10)^2 = 552 = 3025
;;
;; Hence the difference between the sum of the squares of the first ten natural numbers and the square of the sum is 3025 − 385 = 2640.
;;
;; Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum.

(defn square [n]
  (* n n))

; (apply + (map square (range 1 11)))
; (square (apply + (range 1 11)))
(- (square (apply + (range 1 101)))
   (apply + (map square (range 1 101))))


;; 10001st prime
;; Problem 7
;;
;; By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.
;;
;; What is the 10 001st prime number?

(defn prime? [n]
  ;; look at AKS
  (filter pos? (map #(mod % 1) (map / (repeat n) (range 2 (inc n))))))

(mod 4 1)
(mod 4/3 1)
(prime? 2)
(prime? 3)
(prime? 5)
(prime? 8)
(def prime-seq (filter prime? (range)))
(take 6 prime-seq)

;;Largest product in a series
;;Problem 8
;;Find the greatest product of five consecutive digits in the 1000-digit number.
;;
;;73167176531330624919225119674426574742355349194934
;;96983520312774506326239578318016984801869478851843
;;85861560789112949495459501737958331952853208805511
;;12540698747158523863050715693290963295227443043557
;;66896648950445244523161731856403098711121722383113
;;62229893423380308135336276614282806444486645238749
;;30358907296290491560440772390713810515859307960866
;;70172427121883998797908792274921901699720888093776
;;65727333001053367881220235421809751254540594752243
;;52584907711670556013604839586446706324415722155397
;;53697817977846174064955149290862569321978468622482
;;83972241375657056057490261407972968652414535100474
;;82166370484403199890008895243450658541227588666881
;;16427171479924442928230863465674813919123162824586
;;17866458359124566529476545682848912883142607690042
;;24219022671055626321111109370544217506941658960408
;;07198403850962455444362981230987879927244284909188
;;84580156166097919133875499200524063689912560717606
;;05886116467109405077541002256983155200055935729725
;;71636269561882670428252483600823257530420752963450

(let [one-k-num
      "7316717653133062491922511967442657474235534919493496983520312774506326239578318016984801869478851843858615607891129494954595017379583319528532088055111254069874715852386305071569329096329522744304355766896648950445244523161731856403098711121722383113622298934233803081353362766142828064444866452387493035890729629049156044077239071381051585930796086670172427121883998797908792274921901699720888093776657273330010533678812202354218097512545405947522435258490771167055601360483958644670632441572215539753697817977846174064955149290862569321978468622482839722413756570560574902614079729686524145351004748216637048440319989000889524345065854122758866688116427171479924442928230863465674813919123162824586178664583591245665294765456828489128831426076900422421902267105562632111110937054421750694165896040807198403850962455444362981230987879927244284909188845801561660979191338754992005240636899125607176060588611646710940507754100225698315520005593572972571636269561882670428252483600823257530420752963450"]
  (->> one-k-num
       (map str)
       (map read-string)
       (partition 5 1)
       (map (partial apply *))
       (apply max)))

;;Special Pythagorean triplet
;;Problem 9
;;
;;A Pythagorean triplet is a set of three natural numbers, a < b < c, for which,
;;a^2 + b^2 = c^2
;;
;;For example, 3^2 + 4^2 = 9 + 16 = 25 = 5^2.
;;
;;There exists exactly one Pythagorean triplet for which a + b + c = 1000.
;;Find the product abc.
;;
(defn pythagorean-triplet? [a b c]
  (= (+ (square a) (square b)) (square c)))

(pythagorean-triplet? 3 4 5)

#_(first (for [a (range)
             b (range)
             c (range)
             :when (and (< a b)
                        (< b c)
                        (= 12 (+ a b c)))]
         [a b c]))


; Summation of primes
; Problem 10

; The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.

; Find the sum of all the primes below two million.

(defn sum-of-primes [max-n]
  (reduce + (filter prime? (range 2 max-n))))

(comment
  (sum-of-primes 10)
  )

; Largest product in a grid
; Problem 11

; In the 20×20 grid below, four numbers along a diagonal line have been marked in red.

; 08 02 22 97 38 15 00 40 00 75 04 05 07 78 52 12 50 77 91 08
; 49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48 04 56 62 00
; 81 49 31 73 55 79 14 29 93 71 40 67 53 88 30 03 49 13 36 65
; 52 70 95 23 04 60 11 42 69 24 68 56 01 32 56 71 37 02 36 91
; 22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80
; 24 47 32 60 99 03 45 02 44 75 33 53 78 36 84 20 35 17 12 50
; 32 98 81 28 64 23 67 10^26^38 40 67 59 54 70 66 18 38 64 70
; 67 26 20 68 02 62 12 20 95^63^94 39 63 08 40 91 66 49 94 21
; 24 55 58 05 66 73 99 26 97 17^78^78 96 83 14 88 34 89 63 72
; 21 36 23 09 75 00 76 44 20 45 35^14^00 61 33 97 34 31 33 95
; 78 17 53 28 22 75 31 67 15 94 03 80 04 62 16 14 09 53 56 92
; 16 39 05 42 96 35 31 47 55 58 88 24 00 17 54 24 36 29 85 57
; 86 56 00 48 35 71 89 07 05 44 44 37 44 60 21 58 51 54 17 58
; 19 80 81 68 05 94 47 69 28 73 92 13 86 52 17 77 04 89 55 40
; 04 52 08 83 97 35 99 16 07 97 57 32 16 26 26 79 33 27 98 66
; 88 36 68 87 57 62 20 72 03 46 33 67 46 55 12 32 63 93 53 69
; 04 42 16 73 38 25 39 11 24 94 72 18 08 46 29 32 40 62 76 36
; 20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74 04 36 16
; 20 73 35 29 78 31 90 01 74 31 49 71 48 86 81 16 23 57 05 54
; 01 70 54 71 83 51 54 69 16 92 33 48 61 43 52 01 89 19 67 48

; The product of these numbers is 26 × 63 × 78 × 14 = 1788696.

; What is the greatest product of four adjacent numbers in the same direction (up, down, left, right, or diagonally) in the 20×20 grid?


(let [grid [[ 8  2 22 97 38 15  0 40  0 75  4  5  7 78 52 12 50 77 91  8]
            [49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48  4 56 62  0]
            [81 49 31 73 55 79 14 29 93 71 40 67 53 88 30  3 49 13 36 65]
            [52 70 95 23  4 60 11 42 69 24 68 56  1 32 56 71 37  2 36 91]
            [22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80]
            [24 47 32 60 99  3 45  2 44 75 33 53 78 36 84 20 35 17 12 50]
            [32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70]
            [67 26 20 68  2 62 12 20 95 63 94 39 63  8 40 91 66 49 94 21]
            [24 55 58  5 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72]
            [21 36 23  9 75  0 76 44 20 45 35 14  0 61 33 97 34 31 33 95]
            [78 17 53 28 22 75 31 67 15 94  3 80  4 62 16 14  9 53 56 92]
            [16 39  5 42 96 35 31 47 55 58 88 24  0 17 54 24 36 29 85 57]
            [86 56  0 48 35 71 89  7  5 44 44 37 44 60 21 58 51 54 17 58]
            [19 80 81 68  5 94 47 69 28 73 92 13 86 52 17 77  4 89 55 40]
            [ 4 52  8 83 97 35 99 16  7 97 57 32 16 26 26 79 33 27 98 66]
            [88 36 68 87 57 62 20 72  3 46 33 67 46 55 12 32 63 93 53 69]
            [ 4 42 16 73 38 25 39 11 24 94 72 18  8 46 29 32 40 62 76 36]
            [20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74  4 36 16]
            [20 73 35 29 78 31 90  1 74 31 49 71 48 86 81 16 23 57  5 54]
            [ 1 70 54 71 83 51 54 69 16 92 33 48 61 43 52  1 89 19 67 48]]
      hgroups (mapcat (partial partition 4 1) grid)
      vgroups (mapcat (partial partition 4 1) (apply (partial map vector) grid))]
  (apply max-key (partial apply *) (concat hgroups vgroups))
  ; TODO diagonals
  )
