#kill first
NAME="spring-boot:run"
echo $NAME
ID=`ps -ef | grep "$NAME" | grep -v "grep" | awk '{print $2}'`
for id in $ID
do
kill -9 $id
echo "killed $id"
done
echo "killed"

