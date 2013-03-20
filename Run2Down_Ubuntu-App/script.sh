a=$(curl home.iitk.ac.in/~vijaykes/mydata.txt)
echo $a
./update_with_sink.sh $1 $a
