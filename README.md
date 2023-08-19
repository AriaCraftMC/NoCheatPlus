
NoCheatPlus
---------
[![Discord](https://img.shields.io/discord/598285007496151098?label=discord&logo=discord)](https://discord.gg/NASKHYc)

Updated-NoCheatPlus 是著名反作弊插件 NoCheatPlus 的延续，由 [NeatMonster](https://github.com/NeatMonster) 和 [Asofold](https://github.com/asofold) 在 [Evenprime](https://github.com/Evenprime) 创建的 [NoCheat](https://github.com/md-5/NoCheat) 代码基础上推出。

NoCheatPlus 试图执行 "vanilla Minecraft "机制，并防止玩家滥用 Minecraft 或其协议中的弱点，从而使您的服务器更加安全。NoCheatPlus 分为不同部分，执行各种检查以测试玩家的行为，涵盖范围很广，包括飞行和加速、战斗黑客、快速破块和核弹攻击、库存黑客、聊天垃圾和其他类型的恶意行为。

此项目由[nuym](https//github.com/nuym)大力驱动，为 AriaCraft 服务器环境独特优化，遵守 GPL-3.0 进行开发，你可以在自己的服务器使用本项目，但请不要更改 NoCheatPlus 贡献者（包括我）在内的著作权，不提供任何技术支持。


DMCA
---------
* 如果您对版权问题有任何疑问，请发送电子邮件至 `1006800345@qq.com`。

Installation
---------
* [安装 Spigot 服务器](https://github.com/Updated-NoCheatPlus/NoCheatPlus/#obtain-a-build-of-spigot)
* [下载 NoCheatPlus](https://github.com/Updated-NoCheatPlus/NoCheatPlus/#download)
* 将 NoCheatPlus.jar 放入插件文件夹。
* 启动 Spigot/CraftBukkit 服务器。(使用 /reload 可能会对仍在线的玩家产生不必要的副作用，同时也会对复杂插件和跨插件依赖性产生影响，因此我们不推荐使用。通常情况下，它应该能与 NCP 配合使用）。

提示
---------
* 请确保您的 Spigot/CraftBukkit 和 NoCheatPlus 版本相匹配。最新版本的 NCP 兼容各种 CraftBukkit/Spigot 版本。
* 不要在 config.yml 文件中使用制表符。
* 使用 [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/)，可充分提高战斗检查和其他检查的效率。使用 NCP 支持的 ProtocolLib 版本至关重要，否则某些检查将失效。
* 有关与 mcMMO、citizens 等其他插件的兼容性，请查看 [CompatNoCheatPlus](https://github.com/Updated-NoCheatPlus/CompatNoCheatPlus)。

链接
---------

###### 下载
* [Jenkins (current)](https://ci.codemc.io/job/Updated-NoCheatPlus/job/Updated-NoCheatPlus/)
* [BukkitDev (legacy)](https://dev.bukkit.org/projects/nocheatplus/files/)
* [SpigotMC (legacy)](https://www.spigotmc.org/resources/nocheatplus2015-07-25.26/updates)
* [Jenkins (legacy)](https://ci.md-5.net/job/NoCheatPlus/)

###### 支持和文档
* [Issues/Tickets](https://github.com/Updated-NoCheatPlus/NoCheatPlus/issues)
* [Wiki](https://github.com/Updated-NoCheatPlus/Docs)
* [Configuration](https://github.com/Updated-NoCheatPlus/Docs#configuration)
* [Permissions](https://github.com/Updated-NoCheatPlus/Docs/blob/master/Settings/Permissions.md)
* [Commands](https://github.com/Updated-NoCheatPlus/Docs/blob/master/Settings/Commands.md)

###### 开发人员
* [License (GPLv3)](https://github.com/Updated-NoCheatPlus/NoCheatPlus/blob/master/LICENSE.txt)
* [API](https://github.com/Updated-NoCheatPlus/Docs/blob/master/Development/API.md)
* [Contribute](https://github.com/Updated-NoCheatPlus/NoCheatPlus/blob/master/CONTRIBUTING.md)

###### 相关插件
* [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/)
* [CompatNoCheatPlus](https://dev.bukkit.org/projects/compatnocheatplus-cncp/)

###### 获得 Spigot 的构建
* [获取最新的 BuildTools.jar](https://hub.spigotmc.org/jenkins/job/BuildTools/)
* [按照说明运行](https://www.spigotmc.org/wiki/buildtools/)
* ([服务器安装说明](https://www.spigotmc.org/wiki/spigot-installation/))

Compiling NoCheatPlus
---------
* 我们使用 [Maven](http://maven.apache.org/download.cgi) 3 处理依赖关系。经 Eclipse 和 Jenkins 测试，我们使用的是 Maven 3.3.9。
* 你可以使用以下 Maven 目标进行编译："mvn clean package"，以编译不依赖于非公开下载资源的任何 "非免费 "模块，如 CraftBukkit/Spigot 服务器 jar - 基于反射的兼容性模块仍然包含在内。
* 要（重新）构建 "非免费 "兼容模块，请使用 `-P nonfree_build`，并通过配置文件（如 `-P cbdev`）激活相应模块以进行构建--请参阅下表。
* 要编译完全兼容的模块，可以使用以下目标：`mvn clean package -P nonfree_build -P all`。
* 可以手动安装专用兼容模块所需的 "非免费 "jar 文件依赖项，因为本地 maven 资源库可能缺少这些依赖项。
嵌入了 maven 的 Eclipse 示例：
添加一个新的 maven 构建运行配置，并适当命名，例如```安装 CB 1.7.5```。
将目标设置为 ``install:install-file -Dfile=<PATH TO JAR> -DgroupId=org.bukkit -DartifactId=craftbukkit -Dversion=1.7.5-R0.1-SNAPSHOT -Dpackaging=jar```.
在 Windows 上，<PATH TO JAR> 可能看起来像这样：  ```X:\...\craftbukkit\3042\craftbukkit-1.7.5-R0.1-20140408.020329-16.jar```
为了让它运行，您可能需要设置基本目录，例如```${workspace_loc}```，但这似乎并不重要。
请在文件名旁设置正确的版本。在较新版本的 maven 中，目标可能会简化很多，因为 jar 中的 pom 文件会被解析。
  * 最新版本的 BuildTools.jar 会自动将创建的服务器 jar 安装到本地 .m2 资源库（例如 linux 上），前提是配置路径是标准的。因此，如果您已经运行 BuildTools.jar 在该机器/环境上生成了服务器 jars，那么在使用特定配置文件构建 NCP 时，就无需再手动安装了。

与启用/禁用包含/构建 "非免费 "兼容模块有关的选项和配置文件。

| Profile | Parameter | Description |
| :------------------ | :-------------- | :-------------- |
| `-P nonfree_build` | _none_ | Enable building "non free" compatibility modules. |
| _none_ | _none_ | The "non free" modules won't be included. |

Profiles for choice of "non free" compatibility modules to build:

| Profile | Description |
| :------------------ | :-------------- |
| _none_ | 默认构建不包含任何本地访问模块，这可能会造成与旧版本 Minecraft 的兼容性问题。这里包含了基于反射的模块。|
| `-P all` | All compatibility modules. |
| `-P cbdev` | Spigot 1.12 R1 (MC 1.12-1.12.2). |
| `-P spigot1_11_r1` | Spigot 1.11 R1 (MC 1.11-1.11.2). |
| `-P spigot1_10_r1` | Spigot 1.10 R1 (MC 1.10-1.10.2). |
| `-P spigot1_9_r2` | Spigot 1.9 R2 (MC 1.9.4). |
| `-P spigot1_8_r3` | Spigot 1.8 R3 (MC 1.8.4-1.8.8). |
| `-P spigot1_7_r4` | Spigot 1.7 R4 (MC 1.7.10). |
| `-P cblegacy` | The pre-DMCA CraftBukkit builds. |
