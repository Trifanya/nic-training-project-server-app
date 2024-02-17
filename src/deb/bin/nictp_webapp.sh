#!/bin/bash

LIBSPATH=/usr/lib:/usr/lib/x86_64-linux-gnu
export LD_LIBRARY_PATH=$LIBSPATH

APPDIR=/home/trifanya/Java/NIC/training-project/webapp/deb
JAVA=java
#MAINCLASS=dev.trifanya.spring_webapp.SpringWebApp
MAINCLASS=org.springframework.boot.loader.JarLauncher

cd $APPDIR
q
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