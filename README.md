# Firebase Elestic Search

##### BASE_URL = "http://35.232.193.205//elasticsearch/"

```java
Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();


ElasticSearchAPI searchAPI = retrofit.create(ElasticSearchAPI.class);
```

#### Auth:

	you can use a basic auth crendetials using user and password or using an auth token base64(user:ElasticSearchPassword)

##### Here is an example for basic auth

###### ElasticSearchPassword: password get it form firebase database

```java
HashMap<String, String> headerMap = new HashMap<String, String>();
headerMap.put("Authorization", Credentials.basic("user", ElasticSearchPassword));
```

##### than you gonna use this headerMap on your call:

```java		
Call<HitsObject> call = searchAPI.search(headerMap, "AND", searchQuery);
```
#### Building the search query

```java
String searchQuery = "";
```

##### Search: with <keywords> and * for more

```java
searchQuery = searchQuery + keywords + "*";
```
	
#### Filtering:

##### filtreing using wilaya:
    
```java
searchQuery = searchQuery + " wilaya:" + <wilaya_name_for_filtering>;
```

##### filtering using type:

```java    
searchQuery = searchQuery + " type:" + <type>;
```

##### filtreing using rating:

    
```java
    searchQuery = searchQuery + " rating:" + <rating>;
```

###### ex wilaya filtrening : http://35.232.193.205//elasticsearch/products/product/_search?default_operator=AND&q=+wilaya:Sba

##### filtreing using ranges:

```java
    searchQuery = searchQuery + " price:" + [<startValue> To <endValue>];
```

###### ex price range from 0 to 300 [0,300] : http://35.232.193.205//elasticsearch/products/product/_search?default_operator=AND&q=*+price:[0 TO 300]

#### Paging using from and size:

- from: parameter defines the offset from the first result you want to fetch (default=0)
- size: parameter allows you to configure the maximum amount of hits to be returned (default=10)

##### query:
	
```java
	searchQuery = searchQuery + " from=" + <page_number>;
	searchQuery = searchQuery + " size=" + <size_of_page>;
```

###### ex : http://35.232.193.205//elasticsearch/products/product/_search?default_operator=AND&q=*&from=0&size=2

#### Sorting:

	format : sort=<sorting parameter>:{'desc' or 'asc'}

###### ex's sorting depend on price:
	
```java
	searchQuery = searchQuery + " sort=" + price:desc;
	searchQuery = searchQuery + " sort=" + price:asc;
```

###### real ex: http://35.232.193.205//elasticsearch/products/product/_search?default_operator=AND&q=*&sort=price:asc

##### make ur call like this and pass the searchQuery and headerMap for auth:

```java
Call<HitsObject> call = searchAPI.search(headerMap, "AND", searchQuery);
```

##### u will receive data on way like this inside ur call:

```java
if(response.isSuccessful()){
        hitsList = response.body().getHits();
    }

for(int i = 0; i < hitsList.getPostIndex().size(); i++){
    Log.d(TAG, "onResponse: data: " + hitsList.getProductIndex().get(i).getProduct());
    products.add(hitsList.getProductIndex().get(i).getProduct());
}
```

###### Docs: https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html
