(ns core.protocols)

(defprotocol Fractal
  (width [this])
  (height [this])
  (surface-width [this])
  (surface-height [this])
  (iteration [this offset-x offset-y])
  (pixel-color [this iteration-result]))