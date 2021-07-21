export DISPLAY=:1
rm /tmp/.X1-lock
Xvfb $DISPLAY -screen 0 1400x900x24 -ac +extension GLX +render &
fluxbox &
x11vnc -display $DISPLAY -bg -forever -nopw -quiet -listen 0.0.0.0 -xkb

java -jar ModTheSpire.jar --skip-launcher --skip-intro --debug --profile Default