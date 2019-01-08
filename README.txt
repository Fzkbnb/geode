1.概述
gemfire开源版，分布式内存数据库，支持缓存操作，OQL查询，springdata操作，分布式事务等，性能极好（好于ignite）。
主要概念和组件：服务端节点，客户端节点，定位器（负责节点发现和负载均衡），区域（类似于数据库表，便于支持OQL和springdata,便于个性化配置）
相关链接：
https://geode.apache.org/releases/ 下载地址
https://geode.apache.org/docs/ 官方使用文档
https://docs.spring.io/spring-data-gemfire/docs/current/reference/html/#bootstrap:namespace:xml spring集成文档
2.快速启动
（1）下载安装geode(),配置环境变量bin,并测试确保安装成功;
（2）根据用途，可分为单机模式，CS模式启动方式。
单机模式下，直接在应用程序端启动本地节点即可；
CS模式下，可在命令行启动服务节点（单服务节点或集群都可以），而应用端启动客户端节点即可

命令行启动：
cmd>gfsh
gfsh> start locator --name=locator_test
gfsh>start server --name=server1 --server-port=40404
#gfsh>start server --name=server2 --server-port=40405
gfsh>create region --name=Person 【--type=REPLICATE(默认PARTITION)】

应用端启动：可使用注解方式启动，也可使用javaAPI启动，
本demo提供了两种启动方式（均采用默认端口：server40404，locator10334）：
(1)GeodeStart类注解启动
(2)BaseTest类中使用geodeAPI启动
如需启动多节点，只要在命令行执行上述服务节点创建命令即可

3.其他常用gfsh命令：
关闭服务节点：stop server --name=server1
关闭定位器：stop locator --name=locator_test
打开网页控制台工具：start pulse