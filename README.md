# Scala setup

    brew update && brew install scala sbt giter8

~/.sbtconfig

    SBT_OPTS="-XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:PermSize=256M -XX:MaxPermSize=512M"

## TextMate Scala bundle

    cd ~/Library/Application\ Support/TextMate/Bundles/
    git clone git://github.com/mads379/scala.tmbundle.git
