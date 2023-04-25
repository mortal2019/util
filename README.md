# mortal2019-工具类集成

> **utils**
>
> * **core** 核心包
> * **util** 工具包
> * **model** 模型包
> * **cloud-util** 微服务工具包
> * **weixin** 微信包(开发中)
> * **aliyun**   阿里云包(开发中)

***

***部分代码收录了开源作者的代码, 分享以下开源作者***

* [liuhuiyu2004工具包](https://github.com/liuhuiyu2004/util)
* [hutool工具包](https://github.com/dromara/hutool)

#### 使用说明

1. 下载[utils.zip](https://github.com/mortal2019/utils/releases)    ([Releases · mortal2019/utils (github.com)](https://github.com/mortal2019/utils/releases))
2. 解压文件到本地
3. 将解压的jar包加入maven本地仓库
4. 目前可用的jar包有: 
   * core-2023.1.0.jar          -- 核心包
   * model-2023.1.0.jar      --  模型包
   * util-2023.1.0.jar            --  工具包

```bash
mvn install:install-file -Dfile=本地路径\文件.jar -DgroupId=com.mortal2019 -DartifactId=jar包名字(core/model/util...) -Dversion=2023.1.0 -Dpackaging=jar
```

