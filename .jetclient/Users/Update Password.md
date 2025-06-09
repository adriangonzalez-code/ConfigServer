```toml
name = 'Update Password'
method = 'PUT'
url = 'http://localhost:8080/api/users/1/password'
sortWeight = 6000000
id = '9f7b174c-dab6-4986-b7a1-4b359eae7d19'

[body]
type = 'JSON'
raw = '''
{
  "newPassword": "Welcome123!"
}'''
```
