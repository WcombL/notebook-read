# RabbitMQ

## 安装

`sudo lsb_release -a`查看版本号
```
  echo "deb https://dl.bintray.com/rabbitmq/debian {distribution} main" | sudo tee /etc/apt/sources.list.d/bintray.rabbitmq.list

  wget -O- https://dl.bintray.com/rabbitmq/Keys/rabbitmq-release-signing-key.asc | sudo apt-key add -

  wget -O- https://www.rabbitmq.com/rabbitmq-release-signing-key.asc | sudo apt-key add -

  sudo apt-get update

  sudo apt-get install rabbitmq-server
```

## 启停RabbitMQ

```
ps aux | grep epmd
ps aux | grep erl
Kill the process with kill -9 {pid of rabbitmq process}

service rabbitmq-server start
service rabbitmq-server stop
```

## 可视化查看
```
rabbitmq-plugins enable rabbitmq_management
http://127.0.0.1:15672/
用户名/密码: guest/guest
rabbitmqctl stop 停止服务
```

## RabbitMQ配置

默认配置文件路径
- Generic UNIX - $RABBITMQ_HOME/etc/rabbitmq/
- Debian - /etc/rabbitmq/
- RPM - /etc/rabbitmq/
- Mac OSX (Homebrew) - ${install_prefix}/etc/rabbitmq/, the Homebrew prefix is usually /usr/local
- Windows - %APPDATA%\RabbitMQ\

`rabbitmq-env.conf`如果改变请设置环境变量`RABBITMQ_CONF_ENV_FILE`，Windows中是`rabbitmq-env.bat`  
`rabbitmq.conf`如果改变请设置环境变量`RABBITMQ_CONFIG_FILE`

[配置参数](https://www.rabbitmq.com/configure.html#config-location)

**端口介绍**
- 4369: epmd, a peer discovery service used by RabbitMQ nodes and CLI tools
- 5672, 5671: used by AMQP 0-9-1 and 1.0 clients without and with TLS
- 25672: used for inter-node and CLI tools communication (Erlang distribution server port) and is allocated from a dynamic range (limited to a single port by default, computed as AMQP port + 20000). Unless external connections on these ports are really necessary (e.g. the cluster uses federation or CLI tools are used on machines outside the subnet), these ports should not be publicly exposed. See networking guide for details.
- 35672-35682: used by CLI tools (Erlang distribution client ports) for communication with nodes and is allocated from a dynamic range (computed as server distribution port + 10000 through server distribution port + 10010). See networking guide for details.
- 15672: HTTP API clients, management UI and rabbitmqadmin (only if the management plugin is enabled)
- 61613, 61614: STOMP clients without and with TLS (only if the STOMP plugin is enabled)
- 1883, 8883: (MQTT clients without and with TLS, if the MQTT plugin is enabled
- 15674: STOMP-over-WebSockets clients (only if the Web STOMP plugin is enabled)
- 15675: MQTT-over-WebSockets clients (only if the Web MQTT plugin is enabled)



## 问题
rabbitmq-server : Depends: erlang-nox (>= 1:16.b.3) but it is not going to be installed or esl-erlang but it is not installable

```
wget https://packages.erlang-solutions.com/erlang-solutions_1.0_all.deb
sudo dpkg -i erlang-solutions_1.0_all.deb
sudo apt-get update
sudo apt-get install esl-erlang=1:19.3.6
```
