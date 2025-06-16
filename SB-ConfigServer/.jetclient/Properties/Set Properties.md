```toml
name = 'Set Properties'
method = 'PUT'
url = 'http://localhost:8080/api/properties/2/scope'
sortWeight = 1000000
id = '1b808479-e1bb-4807-b006-17e0707ed3d0'

[body]
type = 'JSON'
raw = '''
[
  {
    "key": "management.endpoints.web.exposure.include",
    "value": "info,health,metrics,prometheus,loggers,env,threaddump,heapdump,scheduledtasks"
  },
 
  {
    "key" : "mongo.user.password",
    "value" : "!Welcome!01234",
    "secret": true
  }
]'''
```
