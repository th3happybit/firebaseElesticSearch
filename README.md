# Firebase Elestic Search

BASE_URL = "http://35.232.193.205//elasticsearch/"

Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

ElasticSearchAPI searchAPI = retrofit.create(ElasticSearchAPI.class);

Auth:

you can use a basic auth crendetials using user and password or using an auth token base64(user:ElasticSearchPassword)

Here is an example for basic auth

ElasticSearchPassword: password get it form firebase database

HashMap<String, String> headerMap = new HashMap<String, String>();
headerMap.put("Authorization", Credentials.basic("user", ElasticSearchPassword));

than you gonna use this headerMap on your call:
		Call<HitsObject> call = searchAPI.search(headerMap, "AND", searchQuery);

Building the search query

String searchQuery = "";

Search: with <keywords> and * for more

    searchQuery = searchQuery + keywords + "*";

Filtering:

filtreing using wilaya:
    searchQuery = searchQuery + " wilaya:" + [wilaya name for filtering];

filtering using type:
    searchQuery = searchQuery + " type:" + [type];

filtreing using rating
    searchQuery = searchQuery + " rating:" + [rating];

ex wilaya filtrening : http://35.232.193.205//elasticsearch/products/product/_search?q=+wilaya:Sba

paging using from and size:

from: parameter defines the offset from the first result you want to fetch (default=0)
size: parameter allows you to configure the maximum amount of hits to be returned (default=10)

query:
	searchQuery = searchQuery + " from=" + [page_number];
	searchQuery = searchQuery + " size=" + [size_of_page];

ex : http://35.232.193.205//elasticsearch/products/product/_search?q=*&from=0&size=2

Sorting:
format : sort=[sorting parameter]:{'desc' or 'asc'}

ex's sorting depend on price:
	searchQuery = searchQuery + " sort=" + price:desc;
	searchQuery = searchQuery + " sort=" + price:asc;

real ex: http://35.232.193.205//elasticsearch/products/product/_search?q=*&sort=price:asc

make ur call like this and pass the searchQuery and headerMap for auth:

Call<HitsObject> call = searchAPI.search(headerMap, "AND", searchQuery);

u will receive data on something like this khademtek xD:

if(response.isSuccessful()){
        hitsList = response.body().getHits();
    }

for(int i = 0; i < hitsList.getPostIndex().size(); i++){
    Log.d(TAG, "onResponse: data: " + hitsList.getProductIndex().get(i).getProduct());
    products.add(hitsList.getProductIndex().get(i).getProduct());
}
