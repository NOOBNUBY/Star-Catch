# Star-Catch

코마님의 [별 잡아라](https://youtu.be/zCa1NfL-DII?si=t7Hvgcql-kegu1ZT) 플러그인 

# Library

* [Kommand](https://github.com/gooddltmdqls/kommand)
* [Atem](https://github.com/NOOBNUBY/Atem)
* [MC Server Luncher](https://github.com/monun/minecraft-server-launcher)
* [FAWE World Edit](https://github.com/IntellectualSites/FastAsyncWorldEdit)

# Command

### please apply [FastAsyncWorldEdit](https://www.spigotmc.org/resources/fastasyncworldedit.13932/) plugin

`/star` : basic command   
`/star start` : start game   
`/star save <name : str>` : save map location

# Configuration

gradle.properties

```gradle
group=com.noobnuby.plugin (group)
version=1.0.0 (ver)
paper_version=1.20.4 (papermc ver)
kommand_version=3.1.11 (kommand ver)
atem_version=1.0.0 (atem ver)
```

# Build

* `./gradlew paperJar`
* `Gradle -> Tasks -> other -> paperJar`

# Run Server

* `./start`

### It's not working!

* [READ](https://github.com/monun/minecraft-server-launcher/blob/master/README.md)

# License

[GPL-3.0](https://github.com/NOOBNUBY/kotlin-plugin-template/blob/master/LICENSE)