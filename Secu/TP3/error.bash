#!/usr/bin/env bash
counter=0

# You need to have created a file named text 
# of 128 bytes and its encrypted version text.enc

size=`wc -c text | gawk '{}{print $1}'`
echo $size

while [ $counter -le $size ]
do
	echo "Modify Byte " $counter
	
	# Create a copy of the file text and modify it  	
	cp text tmp.enc
	dd if=/dev/urandom of=tmp.enc bs=1 seek=$counter count=1 conv=notrunc status=none
	
	# Decrypt the file tmp.enc into tmp
	openssl enc -d -des-ecb -in tmp.enc -out tmp -K 310418387c807728 

	# Compare text.enc and temp.enc to check the differences.
	xxd text
	xxd tmp
	echo "================="

	# Clean
	rm tmp.enc tmp
	((counter++))
done
