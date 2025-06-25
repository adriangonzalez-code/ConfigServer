```toml
name = 'Create User'
method = 'POST'
url = 'http://localhost:8080/api/users'
sortWeight = 1000000
id = 'ed8a06a3-eacd-43f6-9f22-4d21c36d06f5'

[body]
type = 'JSON'
raw = '''
{
  "firstName" : "John",
  "lastName" : "Doe",
  "email" : "john@mail.com",
  "password" : "123",
  "roleId" : 1
}'''
```
