(set-env!
 :resource-paths #{"src"}
 :dependencies '[[org.clojure/clojure "1.8.0" :scope "test"]
                 [org.clojure/clojurescript "1.9.473" :scope "test"]
                 [adzerk/boot-cljs "1.7.228-2" :scope "test"]
                 [adzerk/bootlaces "0.1.13" :scope "test"]])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.bootlaces :refer :all])

(task-options!
 pom {:project 'org.clojars.c4605/hello-clojars-from-clojurescript
      :version "0.1.3"
      :description "Test publish ClojureScript to clojars"
      :license {"Eclipse Public License" "http://www.eclipse.org/legal/epl-v10.html"}
      :url ""
      :scm {:url ""}})

(deftask build-cljs []
  (comp
   (cljs :optimizations :none
         :compiler-options {:target :nodejs})
   (target)))

(deftask build []
  (comp
    (pom)
    (jar)
    (install)
    (target)))

(deftask deploy []
  (set-env!
    :repositories #(conj % ["clojars" {:url "https://clojars.org/repo/"}]))
  (comp
    (build)
    (push-release)))
