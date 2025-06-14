```toml
name = 'Set Users'
method = 'PUT'
url = 'http://localhost:8080/api/scopes/1/users'
sortWeight = 4000000
id = '8b90bc59-461e-40df-b0cf-6f0062cbe32f'

[body]
type = 'JSON'
raw = '''
[
  "pepe@mail.com",
  "john@mail.com",
  "other@mail.com"
]'''
```
