```toml
name = 'Create User'
method = 'POST'
url = 'http://localhost:8080/api/users'
sortWeight = 1000000
id = '6187680d-8f3b-4b48-96c1-ab316d659800'

[body]
type = 'JSON'
raw = '''
{
  "firstName": "Juan",
  "lastName": "Perez",
  "email": "juan@mail.com",
  "password": "password123",
  "roleId": 1,
}'''
```
