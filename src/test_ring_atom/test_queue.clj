(ns test-ring-atom.test-queue)

(def queue (atom clojure.lang.PersistentQueue/EMPTY))

(defn add-to-queue [number]
  (swap! queue conj number))

(defn remove-from-queue [number] 
  (swap! queue (fn [q] (remove #{number} q))))

(defn queue-has-id? [number]
  (let [pr-queue (seq @queue)]
    (if-not (nil? pr-queue)
      (> (.indexOf pr-queue number) -1)
      false)))