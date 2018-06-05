# Mongodb 认证

认证时必须提供 *username*、*password* and the *authentication database* associated with that user

## 启用用户名/密码认证

> 登录的用户必须在 `admin` 数据库有 `userAdmin` 或 `userAdminAnyDatabase` 角色权限

**无访问控制启动**
mongod --port 27017 --dbpath /data/db1

> 需要确保 /data/db1 已经存在

**连接实例**

mongo --port 27017

**创建管理员**

在 `admin` 认证数据库中创建 `zrkj` 用户，并分配 `userAdminAnyDatabase` 角色

```
use admin
db.createUser(
  {
    user: "zrkj",
    pwd: "zrkj123.",
    roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
  }
)
```

**使用访问控制重启实例**

mongod --auth --port 27017 --dbpath /data/db1

或者在配置文件中, 通过`security.authorization` 进行设置

> 此时客户端连接实例必须进行认证

**使用创建的用户连接实例**
两种方式：
- 在连接时进行认证
  mongo --port 27017 -u "zrkj" -p "zrkj123." --authenticationDatabase "admin"
- 在连接后进行认证
  ```
  mongo --port 27017
  use admin
  db.auth("zrkj","zrkj123.")
  ```

**创建额外用户**

> 此时如果需要也可以创建额外的用户。可以分配任意内建角色或自定义角色给用户

> 此时 `zrkj` 用户只拥有管理用户和角色的权限

创建 `idlink` 用户，对于 `idlink` 数据库拥有 `readWrite` 角色且拥有 `read` 角色在 `reporting` 数据库

```
use idlink
db.createUser(
  {
    user: "idlink",
    pwd: "zrkj123.",
    roles: [ { role: "readWrite", db: "idlink" },
             { role: "read", db: "reporting" } ]
  }
)
```

**使用idlink用户认证连接**

mongo --port 27017 -u "idlink" -p "zrkj123." --authenticationDatabase "idlink"

插入数据  
```
use idlink
db.foo.insert( { x: 1, y: 1 } )
```

## 认证方式

> 使用额外的认证方式，例如：Kerberos、LDAP、x.509 Client Certificate，必须创建 `$external` 数据库。
  必须添加 `Kerberos principal` 作为用户名，不需要指定特定的密码

### 用户名/密码认证

```
use reporting
db.createUser(
  {
    user: "reportsUser",
    pwd: "12345678",
    roles: [
       { role: "read", db: "reporting" },
       { role: "read", db: "products" },
       { role: "read", db: "sales" },
       { role: "readWrite", db: "accounts" }
    ]
  }
)
```

### Kerberos 认证

```
use $external
db.createUser(
    {
      user: "reportingapp@EXAMPLE.NET",
      roles: [
         { role: "read", db: "records" }
      ]
    }
)
```
[Kerberos on Linux](https://docs.mongodb.com/v3.4/tutorial/control-access-to-mongodb-with-kerberos-authentication/)
[Kerberos on  Windows](https://docs.mongodb.com/v3.4/tutorial/control-access-to-mongodb-windows-with-kerberos-authentication/)

### LDAP 认证

```
use $external
db.createUser(
    {
      user: "reporting",
      roles: [
         { role: "read", db: "records" }
      ]
    }
)
```

[Authenticate Using SASL and LDAP with ActiveDirectory](https://docs.mongodb.com/v3.4/tutorial/configure-ldap-sasl-activedirectory/)  
[Authenticate Using SASL and LDAP with OpenLDAP](https://docs.mongodb.com/v3.4/tutorial/configure-ldap-sasl-activedirectory/)

### x.509 Client Certificate 认证

```
use $external
db.createUser(
    {
      user: "CN=myName,OU=myOrgUnit,O=myOrg,L=myLocality,ST=myState,C=myCountry",
      roles: [
         { role: "read", db: "records" }
      ]
    }
)
```

## 认证机制

> 在3.0以后使用 `SCRAM`(使用 SHA-1 hash算法) 作为默认的认证机制，之前是使用 `MONGODB-CR`为默认认证机制

使用 `authenticationMechanisms` 指定认证机制

> 可选值：SCRAM-SHA-1、MONGODB-CR、MONGODB-X509、GSSAPI (Kerberos)、PLAIN (LDAP SASL)


## 角色与权限

> 一个角色可以包含其它角色，那么这个角色继承了包含角色的权限。
  一个在 `admin` 数据库中的角色能继承任何数据库中的角色权限

### 数据库角色

- read: 非系统集合和部分系统集合(system.indexes, system.js, system.namespaces)中读数据权限
- readWrite: 提供 `read` 角色的权限且提供非系统集合和系统集合(system.js)修改数据的权限

### 数据库管理员角色

- dbAdmin:
- dbOwner:
- userAdmin:

[Built-In Roles](https://docs.mongodb.com/v3.4/core/security-built-in-roles/)

### 集合层级访问控制

创建角色
```
use admin
db.createRole(
   {
     role: "manageOpRole",
     privileges: [
       { resource: { cluster: true }, actions: [ "killop", "inprog" ] },
       { resource: { db: "", collection: "" }, actions: [ "killCursors" ] }
     ],
     roles: []
   }
)
```

[Privilege Actions](https://docs.mongodb.com/v3.4/reference/privilege-actions/)  
[Resource](https://docs.mongodb.com/v3.4/reference/resource-document/)
