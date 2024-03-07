#!/bin/bash

LIBSPATH=/usr/lib:/usr/lib/x86_64-linux-gnu
export LD_LIBRARY_PATH=$LIBSPATH

APPDIR=/home/trifanya/Java/NICTP/webapp
JAVA=java
MAINCLASS=org.springframework.boot.loader.JarLauncher

cd $APPDIR
exec $JAVA \
  -server \
  -Djava.library.path=$LIBSPATH \
  -Dapp.name=nictp_webapp \
  -Xms1024m \
  -Xmx1024m \
  -XX:+UseParNewGC \
  -XX:ObjectAlignmentInBytes=16 \
  -Djava.net.preferIPv4Stack=true \
  -cp "$APPDIR/lib/*" \
  $MAINCLASS &

#  -Dlogging.level.org.springframework.web=TRACE \
#  -Dlogging.level.dev.trifanya.spring_webapp=TRACE \