#!/bin/bash
#Menue fuer LEGO Mindstorms Projekt

SERVER_DIR="/home/pi/roborace/roboserver/"
TARGET_FOLDER=" "
GIT_FOLDER="/home/pi/roborace/"

PS3='Bitte Auswahl treffen: '
options=("Start Server" "Stop Server" "Pull" "Clone" "Exit")
select opt in "${options[@]}"
do
	case $opt in
		"Start Server")
			npm start $SERVER_DIR >roboserver.log 2>&1 &
			exit
			;;
		"Stop Server")
			npm stop $SERVER_DIR 
			exit
			;;
		"Pull")
			cd $GIT_FOLDER
			git pull https://github.com/phllipo/RoboRace.git
			cd ~
			exit
			;;
		"Clone")
			echo "Bitte Zielordner eingeben: "
			read TARGET_FOLDER
			git clone https://github.com/phllipo/roborace $TARGET_FOLDER
			exit 
			;;
		"Exit")
			exit
			;;
		*) echo Ungueltige Eingabe;;
	esac
done


