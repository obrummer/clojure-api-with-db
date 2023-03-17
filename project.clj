(defproject api-with-db "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.5.1"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [org.postgresql/postgresql "42.4.0"]
                 [environ "1.2.0"]]
  :plugins [[lein-ring "0.12.5"]
            [lein-environ "1.2.0"]]
  :ring {:init    api-with-db.db/init
         :handler api-with-db.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
