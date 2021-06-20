(ns core.fractals
  (:require [quil.core :as q]
            [complex.core :as c]
            [core.mandelbrot :refer :all] 
            [core.protocols :refer [surface-width surface-height iteration pixel-color]])
  (:gen-class))

; define function which draws spiral
; define f


(defn draw-fractal
  [fractal]
  (doseq [i (range (surface-width fractal))
          j (range (surface-height fractal))
          :let [r (iteration fractal i j)
                c (pixel-color fractal r)]]
    (apply q/stroke c)      
    (q/point i j)))

(defn create-sketch
  [m]
  (q/defsketch fractal
               :size [(surface-width m) (surface-height m)]
               :bgcolor "#000000" 
               :draw (partial draw-fractal m) ))


#_ (q/defsketch fractal
       )
; run sketch
#_ (q/defsketch trigonometry
  :size [300 300]
  :draw draw)

#_ (create-sketch (->Mandelbrot  (c/complex -2.1 -1.4) 
                                 3.0 
                                 3.1 
                                 1000
                                 1000 
                                 10
                                 32
                                 2.0))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

