#!/usr/bin/env bash

# FIRST ARGUMENT OF THE COMMAND IS THE DIGEST FILE NAME

for line in $(cat $1); 
do 
	len=$(printf $line | wc -c;)
	len_bits=$(($len*8))
	list=$(john --list=formats | grep -i $len_bits)
	echo "----------"
	echo "length:" $len_bits "bits"
	echo $list	
	if [ $len -eq 0 ]
	then
        	echo "Pas de digest"
	fi
done
