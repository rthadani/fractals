(ns core.mandelbrot
  (:require [core.protocols :refer [Fractal width height surface-height surface-width]]
            [complex.core :as cm]
            [quil.core :as q]))


(defrecord Mandelbrot [c w h sw sh min-iter max-iter max-mag]
  Fractal
  (width [_] w)
  (height [_] h)
  (surface-width [_] sw)
  (surface-height [_] sh)
  (iteration
    [this offset-x offset-y]
    (let [x (+ (cm/real-part c) (* (width this) (/ offset-x (surface-width this))))
          y (+ (cm/imaginary-part c) (* (height this) (/ offset-y (surface-height this))))
          c (cm/complex x y)]
    (loop [z c 
           i 0]
    (if (or (> (cm/abs z) max-mag) (> i max-iter))
      (if (zero? i) i (dec i))
      (recur (cm/+ c (cm/* z z)) (inc i))))))
  (pixel-color 
    [_ iteration-result]
    (if (or (< iteration-result min-iter) (> iteration-result max-iter)) 
      [0 0 0]
      (let [gray (int (/ (* iteration-result 255) max-iter))
          r    gray
          g    (Math/min (int (/ (* 5 ( * gray gray)) 255)) (int 255))
          b    (Math/min (int (+ 40 ( / (* 5 (* gray gray)) 255))) (int 255))]
      [r g b]))))