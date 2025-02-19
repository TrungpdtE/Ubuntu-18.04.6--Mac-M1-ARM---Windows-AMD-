# Ubuntu-18.04.6-Mac-M1
1.Install Ubutu 18.04.6 ARM64 ios file
https://drive.google.com/drive/folders/1WtE12l41CbENgubpRAqTlwjE3TgMJD08?usp=share_link

2. Install Parallels Desktop or UTM, VM,..
https://maclife.io/parallels-desktop-20-huong-dan-cai-dat.html

3. You must have a USB (or you can create a virtual usb via apps)
Send the ios file to the USB

//
Create a virtual USB:
% hdiutil create -size 8G -fs HFS+ -volname "USB_Virtual" ~/Desktop/USB_Virtual.dmg
% hdiutil attach ~/Desktop/USB_Virtual.dmg

If you want to unlick the usb: % hdiutil detach /Volumes/USB_Virtual
convert dmg to iso file 


4. Open Parallels desktop
a. Click install another OS from a DVD or image file
b. 





# Install SSH

sudo apt íntall openssh-server -y

! openssh-server (>= 1:7.6p1-4) but it is not going to be installed 

-> sudo apt purge openssh-client 
-> sudo apt íntall openssh-server -y

test SSH:
-> sudo systemctl status ssh

if ssh is not enabled
-> sudo systemctl restart ssh

