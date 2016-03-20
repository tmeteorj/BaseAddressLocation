#!/bin/bash
IFS=,
arrays="hello,world"
for var in ${arrays[*]}
do
echo $var
done

