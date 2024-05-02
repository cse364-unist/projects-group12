# Feature 1 Curl command
1. Run the following command in your terminal:
```bash
$ curl -X GET http://localhost:8080/recommendation/type1 -H 'Content-type:application/json' -d '{"gender" : "[gender]", "age" :"[age]", "occupation" : "[occupation]", "genre" : "[genre_1|genre_2]"}'
```
2. Replace the placeholders [gender], [age], [occupation], and [genre_1|genre_2] with the respective user data and preferred genres.

Example
```bash
$ curl -X GET http://localhost:8080/recommendation/type1 -H 'Content-type:application/json' -d '{"gender" : "F", "age" :"25", "occupation" : "scientist", "genre" : "Action|War"}'

```
