(ns my-crud-app.core
  (:require [amazonica.aws.dynamodbv2 :as dynamodb])
  (:gen-class))

(defn create-user [user-id name age sex]
  (println "Creating user with ID:" user-id " Name:" name " Age:" age " Sex:" sex)
  (dynamodb/put-item :table-name "Users"
                     :item {:UserId user-id
                            :Name name
                            :Age (str age)
                            :Sex sex}))

(defn get-user [user-id]
  (println "Getting user with ID:" user-id)
  (let [result (dynamodb/get-item :table-name "Users"
                                  :key {:UserId user-id})]
    (println "Get user result:" result)
    (when (seq (:item result))
      {:user-id user-id
       :name (get-in result [:item :Name])
       :age (Integer/parseInt (get-in result [:item :Age]))
       :sex (get-in result [:item :Sex])})))

(defn update-user [user-id new-name new-age new-sex]
  (println "Updating user with ID:" user-id " New Name:" new-name " New Age:" new-age " New Sex:" new-sex)
  (dynamodb/update-item :table-name "Users"
                        :key {:UserId user-id}
                        :attribute-updates {:Name {:Value new-name :Action "PUT"}
                                            :Age {:Value (str new-age) :Action "PUT"}
                                            :Sex {:Value new-sex :Action "PUT"}}))

(defn delete-user [user-id]
  (println "Deleting user with ID:" user-id)
  (dynamodb/delete-item :table-name "Users"
                        :key {:UserId user-id}))

(defn -main [& args]
  (create-user "1" "Joao Gomes" 30 "Masculino")
  (println "Usuário criado:" (get-user "1"))
  (update-user "1" "Joana Gomes" 28 "Femenino")
  (println "Usuário alterado:" (get-user "1")))
