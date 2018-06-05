# NIO Tutorial

NIO 将填充、排放缓冲区等时间性I/O活动移回操作系统，从而大大提高了操作速度。

NIO组件：
- 通道和缓冲区(Channels and Buffers)：数据从缓冲区写入通道，并从通道读取到缓冲区
- 选择器(Selectors)：监视多个通道的对象，如数据到达、连接打开等。
- 非阻塞I/O(Non-blocking I/O)：提供非阻塞I/O功能
