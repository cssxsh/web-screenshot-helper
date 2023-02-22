# [Web Screenshot Helper](https://github.com/cssxsh/web-screenshot-helper)

> 基于 Mirai Selenium Plugin 的 网页截图插件

相关项目:  
[Mirai Selenium Plugin](https://github.com/cssxsh/mirai-selenium-plugin) 前置插件，用于 Selenium 框架的初始化

权限ID `xyz.cssxsh.mirai.plugin.web-screenshot-helper:*`

截图采用的是白名单模式  
你需要在 `config/xyz.cssxsh.mirai.plugin.web-screenshot-helper/script` 下创建建一个 `$host.js` 脚本  
例如 `www.example.com.js` , 脚本的内容决定截图的内容，一般取 `return document.body;` 即截取整个窗口  
如果想定制特殊的截图效果，比如去除广告，顶栏之类的。可以爱发电投食。

## 关键词

*   `截图 https://...` 将会启用无头浏览器截取网页图片

## 安装

### MCL 指令安装

**请确认 mcl.jar 的版本是 2.1.0+**  
`./mcl --update-package xyz.cssxsh:web-screenshot-helper --channel maven-stable --type plugins`

### 手动安装

1. 从 [Releases](https://github.com/cssxsh/web-screenshot-helper/releases) 或者 [Maven](https://repo1.maven.org/maven2/xyz/cssxsh/web-screenshot-helper/) 下载 `mirai2.jar`
2. 将其放入 `plugins` 文件夹中

## [爱发电](https://afdian.net/@cssxsh)

![afdian](.github/afdian.jpg)