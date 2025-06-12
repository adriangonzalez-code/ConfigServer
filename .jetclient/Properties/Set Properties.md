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
    "key": "management.endpoint.health.show-details",
    "value": "always"
  },
  {
    "key": "management.endpoint.health.show-components",
    "value": "always"
  },
  {
    "key": "management.endpoints.web.cors.allowed-origins",
    "value": "*"
  },
  {
    "key": "management.endpoints.web.cors.allowed-methods",
    "value": "*"
  },
  {
    "key" : "mongo.user.password",
    "value" : "!Welcome!01234",
    "secret": true
  }
]'''
```
