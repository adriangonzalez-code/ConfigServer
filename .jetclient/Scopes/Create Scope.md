```toml
name = 'Create Scope'
method = 'POST'
url = 'http://localhost:8080/api/scopes'
sortWeight = 1000000
id = '2d816549-9482-402c-8a7d-2559776ad0c5'

[body]
type = 'JSON'
raw = '''
{
  "scopeName": "example-scope",
  "description": "This scope allows access to the example API endpoints.",
}'''
```
