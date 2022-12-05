#!/bin/bash

inkscape -C -e banner.png banner.svg
inkscape -C -e dialog.png dialog.svg

convert banner.png ../desktop/src/main/packaging/windows/banner.bmp
convert dialog.png ../desktop/src/main/packaging/windows/dialog.bmp

inkscape -C -e icon16.png -h 16 icon.svg
inkscape -C -e icon32.png -h 32 icon.svg
inkscape -C -e icon48.png -h 48 icon.svg
inkscape -C -e icon256.png -h 256 icon.svg
convert icon16.png icon32.png icon48.png icon256.png ../desktop/src/main/packaging/windows/lanchat.ico

inkscape -C -h 500 -e ../desktop/src/main/packaging/deb/lanchat.png icon.svg
