#!/bin/bash

inkscape -C -e banner.png banner.svg
inkscape -C -e dialog.png dialog.svg

convert banner.png ../desktop/src/main/packaging/windows/banner.bmp
convert dialog.png ../desktop/src/main/packaging/windows/dialog.bmp

convert icon128.png ../desktop/src/main/packaging/windows/lanchat.ico

inkscape -C -h 500 -e ../desktop/src/main/packaging/deb/lanchat.png icon.svg
