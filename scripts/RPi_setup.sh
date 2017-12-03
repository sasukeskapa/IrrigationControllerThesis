#!/bin/bash
#RPi setup:

#before running this script:
    #install raspbian stretch on the Raspberry Pi
    #change the password to something secure

#necessary variables
downloadFromLocation="vaioThesis:Dropbox/egyetem/not_passed_yet/szakdoga"
slaveControllerIpOrHostname="vaioThesis:"
irrigationProgramCronjob="@reboot while true; do rsync -rlpthuvz /var/www/html/remote1_sources/ $slaveControllerIpOrHostname/var/www/html/sources/ ; rsync -rlpthuvz $slaveControllerIpOrHostname/var/www/html/sources/ /var/www/html/remote1_sources/ ; sleep 1 ; done"

#initial setup
sudo apt-get update || (echo "NO INTERNET ACCESS: set up wifi from gui for internet if LAN internet access is unavailable" ; exit 1)
sudo apt-get install ssh
sudo apt-get install tmux
sudo apt-get install rsync
sudo systemctl enable ssh.service
sudo service ssh start

#RPi relay module setup
#install dependencies
sudo apt-get install oracle-java8-jdk
echo "get and install pi4j-1.2-SNAPSHOT.deb from http://pi4j.com/download.html or later versions to use Pi4J to control the GPIO"
cd
wget http://get.pi4j.com/download/pi4j-1.2-SNAPSHOT.deb
sudo dpkg -i pi4j-1.2-SNAPSHOT.deb
#get the java daemon: IrrigationController on the device and run it
cd
rsync -rthvzc --progress $downloadFromLocation/code/daemon/IrrigationControllerDaemon/out/artifacts/IrrigationControllerDaemon_jar/IrrigationControllerDaemon.jar .
sudo java -Dpi4j.linking=dynamic -classpath .:classes:/opt/pi4j/lib/'*' -jar IrrigationControllerDaemon.jar & disown

#web setup
#install dependencies
sudo apt-get install apache2
sudo apt-get install libapache2-mod-php7.0
#setting up php7 with apache2

#setting up /var/www for the project
sudo chown -R pi:www-data /var/www/
sudo chmod -R 775 /var/www/html/
#downloading the files
rsync -rthvzc --progress --delete --exclude 'html/sources/*.txt' $downloadFromLocation/code/web/ /var/www/
#set the owner and rights for the downloaded files
sudo chown -R pi:www-data /var/www/
sudo chmod -R 775 /var/www/html/

#setup syncing between the main controller and it's slave controllers
(crontab -l 2>/dev/null; echo "$irrigationProgramCronjob") | crontab -

#rsync options
#-c   skip based on checksum, not mod-time & size
#-r   recursive
#-l   copy symlinks as symlinks
#-p   preserve permissions
#-t   preserve modification times
#-h   human readable output
#-u   update (skip files that are newer on the receiver)
#-v   verbose
#-z   compress transfer
#--delete delete extraneous files from dest dirs
