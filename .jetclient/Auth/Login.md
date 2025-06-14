```toml
name = 'Login'
method = 'POST'
url = 'http://localhost:8080/api/auth/login'
sortWeight = 1000000
id = 'e046b3c1-a6da-4315-9f2f-c1d09f4be397'

[body]
type = 'JSON'
raw = '''
{
  "email": "juan@mail.com",
  "password": "password123",
}'''
```
