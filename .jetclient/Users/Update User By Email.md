```toml
name = 'Update User By Email'
method = 'PUT'
url = 'http://localhost:8080/api/users/email@mail.com'
sortWeight = 4000000
id = '223c5814-f49a-4eb2-9c28-2ecec33c5d20'

[body]
type = 'JSON'
raw = '''
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "email@mail.com",
  "roleId": 2,
}'''
```
