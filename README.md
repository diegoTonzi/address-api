# address-api

CRUD API to manage addresses with CEP verification

# Technologies

- Java 8
- SpringBoot
- Junit

# EndPoints

- [GET] /cep-api/cep/{cep} - Retrieves the address find by {cep} number - Note: inform the cep number without '-'.
- [GET] /cep-api/address/{id} - Get address by id, replace {id} to address id;
- [PUT] /cep-api/address/ - Update address if exist, all required fields are not null(street, cep, number, city, state) and cep is valid;
- [POST] /cep-api/address/ - Create new address if all required fields are not null(street, cep, number, city, state) and cep is valid;
- [DELETE] /cep-api/address/{id} - Remove user address by id if exist, replace {id} to user address id.
