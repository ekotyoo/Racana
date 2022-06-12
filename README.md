# Racana Express.js API

### Built With

* [Express.js](https://expressjs.com/)
* [Sequelize](https://sequelize.org/)
* [MySQL](https://www.mysql.com/)

<p align="right">(<a href="#top">back to top</a>)</p>


### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/ekotyoo/Racana.git
   git checkout racana-api-express
   git pull
   ```
2. Install NPM packages
   ```sh
   npm install
   ```
3. Copy `.env.example` and rename to `.env`
4. Enter your MySQL connection in `.env`

<p align="right">(<a href="#top">back to top</a>)</p>


# API Documentation
* [Postman](https://documenter.getpostman.com/view/20519810/Uz5CMdh2)

# 📁 Collection: Auth 

## End-point: Signup
### Method: POST
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/auth/signup
>```
### Response: 200
```json
{
    "status": "Success",
    "message": "Signup success.",
    "data": {
        "id": 5,
        "name": "FooBar",
        "email": "foobar@mail.com"
    }
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Login
### Method: POST
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/auth/login
>```
### Response: 200
```json
{
    "status": "Success",
    "message": "Login success.",
    "data": {
        "id": 1,
        "name": "Foo",
        "email": "foo@mail.com",
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0NzY1MDc0fQ.3W2-1ItqxpCVjV9bVGZIwXYTqLW6poVTRbsucSZsSN8"
    }
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃
# 📁 Collection: Tour Plan 


## End-point: Get All Tour Plan
### Method: GET
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/tourplan
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0NzY1MDc0fQ.3W2-1ItqxpCVjV9bVGZIwXYTqLW6poVTRbsucSZsSN8|


### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully get data.",
    "data": [
        {
            "id": 6,
            "title": "hjkj",
            "description": "hjnghj",
            "isActive": false,
            "createdAt": "2022-06-09T05:32:01.000Z",
            "updatedAt": "2022-06-09T08:02:19.000Z",
            "userId": 1,
            "tourplandates": [
                {
                    "id": 12,
                    "date_millis": 1654621200000,
                    "tourplanId": 6,
                    "destinations": [
                        {
                            "id": 10,
                            "name": "Handara Iconic Gate",
                            "description": "Gapura indah yang berada di sebelah Pura Ulun Danu Beratan. Gerbang ini salah satu ikon foto paling mengesankan bagi para wisatawan. Dengan kombinasi gerbang kuno dengan perbukitan dan lapangan golf hijau menjadikan gerbang ini menjadi tempat wisata yang ",
                            "addresss": "Jl. Raya Singaraja-Denpasar, Pancasari, Kec. Sukasada, Kabupaten Buleleng, Bali 81161",
                            "city": "Buleleng",
                            "lat": -8.25279,
                            "lon": 115.15828,
                            "imageUrl": "https://storage.googleapis.com/racana_img/Handara%20Iconic%20Gate.jpg",
                            "weekdayPrice": 20000,
                            "weekendHolidayPrice": 20000,
                            "rating": 3.5,
                            "categoryId": 4,
                            "datedestination": {
                                "isDone": false,
                                "createdAt": "2022-06-09T05:32:01.000Z",
                                "updatedAt": "2022-06-09T07:16:54.000Z",
                                "tourplandateId": 12,
                                "destinationId": 10
                            }
                        }
                    ]
                },
                {
                    "id": 11,
                    "date_millis": 1654534800000,
                    "tourplanId": 6,
                    "destinations": [
                        {
                            "id": 2,
                            "name": "Agung Bali",
                            "description": "Dapatkan berbagai produk oleh-oleh khas Bali berupa baju vip, camilan, handycraft dan lain-lain.",
                            "addresss": "Jln. Dewi Sri No.18XX, Kuta, Badung, Bali",
                            "city": "Badung",
                            "lat": -8.70023,
                            "lon": 115.17653,
                            "imageUrl": "https://storage.googleapis.com/racana_img/Agung%20Bali.jpg",
                            "weekdayPrice": 0,
                            "weekendHolidayPrice": 0,
                            "rating": 4,
                            "categoryId": 3,
                            "datedestination": {
                                "isDone": false,
                                "createdAt": "2022-06-09T07:13:43.000Z",
                                "updatedAt": "2022-06-09T07:13:43.000Z",
                                "tourplandateId": 11,
                                "destinationId": 2
                            }
                        },
                        {
                            "id": 7,
                            "name": "Air Terjun Tukad Cepung",
                            "description": "Salah satu air terjun yang berbeda dari air terjun lain di Bali adalah Tukad Cepung. Dikatakan berbeda karena air terjunnya tidak terbuka, melainkan berada di dalam goa.  Di sekitar air terjun pun seolah belum pernah terjamah oleh manusia. Karena batuan d",
                            "addresss": "Jl. Tembuku, Tembuku, Kec. Tembuku, Kabupaten Bangli, Bali 80671",
                            "city": "Bangli",
                            "lat": -8.43804,
                            "lon": 115.38732,
                            "imageUrl": "https://storage.googleapis.com/racana_img/Air%20Terjun%20Tukad%20Cepung.jpg",
                            "weekdayPrice": 15000,
                            "weekendHolidayPrice": 15000,
                            "rating": 4.5,
                            "categoryId": 2,
                            "datedestination": {
                                "isDone": false,
                                "createdAt": "2022-06-09T07:08:54.000Z",
                                "updatedAt": "2022-06-09T07:13:18.000Z",
                                "tourplandateId": 11,
                                "destinationId": 7
                            }
                        },
                        {
                            "id": 76,
                            "name": "Wisata Agro Teba Sari Bali",
                            "description": "Dalam kawasan Wisata Agro Teba Sari Bali, pengunjung yang datang dapat menikmati pemandangan perkebunan kopi, dan peternakan hewan luwak serta teknik pertanian modern sambil berekreasi dan menikmati makan siang di restoran. ",
                            "addresss": "Jl. Tegal Utu, Lodtunduh, Kecamatan Ubud, Kabupaten Gianyar, Bali 80571",
                            "city": "Gianyar",
                            "lat": -8.54723,
                            "lon": 115.25742,
                            "imageUrl": "https://storage.googleapis.com/racana_img/Wisata%20Agro%20Teba%20Sari.jpg",
                            "weekdayPrice": 0,
                            "weekendHolidayPrice": 0,
                            "rating": 4,
                            "categoryId": 1,
                            "datedestination": {
                                "isDone": false,
                                "createdAt": "2022-06-09T08:15:25.000Z",
                                "updatedAt": "2022-06-09T08:15:25.000Z",
                                "tourplandateId": 11,
                                "destinationId": 76
                            }
                        }
                    ]
                }
            ]
        }
    ]
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Get Tour Plan by id
### Method: GET
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/tourplan/6
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0NzY1MDc0fQ.3W2-1ItqxpCVjV9bVGZIwXYTqLW6poVTRbsucSZsSN8|


### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully get data.",
    "data": {
        "id": 6,
        "title": "hjkj",
        "description": "hjnghj",
        "isActive": false,
        "createdAt": "2022-06-09T05:32:01.000Z",
        "updatedAt": "2022-06-09T08:02:19.000Z",
        "userId": 1,
        "tourplandates": [
            {
                "id": 11,
                "date_millis": 1654534800000,
                "tourplanId": 6,
                "destinations": [
                    {
                        "id": 2,
                        "name": "Agung Bali",
                        "description": "Dapatkan berbagai produk oleh-oleh khas Bali berupa baju vip, camilan, handycraft dan lain-lain.",
                        "addresss": "Jln. Dewi Sri No.18XX, Kuta, Badung, Bali",
                        "city": "Badung",
                        "lat": -8.70023,
                        "lon": 115.17653,
                        "imageUrl": "https://storage.googleapis.com/racana_img/Agung%20Bali.jpg",
                        "weekdayPrice": 0,
                        "weekendHolidayPrice": 0,
                        "rating": 4,
                        "categoryId": 3,
                        "datedestination": {
                            "isDone": false,
                            "createdAt": "2022-06-09T07:13:43.000Z",
                            "updatedAt": "2022-06-09T07:13:43.000Z",
                            "tourplandateId": 11,
                            "destinationId": 2
                        }
                    },
                    {
                        "id": 7,
                        "name": "Air Terjun Tukad Cepung",
                        "description": "Salah satu air terjun yang berbeda dari air terjun lain di Bali adalah Tukad Cepung. Dikatakan berbeda karena air terjunnya tidak terbuka, melainkan berada di dalam goa.  Di sekitar air terjun pun seolah belum pernah terjamah oleh manusia. Karena batuan d",
                        "addresss": "Jl. Tembuku, Tembuku, Kec. Tembuku, Kabupaten Bangli, Bali 80671",
                        "city": "Bangli",
                        "lat": -8.43804,
                        "lon": 115.38732,
                        "imageUrl": "https://storage.googleapis.com/racana_img/Air%20Terjun%20Tukad%20Cepung.jpg",
                        "weekdayPrice": 15000,
                        "weekendHolidayPrice": 15000,
                        "rating": 4.5,
                        "categoryId": 2,
                        "datedestination": {
                            "isDone": false,
                            "createdAt": "2022-06-09T07:08:54.000Z",
                            "updatedAt": "2022-06-09T07:13:18.000Z",
                            "tourplandateId": 11,
                            "destinationId": 7
                        }
                    }
                ]
            },
            {
                "id": 12,
                "date_millis": 1654621200000,
                "tourplanId": 6,
                "destinations": [
                    {
                        "id": 10,
                        "name": "Handara Iconic Gate",
                        "description": "Gapura indah yang berada di sebelah Pura Ulun Danu Beratan. Gerbang ini salah satu ikon foto paling mengesankan bagi para wisatawan. Dengan kombinasi gerbang kuno dengan perbukitan dan lapangan golf hijau menjadikan gerbang ini menjadi tempat wisata yang ",
                        "addresss": "Jl. Raya Singaraja-Denpasar, Pancasari, Kec. Sukasada, Kabupaten Buleleng, Bali 81161",
                        "city": "Buleleng",
                        "lat": -8.25279,
                        "lon": 115.15828,
                        "imageUrl": "https://storage.googleapis.com/racana_img/Handara%20Iconic%20Gate.jpg",
                        "weekdayPrice": 20000,
                        "weekendHolidayPrice": 20000,
                        "rating": 3.5,
                        "categoryId": 4,
                        "datedestination": {
                            "isDone": false,
                            "createdAt": "2022-06-09T05:32:01.000Z",
                            "updatedAt": "2022-06-09T07:16:54.000Z",
                            "tourplandateId": 12,
                            "destinationId": 10
                        }
                    }
                ]
            }
        ]
    }
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Create Tour Plan
### Method: POST
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/tourplan
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0NzY1MDc0fQ.3W2-1ItqxpCVjV9bVGZIwXYTqLW6poVTRbsucSZsSN8|


### Body (**raw**)

```json
{
    "description": "Deskripsi tour ke bali",
    "title": "Tour ke bali",
    "tourplandates": [
        {
            "date_millis": 1654765347702,
            "destinations": [
                1,
                2
            ]
        },
        {
            "date_millis": 1654794000000,
            "destinations": [
                8,
                9,
                10
            ]
        }
    ]
}
```

### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully inserting data.",
    "data": {
        "isActive": false,
        "id": 8,
        "title": "Tour ke bali",
        "description": "Deskripsi tour ke bali",
        "userId": 1,
        "tourplandates": [
            {
                "id": 13,
                "date_millis": 1654765347702,
                "tourplanId": 8
            },
            {
                "id": 14,
                "date_millis": 1654794000000,
                "tourplanId": 8
            }
        ],
        "updatedAt": "2022-06-09T09:03:25.674Z",
        "createdAt": "2022-06-09T09:03:25.674Z"
    }
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Edit Tour Plan
### Method: PUT
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/tourplan/6
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0NzY1MDc0fQ.3W2-1ItqxpCVjV9bVGZIwXYTqLW6poVTRbsucSZsSN8|


### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully updating data.",
    "data": [
        1
    ]
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Delete Tour Plan
### Method: DELETE
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/tourplan/6
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0NzY1MDc0fQ.3W2-1ItqxpCVjV9bVGZIwXYTqLW6poVTRbsucSZsSN8|


### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully deleting data.",
    "data": 1
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃
# 📁 Collection: Tour Plan Date 


## End-point: Get Tour Plan Date
### Method: GET
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/tourplan/9/date
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0NzY1MDc0fQ.3W2-1ItqxpCVjV9bVGZIwXYTqLW6poVTRbsucSZsSN8|


### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully get data.",
    "data": [
        {
            "id": 15,
            "date_millis": 1654534800000,
            "tourplanId": 9
        },
        {
            "id": 16,
            "date_millis": 1654621200000,
            "tourplanId": 9
        }
    ]
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Create Tour Plan Date
### Method: POST
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/tourplan/9/date
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0NzY1MDc0fQ.3W2-1ItqxpCVjV9bVGZIwXYTqLW6poVTRbsucSZsSN8|


### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully inserting data.",
    "data": {}
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Delete Tour Plan Date
### Method: DELETE
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/tourplan/1/date/5
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0NzY1MDc0fQ.3W2-1ItqxpCVjV9bVGZIwXYTqLW6poVTRbsucSZsSN8|


### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully deleting data.",
    "data": 1
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Mark Destination Done
### Method: PUT
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/tourplandate/15/destination/1/done
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0NzY1MDc0fQ.3W2-1ItqxpCVjV9bVGZIwXYTqLW6poVTRbsucSZsSN8|


### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully update data.",
    "data": [
        1
    ]
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Mark Destination Not Done
### Method: PUT
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/tourplandate/15/destination/1/notdone
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0NzY1MDc0fQ.3W2-1ItqxpCVjV9bVGZIwXYTqLW6poVTRbsucSZsSN8|


### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully update data.",
    "data": [
        1
    ]
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃
# 📁 Collection: Destination 


## End-point: Get All Destination
### Method: GET
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/destination?keyword= 
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0NzY1MDc0fQ.3W2-1ItqxpCVjV9bVGZIwXYTqLW6poVTRbsucSZsSN8|


### Query Params

|Param|value|
|---|---|
|keyword| |


### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully get data.",
    "data": [
        {
            "id": 1,
            "name": "Agrowisata Satria",
            "description": "Agrowisata Satria menawarkan 'rasa' Bali pada setiap sesapan teh dan kopi di tengah perkebunan yang asri",
            "addresss": "Manukaya, Kec. Tampaksiring, Kabupaten Gianyar, Bali 80552",
            "city": "Gianyar",
            "lat": -8.40222,
            "lon": 115.32296,
            "imageUrl": "https://storage.googleapis.com/racana_img/Agrowisata%20Satria.jpg",
            "weekdayPrice": 50000,
            "weekendHolidayPrice": 50000,
            "rating": 3,
            "categoryId": 1
        },
        {
            "id": 2,
            "name": "Agung Bali",
            "description": "Dapatkan berbagai produk oleh-oleh khas Bali berupa baju vip, camilan, handycraft dan lain-lain.",
            "addresss": "Jln. Dewi Sri No.18XX, Kuta, Badung, Bali",
            "city": "Badung",
            "lat": -8.70023,
            "lon": 115.17653,
            "imageUrl": "https://storage.googleapis.com/racana_img/Agung%20Bali.jpg",
            "weekdayPrice": 0,
            "weekendHolidayPrice": 0,
            "rating": 4,
            "categoryId": 3
        },
        {
            "id": 3,
            "name": "Wisata Air Panas Toya Bungkah",
            "description": "Pemandian air panas di dekat gunung Batur (Pemandian Air Panas Batur) di Bali merupakan keseluruhan kompleks waduk. Air di dalamnya memanas hingga 50 derajat, mengandung banyak mineral dan belerang, dan memiliki khasiat penyembuhan.",
            "addresss": "Batur Tengah, Kec. Kintamani, Kabupaten Bangli, Bali",
            "city": "Bangli",
            "lat": -8.25129,
            "lon": 115.39981,
            "imageUrl": "https://storage.googleapis.com/racana_img/Wisata%20Air%20Panas%20Toya%20Bungkah.jpeg",
            "weekdayPrice": 70000,
            "weekendHolidayPrice": 70000,
            "rating": 4,
            "categoryId": 2
        },
        {
            "id": 4,
            "name": "Air Terjun Aling-Aling",
            "description": "Keberadaan air terjun ini semakin mempercantik wisata alam yang terdapat di pulau dewata Bali.",
            "addresss": "Jl. Raya Desa Sambangan, Banjar, Sambangan, Kec. Sukasada, Kabupaten Buleleng, Bali 81161",
            "city": "Buleleng",
            "lat": -8.17307,
            "lon": 115.10513,
            "imageUrl": "https://storage.googleapis.com/racana_img/Air%20Terjun%20Aling%20aling.jpg",
            "weekdayPrice": 20000,
            "weekendHolidayPrice": 20000,
            "rating": 4.5,
            "categoryId": 2
        },
        {
            "id": 5,
            "name": "Air Terjun Tegenungan",
            "description": "Bosan dengan wisata alam pantai dan ingin menikmati suasana alam yang berbeda, Air Terjun Tegenungan jawabannya. Tempat rekreasi alam air terjun di Gianyar ini, mudah dijangkau, pesona alam yang ditawakan indah dan menarik",
            "addresss": "Jl. Ir. Sutami, Kemenuh, Kec. Sukawati, Kabupaten Gianyar, Bali 80581",
            "city": "Gianyar",
            "lat": -8.57519,
            "lon": 115.2887,
            "imageUrl": "https://storage.googleapis.com/racana_img/Air%20Terjun%20Tegenungan.jpg",
            "weekdayPrice": 20000,
            "weekendHolidayPrice": 20000,
            "rating": 4,
            "categoryId": 2
        },
        {
            "id": 7,
            "name": "Air Terjun Tukad Cepung",
            "description": "Salah satu air terjun yang berbeda dari air terjun lain di Bali adalah Tukad Cepung. Dikatakan berbeda karena air terjunnya tidak terbuka, melainkan berada di dalam goa.  Di sekitar air terjun pun seolah belum pernah terjamah oleh manusia. Karena batuan d",
            "addresss": "Jl. Tembuku, Tembuku, Kec. Tembuku, Kabupaten Bangli, Bali 80671",
            "city": "Bangli",
            "lat": -8.43804,
            "lon": 115.38732,
            "imageUrl": "https://storage.googleapis.com/racana_img/Air%20Terjun%20Tukad%20Cepung.jpg",
            "weekdayPrice": 15000,
            "weekendHolidayPrice": 15000,
            "rating": 4.5,
            "categoryId": 2
        }
    ]
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Get Destination by Id
### Method: GET
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/destination/1
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0NzY1MDc0fQ.3W2-1ItqxpCVjV9bVGZIwXYTqLW6poVTRbsucSZsSN8|


### Response: 200
```json
{
    "status": "Success",
    "message": "Successfully get data.",
    "data": {
        "id": 1,
        "name": "Agrowisata Satria",
        "description": "Agrowisata Satria menawarkan 'rasa' Bali pada setiap sesapan teh dan kopi di tengah perkebunan yang asri",
        "addresss": "Manukaya, Kec. Tampaksiring, Kabupaten Gianyar, Bali 80552",
        "city": "Gianyar",
        "lat": -8.40222,
        "lon": 115.32296,
        "imageUrl": "https://storage.googleapis.com/racana_img/Agrowisata%20Satria.jpg",
        "weekdayPrice": 50000,
        "weekendHolidayPrice": 50000,
        "rating": 3,
        "categoryId": 1
    }
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Create Destination
### Method: POST
>```
>https://racana-test.herokuapp.com/api/destination
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJ1c2VySWQiOjEsImlhdCI6MTY1Mzg5NTAyNn0.XF9FZ2U3ot1aXFUPm8_aImidXqXtFVdbkSSLVnpVIsU|


### Body (**raw**)

```json
{
    "name": "Danau Toba",
    "location": "Sumatera Gatau",
    "brief": "Ini adalah brief",
    "lat": 11.32432,
    "lon": 9.42342,
    "imageUrl": "www.danutoba.com",
    "expense": 300000
}
```

### Response: 200
```json
{
    "status": "Success",
    "message": "Successfully adding data.",
    "data": {
        "id": 4,
        "name": "Danau Toba",
        "brief": "Ini adalah brief",
        "location": "Sumatera Gatau",
        "lat": 11.32432,
        "lon": 9.42342,
        "imageUrl": "www.danutoba.com",
        "expense": 300000
    }
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Update Destination by Id
### Method: PUT
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/destination/1
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJ1c2VySWQiOjEsImlhdCI6MTY1Mzg5NTAyNn0.XF9FZ2U3ot1aXFUPm8_aImidXqXtFVdbkSSLVnpVIsU|


### Body (**raw**)

```json
{
    "name": "Danau Malang",
    "location": "Sumatera Gatau",
    "brief": "Ini adalah brief",
    "lat": -11.24343,
    "lon": 9.4234234,
    "imageUrl": "www.danutoba.com",
    "expense": 300000
}
```

### Response: 200
```json
{
    "status": "Success",
    "message": "Successfully upadating data.",
    "data": [
        1
    ]
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Delete Destination by Id
### Method: DELETE
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/destination/1
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJ1c2VySWQiOjEsImlhdCI6MTY1Mzg5NTAyNn0.XF9FZ2U3ot1aXFUPm8_aImidXqXtFVdbkSSLVnpVIsU|


### Response: 200
```json
{
    "status": "Success",
    "message": "Successfully deleting data.",
    "data": 1
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃
# 📁 Collection: Article 


## End-point: Get All Articles
### Method: GET
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/article
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0NzY1MDc0fQ.3W2-1ItqxpCVjV9bVGZIwXYTqLW6poVTRbsucSZsSN8|


### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully get data.",
    "data": [
        {
            "title": "How to Get to the Campuhan Ridge Walk Ubud And What to Expect",
            "imageUrl": "https://cdn.almostlanding-bali.com/wp-content/uploads/2016/02/campuhan-ridge-walk.jpg",
            "author": "Mandy and Chris"
        },
        {
            "title": "Tips for visiting Kelingking Beach on Nusa Penida",
            "imageUrl": "https://static.saltinourhair.com/wp-content/uploads/2018/10/23145815/kelingking-nusa-penida-viewpoint-1.jpg",
            "author": "Nick and Hannah"
        },
        {
            "title": "Why is everyone so obsessed with Bali, yet ignorant of Indonesia’s 17,500 other islands?",
            "imageUrl": "https://img.i-scmp.com/cdn-cgi/image/fit=contain,width=1098,format=auto/sites/default/files/styles/1200x800/public/d8/images/canvas/2021/08/24/e3a6d6ab-5296-4b33-9bc8-76285fa567a2_fcfe4d46.jpg?itok=dCv9wYqJ&v=1629809760",
            "author": "Mercedes Hutton"
        },
        {
            "title": "Joger Bali, where to shop souvenirs from Bali",
            "imageUrl": "https://balitraveldiary.com/wp-content/uploads/2019/10/Joger-Kuta-Bali.jpg",
            "author": "Dwiwinarno"
        },
        {
            "title": "An Iconic Bali Handara Gate",
            "imageUrl": "https://balicheapesttours.com/dummy/handara-gate.jpg",
            "author": "Ketut Astawa"
        }
    ]
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Get Dashboard Articles
### Method: GET
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/article/dashboard
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0NzY1MDc0fQ.3W2-1ItqxpCVjV9bVGZIwXYTqLW6poVTRbsucSZsSN8|


### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully get data.",
    "data": [
        {
            "title": "How to Get to the Campuhan Ridge Walk Ubud And What to Expect",
            "imageUrl": "https://cdn.almostlanding-bali.com/wp-content/uploads/2016/02/campuhan-ridge-walk.jpg",
            "author": "Mandy and Chris"
        },
        {
            "title": "Tips for visiting Kelingking Beach on Nusa Penida",
            "imageUrl": "https://static.saltinourhair.com/wp-content/uploads/2018/10/23145815/kelingking-nusa-penida-viewpoint-1.jpg",
            "author": "Nick and Hannah"
        },
        {
            "title": "Why is everyone so obsessed with Bali, yet ignorant of Indonesia’s 17,500 other islands?",
            "imageUrl": "https://img.i-scmp.com/cdn-cgi/image/fit=contain,width=1098,format=auto/sites/default/files/styles/1200x800/public/d8/images/canvas/2021/08/24/e3a6d6ab-5296-4b33-9bc8-76285fa567a2_fcfe4d46.jpg?itok=dCv9wYqJ&v=1629809760",
            "author": "Mercedes Hutton"
        },
        {
            "title": "Joger Bali, where to shop souvenirs from Bali",
            "imageUrl": "https://balitraveldiary.com/wp-content/uploads/2019/10/Joger-Kuta-Bali.jpg",
            "author": "Dwiwinarno"
        }
    ]
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Get Article by Id
### Method: GET
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/article/1
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0NzY1MDc0fQ.3W2-1ItqxpCVjV9bVGZIwXYTqLW6poVTRbsucSZsSN8|


### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully get data.",
    "data": {
        "id": 1,
        "title": "How to Get to the Campuhan Ridge Walk Ubud And What to Expect",
        "author": "Mandy and Chris",
        "content": "When in Ubud there are many fantastic things to do. There are some really nice walks, be it through the village, through rice fields or just a beautiful nature walk. The Campuhan Ridge walk is definitely at the top of the list of must do walks when in Ubud. It is a beautiful walk which is filled with greenery and hills. It’s more of a jungle or forest walk so if you wanted to only see rice fields and terraces there are other walks you can do. We have been doing this walk every year since 2015, and although it gets busier each year, it’s still such a beautiful experience.This is a beautiful paved walk along the Campuhan Ridge. This walk definitely showcases the natural side to Ubud. Many trees and hills, the Campuhan Ridge Walk has the scenery of a jungle or forest rather than that of rice terraces and fields.",
        "imageUrl": "https://cdn.almostlanding-bali.com/wp-content/uploads/2016/02/campuhan-ridge-walk.jpg",
        "source": "Almostlanding Bali"
    }
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃
# 📁 Collection: Prediction 


## End-point: Predict 1
### Method: POST
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/predict
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0ODc0NTM5fQ.W2_0FcxrE4Jm_WX4Wc0q_D0lsWMYRqBoiFHJvYQ8sUY|


### Body (**raw**)

```json
{
    "input": "Pantai Diamond"
}
```

### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully get data.",
    "data": {
        "id": 2,
        "name": "Agung Bali",
        "description": "Dapatkan berbagai produk oleh-oleh khas Bali berupa baju vip, camilan, handycraft dan lain-lain.",
        "addresss": "Jln. Dewi Sri No.18XX, Kuta, Badung, Bali",
        "city": "Badung",
        "lat": -8.70023,
        "lon": 115.17653,
        "imageUrl": "https://storage.googleapis.com/racana_img/Agung%20Bali.jpg",
        "weekdayPrice": 0,
        "weekendHolidayPrice": 0,
        "rating": 4,
        "categoryId": 3
    }
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Predict 2
### Method: POST
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/predict2
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0ODc0NTM5fQ.W2_0FcxrE4Jm_WX4Wc0q_D0lsWMYRqBoiFHJvYQ8sUY|


### Body (**raw**)

```json
{
    "input": "Bali Bird Park"
}
```

### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully get data.",
    "data": {
        "id": 5,
        "name": "Air Terjun Tegenungan",
        "description": "Bosan dengan wisata alam pantai dan ingin menikmati suasana alam yang berbeda, Air Terjun Tegenungan jawabannya. Tempat rekreasi alam air terjun di Gianyar ini, mudah dijangkau, pesona alam yang ditawakan indah dan menarik",
        "addresss": "Jl. Ir. Sutami, Kemenuh, Kec. Sukawati, Kabupaten Gianyar, Bali 80581",
        "city": "Gianyar",
        "lat": -8.57519,
        "lon": 115.2887,
        "imageUrl": "https://storage.googleapis.com/racana_img/Air%20Terjun%20Tegenungan.jpg",
        "weekdayPrice": 20000,
        "weekendHolidayPrice": 20000,
        "rating": 4,
        "categoryId": 2
    }
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## End-point: Predict Final
### Method: POST
>```
>https://racana-api-mg3l2wnsyq-et.a.run.app/api/recommendation/predictfinal
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImZvb0BtYWlsLmNvbSIsInVzZXJJZCI6MSwiaWF0IjoxNjU0ODc0NTM5fQ.W2_0FcxrE4Jm_WX4Wc0q_D0lsWMYRqBoiFHJvYQ8sUY|


### Body (**raw**)

```json
{
    "totalDestination": 5,
    "budget": 100000,
    "startDateMillis": 1655954364681
}
```

### Response: 200
```json
{
    "status": "Success",
    "message": "Sucessfully get data.",
    "data": {
        "tourplandates": [
            {
                "date_millis": 1655954364681,
                "destinations": [
                    {
                        "id": 11,
                        "name": "Bali Swing",
                        "description": "Bali Swing Tegalalang yang menggantung di antara dua pohon palem ini terletak di desa Tegalalang, utara Ubud, Gianyar, Bali. Ayunan ini menghadap ke pemandangan indah yang tak lain adalah petak-petak teras sawah dan selalu ramai dikunjungi wisatawan. ",
                        "addresss": "Jl. Dewi Saraswati No.7, Bongkasa Pertiwi, Kec. Abiansemal, Kabupaten Badung, Bali 80352",
                        "city": "Badung",
                        "lat": -8.49046,
                        "lon": 115.23971,
                        "imageUrl": "https://storage.googleapis.com/racana_img/Bali%20Swing.jpg",
                        "weekdayPrice": 500000,
                        "weekendHolidayPrice": 500000,
                        "rating": 4.5,
                        "categoryId": 7
                    },
                    {
                        "id": 70,
                        "name": "Tari Barong dan Keris\r\n",
                        "description": "Tari Barong adalah tarian khas Bali yang menggambarkan pertarungan antara kebajikan (dharma) dan kebatilan (adharma). Wujud kebajikan dilakonkan oleh Barong sementara wujud kebatilan dimainkan oleh Rangda, yaitu sosok yang menyeramkan dengan dua taring ru",
                        "addresss": "Jl. Raya Batubulan No.295X, Batubulan, Kec. Sukawati, Kabupaten Gianyar, Bali 80582",
                        "city": "Gianyar",
                        "lat": -8.6041,
                        "lon": 115.25332,
                        "imageUrl": "https://storage.googleapis.com/racana_img/Barong%20_%20Kris%20Dance.jpg",
                        "weekdayPrice": 100000,
                        "weekendHolidayPrice": 100000,
                        "rating": 3.5,
                        "categoryId": 4
                    }
                ]
            }
        ]
    }
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃
_________________________________________________
Powered By: [postman-to-markdown](https://github.com/bautistaj/postman-to-markdown/)
