#!/bin/bash
rm -f src/org/galaxyworld/beangenerator/util/Version.java
git rev-list HEAD | sort > config.git-hash
LOCALVER=`wc -l config.git-hash | awk '{print $1}'`
if [ $LOCALVER \> 1 ] ; then
    VER=`git rev-list origin/master | sort | join config.git-hash - | wc -l | awk '{print $1}'`
    if [ $VER != $LOCALVER ] ; then
        VER="$VER+$(($LOCALVER-$VER))"
    fi
    if git status | grep -q "modified:" ; then
        VER="${VER}M"
    fi
    VER="$VER $(git rev-list HEAD -n 1 | cut -c 1-7)"
    GIT_VERSION=r$VER
else
    GIT_VERSION=
    VER="x"
fi
rm -f config.git-hash
API=`grep 'BUILD' < build.version.number | sed -e 's/.* \([1-9][0-9]*\).*/\1/'`
FULL_VERSION=0.$API.$VER

cat Version.java.template | sed -e "s/\$FULL_VERSION/$FULL_VERSION/g" -e "s/\$GIT_VERSION/$GIT_VERSION/g" > src/org/galaxyworld/beangenerator/util/Version.java

echo "Generated Version.java"