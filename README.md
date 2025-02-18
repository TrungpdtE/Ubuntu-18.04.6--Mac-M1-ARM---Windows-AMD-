# Ubuntu-18.04.6-Mac-M1




# Install SSH

sudo apt íntall openssh-server -y

! openssh-server (>= 1:7.6p1-4) but it is not going to be installed 

-> sudo apt purge openssh-client 
-> sudo apt íntall openssh-server -y

test SSH:
-> sudo systemctl status ssh

if ssh is not enabled
-> sudo systemctl restart ssh

