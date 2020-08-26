bootstrap.yml 在 application.yml 之前启动；

bootstrap.yml 配置 application 的 name、spring.cloud.config.server.git.uri、
一些encryption/decryption（加密/解密）信息；

application.yml 的信息会覆盖 bootstrap.yml 中的内容，当遇到相同的配置的时候；