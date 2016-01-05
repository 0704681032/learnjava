#!/bin/bash
basepath=$(cd "$(dirname "$0")"; pwd);
cd $basepath/../;
projectDir=`pwd`
#需要读取的配置文件
defaultFile="$projectDir""/sh/crontab_sh.sh"
file=$defaultFile
if [ $1 ]; then
    file=$1
fi
echo $file
##检查配置文件是否符合规范
function checkFile() 
{
	firstLine=`head -1 $file`
	lastLine=`tail -1 $file`
	project=`echo $firstLine | awk '{ print $1 }'`
	project2=`echo $lastLine | awk '{ print $1 }'`
	if [ ! "$project" == "$project2" ]; then
		echo "配置文件不合法,项目名称不一致!"
		exit 1
	fi
	if [ ! "$firstLine" == "$project ""crontab begin" ]; then
		echo "配置文件不合法,首行不合法"
		exit 1
	fi
	if [ ! "$lastLine" == "$project ""crontab end" ]; then
		echo "配置文件不合法,尾行不合法"
		exit 1
	fi
}
checkFile
#已经存在的crontab
originFile="_tmpcrontab_o"
crontab -l > $originFile
crontabTmp="_tmpcrontab"
>$crontabTmp
startIndex=`grep -n "$firstLine" $originFile | awk -F":" '{print $1}'`
endIndex=`grep -n "$lastLine" $originFile | awk -F":" '{print $1}'`
echo $startIndex"||"$endIndex"||"
if test -n "$startIndex" && test -n "$endIndex"
then
	sed "${startIndex},${endIndex}d" $originFile > $crontabTmp
else
	cat $originFile > $crontabTmp
fi
echo "">>$crontabTmp
cat $file >> $crontabTmp
crontab $crontabTmp
