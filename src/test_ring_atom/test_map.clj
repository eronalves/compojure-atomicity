(ns test-ring-atom.test-map)

(def list-pr (atom {}))

(defn- get-keyword [number]
  (keyword (str number)))

(defn add-pr [number]
  (swap! list-pr assoc (get-keyword number) {:log []}))

(defn remove-pr [number] 
  (swap! list-pr dissoc (get-keyword number)))

(defn has-pr? [number]
  (contains? @list-pr (get-keyword number)))
