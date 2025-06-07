```toml
name = 'Update User By Id'
method = 'PUT'
url = 'http://localhost:8080/api/users/1'
sortWeight = 4000000
id = '223c5814-f49a-4eb2-9c28-2ecec33c5d20'

[body]
type = 'JSON'
raw = '''
{
  "id": 1,
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane@mail.com",
  "active": true,
  "roleId": 2,
  "password": "newpassword123"
}'''
```
