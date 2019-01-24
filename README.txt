1.概述
gemfire开源版，分布式内存数据库，支持缓存操作，OQL查询，springdata操作，分布式事务等，性能极好（好于ignite）。
主要概念和组件：服务端节点，客户端节点，定位器（负责节点发现和负载均衡），区域（类似于数据库表，便于支持OQL和springdata,便于个性化配置）
相关链接：
https://geode.apache.org/releases/ 下载地址
https://geode.apache.org/docs/ 官方使用文档
https://docs.spring.io/spring-data-gemfire/docs/current/reference/html/#bootstrap:namespace:xml spring集成文档
2.快速启动
（1）下载安装geode,配置环境变量bin,并测试确保安装成功;
（2）根据用途，可分为单机模式，CS模式启动方式。
单机模式下，直接在应用程序端启动本地节点即可；
CS模式下，可在命令行或java应用程序启动服务节点，而java应用端启动客户端节点即可

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

3.其他常用gfsh命令：
关闭服务节点：stop server --name=server1
关闭定位器：stop locator --name=locator_test
打开网页控制台工具：start pulse

4.技术总结
一.节点拓扑模式
1.p2p模式：各节点直接嵌入到应用程序，节点缓存和应用绑定，节点之间可以通信用于交换缓存信息，支持复制模式和分区模式，类似于ignite。且分区模式中支持单跳（最快访问算法）。
2.cs模式：服务端为客户端提供缓存，服务端之间可以相互访问复制。
3.wan模式（多站点模式）：p2p模式属于节点紧耦合，扩展性不好，wan可解决。
二.节点间通信方式：通过IP多播技术进行集群发现，通过tcp通信
三.数据分发方式：
（1）local:不分发数据到其他节点。（gemfire节点有自动持久化到磁盘，支持查询，事务等功能，这是他相对于原生本地缓存的优势，也是local模式与本地缓存区别所在。）
（2）Distribute-no-ack：单点异步分发
（3）Distribute-ack：单点同步分发
（4）Global：全局同步分发
四.镜像类型（决定节点接收到数据后的处理方式）：
（1）none:仅当当前节点包含新数据key时才会保存新数据，该节点一般用于保存其他某节点的子集
（2）keys:数据区域仅保存key以节省空间，当有请求到达该节点时会去根据key去其他节点拉取数据返回。该节点用于无法预知数据会被哪一节点访问时。
  (3) keys-values：全量保存着数据。该节点用于提供需要立即访问的数据。
五.持久化机制：支持操作内存时同步写入磁盘和固定间隔异步写，后者适用于可容忍应用宕机时最后部分数据丢失的场景。
六.溢出机制：gemfire支持在节点内存不足时将根据LRU策略将内存写入磁盘(最少使用的数据会被保存在磁盘)，可与持久化机制配合使用。
七.支持缓存事务和JTA事务