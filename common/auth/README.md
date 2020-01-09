##### 一、重写代码返回自定义的json格式串

```
修改异常返回格式类
org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator
```

```
修改后：
{
  "code": 401,
  "success": false,
  "msg": "invalid_client: Bad client credentials",
  "data": null
}
```

```
修改认证成功返回格式类
org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
```

```
修改后：
{
  "code": 200,
  "success": true,
  "msg": "认证成功",
  "data": {
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicjEiXSwidXNlcl9uYW1lIjoicm9vdCIsInNjb3BlIjpbImFsbCJdLCJleHAiOjE1NzY1NzMwMzAsImF1dGhvcml0aWVzIjpbInAxIl0sImp0aSI6ImE2MGI2NzU3LTAwZDMtNDhiZC05NTlhLTI2M2YzZjFlY2E0OSIsImNsaWVudF9pZCI6ImMxIn0.nOTkIDtodkgE1Ad7Fk2mqbXFksZlXMWqY7qX0PJrtVQ",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicjEiXSwidXNlcl9uYW1lIjoicm9vdCIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiJhNjBiNjc1Ny0wMGQzLTQ4YmQtOTU5YS0yNjNmM2YxZWNhNDkiLCJleHAiOjE1NzY1NzY2MzAsImF1dGhvcml0aWVzIjpbInAxIl0sImp0aSI6IjE5YTE3ZTExLWJjMTAtNGNlYS1hYTc1LTdhNzBmNTAwMjI2YiIsImNsaWVudF9pZCI6ImMxIn0.L6tW8LpvqMwmLcu2udmiYbUCsbDF2NNmh7kD5mz9nP0",
    "expires_in": 3599,
    "scope": "all",
    "jti": "a60b6757-00d3-48bd-959a-263f3f1eca49"
  }
}
```



