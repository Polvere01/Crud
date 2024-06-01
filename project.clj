(defproject my-crud-app "0.1.0-SNAPSHOT"
  :description "A simple CRUD app with Clojure and DynamoDB"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [amazonica "0.3.143"]
                 [com.amazonaws/aws-java-sdk-dynamodb "1.11.106"]]
  :main my-crud-app.core
  :aot [my-crud-app.core]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})