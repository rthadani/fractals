(ns core.julia
  (:require [core.protocols :refer [Fractal width height surface-height surface-width]]
            [complex.core :as cm]))


(defrecord Julia [z c w h sw sh min-iter max-iter max-mag]
  Fractal
  (width [_] w)
  (height [_] h)
  (surface-width [_] sw)
  (surface-height [_] sh)
  (iteration
    [this offset-x offset-y]
    (let [x (+ (cm/real-part z) (* (width this) (/ offset-x (surface-width this))))
          y (+ (cm/imaginary-part z) (* (height this) (/ offset-y (surface-height this))))
          z (cm/complex x y)]
    (loop [z z 
           i 0]
    (if (or (> (cm/abs z) max-mag) (> i max-iter))
      (if (zero? i) i (dec i))
      (recur (cm/+ c (cm/* z z)) (inc i))))))
  (pixel-color 
    [_ iteration-result]
    (if (or (< iteration-result min-iter) (= iteration-result max-iter)) 
      [0 0 0]
      (let [gray (int (/ (* iteration-result 255) max-iter))
          r    gray
          g    (Math/min (int (/ (* 5 ( * gray gray)) 255)) (int 255))
          b    (Math/min (int (+ 40 ( / (* 5 (* gray gray)) 255))) (int 255))]
      [r g b]))))