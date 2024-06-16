## USER 

### Register new User
```bash
$ curl -v -X POST localhost:8080/users/register \
-H 'Content-type:application/json' \
-d '{"userId": 0, "username": "kimki", "password": "password", "age": 4, "occupation": "scientist", "gender": "M"}'
```

### Login User(=kimki)
```bash
$ curl -v -X POST localhost:8080/users/register \
-H 'Content-type:application/json' \
-d '{"username": "kimki", "password": "password"}'
```

### Add movie to Wish List of a User(=kimki)
```bash
$ curl -v -X POST localhost:8080/users/kimki/wishList/add?movieId=5
```

### Get a Wish List of a User(=kimki)
```bash
$ curl -v  http://localhost:8080/kimki/wishList
```

### Add points of solved Quiz to User(=kimki)
```bash
$ curl -v -X POST localhost:8080/users/kimki/addPoints
```

## Movie

### Get Movie by its Id(=5)
```bash
$ curl -v  http://localhost:8080/movies/5
```

### Add comment to Movie with Id(=5)

```bash
$ curl -v -X POST localhost:8080/movies/5/addComment \
-H 'Content-type:application/json' \
-d '{"username": "kimki", "body": "Awesome Movie!", "rate": 4}'
```

## Quiz

### Get a random Quiz

```bash
$ curl -v  http://localhost:8080/quiz
```

## Recommendation

### Type 1
1. Run the following command in your terminal:
```bash
$ curl -X GET http://localhost:8080/recommendation/type1 \
-H 'Content-type:application/json' -d '{"gender" : "[gender]", "age" :"[age]", \ 
"occupation" : "[occupation]", "genre" : "[genre_1|genre_2]"}'
```
2. Replace the placeholders [gender], [age], [occupation], 
and [genre_1|genre_2] with the respective user data and preferred genres.

Example
```bash
$ curl -X GET http://localhost:8080/recommendation/type1 \ 
-H 'Content-type:application/json' -d '{"gender" : "F", "age" :"25", \ 
"occupation" : "scientist", "genre" : "Action|War"}'
```

### Type 2: Get recommended movies based on an occasion(=Family Night)
```bash
$ curl -v  http://localhost:8080/recommendation/type2/familyNight
```
