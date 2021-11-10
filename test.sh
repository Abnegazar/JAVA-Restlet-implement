
printf "\n*** INIT ***\n"

curl http://localhost:8124/users

curl -X POST -H 'Content-type:application/json' -H 'Accept:application/json' -d '{"name":"peter", "age":30}' http://localhost:8124/users
curl -X POST -H 'Content-type:application/json' -H 'Accept:application/json' -d '{"name":"carlos", "age":25}' http://localhost:8124/users

printf "\n*** USERS ***\n"
curl http://localhost:8124/users

#printf "\n*** ADD TWEETS ***\n"

#curl -X POST -H 'Content-type:application/json' -H 'Accept:application/json' -d '{"tweet":"bla"}' http://localhost:8124/users/0/tweets
#curl -X POST -H 'Content-type:application/json' -H 'Accept:application/json' -d '{"tweet":"cronf"}' http://localhost:8124/users/0/tweets

#printf "\n*** GET TWEETS ***\n"

#curl http://localhost:8124/users/0/tweets
#curl http://localhost:8124/users/1/tweets

#printf "\n*** GET ALL TWEETS ***\n"

#curl -X GET http://localhost:8124/users/tweets

#printf "\n*** DELETION ***\n"

#curl -X DELETE http://localhost:8124/users/1

#printf "\n*** AFTER DELETION ***\n"

#curl -X GET http://localhost:8124/users/1




