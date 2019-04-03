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

```java
ElasticSearchPassword: password get it form firebase database

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

- Search: with "keywords" and * for more

```java
searchQuery = searchQuery + keywords + "*";
```
	
#### Filtering:

- filtreing using wilaya:
    
```java
searchQuery = searchQuery + " wilaya:" + <wilaya_name_for_filtering>;
```

- filtering using type:

```java    
searchQuery = searchQuery + " type:" + <type>;
```

- filtreing using rating:

    
```java
    searchQuery = searchQuery + " rating:" + <rating>;
```

###### ex wilaya filtrening : http://35.232.193.205//elasticsearch/products/product/_search?default_operator=AND&q=+wilaya:Sba

- filtreing using ranges:

```java
    searchQuery = searchQuery + " price:" + [<startValue> To <endValue>];
```

###### ex price range from 0 to 300 [0,300] : http://35.232.193.205//elasticsearch/products/product/_search?default_operator=AND&q=*+price:[0 TO 300]

#### Paging using Scroll Api

- scroll: paremeter will allow to configure how much time you want to save the search context
- size: maximum of hits to be returned

1. First we save the search context:

```java
	searchQuery = searchQuery + " size=" + <max_of_hits>;
	searchQuery = searchQuery + " scroll=" + <time>; //ex scroll=1m, m for minute
```

###### ex : http://35.232.193.205//elasticsearch/products/product/_search?default_operator=AND&q=*&size=3&scroll=1m

2. Retrieve the next batch of results:

```
POST /_search/scroll
with those parameters
{
   "scroll" : "1m",
   "scroll_id" : "DXF1ZXJ5QW5kRmV0Y2gBAAAAAAAAAWIWWlRvRGs0bDFTck9FRGtrUFZFRUIzUQ=="
}
```
- scroll: parameter tells Elasticsearch to keep the search context open for another 1m.
- _scroll_id: will be includes in first request result

###### every time you sent a request you will get a new batch of result

3. Clear Scroll Api 

Search context are automatically removed when the scroll timeout has been exceeded. but here is how to delete it.

```
DELETE /_search/scroll
{
    "scroll_id" : "DXF1ZXJ5QW5kRmV0Y2gBAAAAAAAAAD4WYm9laVYtZndUQlNsdDcwakFMNjU1QQ=="
}
```

#### Paging using from and size (Not really helpful):

- from: parameter defines the offset from the first result you want to fetch (default=0)
- size: parameter allows you to configure the maximum amount of hits to be returned (default=10)

- query:
	
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

##### Make ur call like this and pass the searchQuery and headerMap for auth:

```java
Call<HitsObject> call = searchAPI.search(headerMap, "AND", searchQuery);
```

- You will receive data on way like this inside ur call:

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
