#!/bin/bash

DIR=$(dirname $0)
CMD="$DIR/lanchat.sh"
CLASS="de.mobanisto.lanchat.Send"

exec "$CMD" "$CLASS" "$@"
